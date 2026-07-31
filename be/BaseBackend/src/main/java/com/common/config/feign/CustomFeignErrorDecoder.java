package com.common.config.feign;

import com.common.dto.response.WrapperApiResponse;
import com.common.exception.ForbiddenException;
import com.common.exception.NotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import java.io.IOException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomFeignErrorDecoder implements ErrorDecoder {
    ErrorDecoder defaultErrorDecoder = new Default();
    ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public Exception decode(String methodKey, @NonNull Response response) {
        // Log endpoint and request details to know exactly which endpoint failed
        var httpMethod = "UNKNOWN";
        var url = "UNKNOWN";
        try {
            if (response.request() != null) {
                if (response.request().httpMethod() != null) {
                    httpMethod = response.request().httpMethod().name();
                }
                if (response.request().url() != null) {
                    url = response.request().url();
                }
            }
        } catch (Exception ignored) {
            // ignore metadata extraction issues
            log.error(methodKey, ignored);
        }

        var status = response.status();
        var reason = response.reason();
        log.error("Feign error -> [{}] {} | status={}{} | methodKey={}",
                httpMethod,
                url,
                status,
                reason != null ? " (" + reason + ")" : "",
                methodKey);

        // If body is null or unreadable, fall back to default decoder
        if (response.body() == null) {
            return defaultErrorDecoder.decode(methodKey, response);
        }

        try (var bodyIs = response.body().asInputStream()) {
            var apiResponse = objectMapper.readValue(bodyIs, WrapperApiResponse.class);
            var message = apiResponse != null ? apiResponse.message() : null;

            return switch (response.status()) {
                case 400 -> {
                    if (message != null && message.contains("NotExistingException")) {
                        yield new NotFoundException(extractCleanMessage(message));
                    }
                    yield new IllegalArgumentException(extractCleanMessage(message));
                }
                case 403 -> new ForbiddenException(message != null ? message : "Forbidden");
                case 404 -> new NotFoundException(message != null ? message : "Not Found");
                default -> defaultErrorDecoder.decode(methodKey, response);
            };
        } catch (IOException e) {
            log.error("Error decoding Feign error response body", e);
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }

    private @NonNull String extractCleanMessage(String rawMessage) {
        if (rawMessage == null) return "Unknown error";
        // If the message contains the exception class name, try to extract just the message part
        if (rawMessage.contains(": ")) {
            return rawMessage
                    .substring(rawMessage.indexOf(": "))
                    .substring(1)
                    .trim();
        }
        return rawMessage;
    }
}
