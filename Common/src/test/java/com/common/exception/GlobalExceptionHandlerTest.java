package com.common.exception;

import com.common.dto.response.WrapperApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Kiểm thử đơn vị cho lớp GlobalExceptionHandler.<br/>
 * Sử dụng cơ chế MockMvc độc lập (standaloneSetup) để kiểm tra serialization của ngoại lệ mà không cần khởi chạy Spring Context.<br/>
 *
 * @author txhoan
 */
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @RestController
    static class TestController {

        @GetMapping("/test/base-exception")
        public void throwBaseException() {
            throw new BaseException("Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT);
        }

        @GetMapping("/test/generic-exception")
        public void throwGenericException() {
            throw new NullPointerException("Null pointer reference!");
        }

        @PostMapping("/test/validation")
        public void testValidation(@RequestBody @Valid DummyDto dto) {
            // No-op
        }
    }

    @Getter
    @Setter
    static class DummyDto {
        @NotBlank(message = "Tên đăng nhập không được để trống")
        private String username;
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenBaseExceptionThrown_thenReturnsCustomStatusAndMessage() throws Exception {
        mockMvc.perform(get("/test/base-exception"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Tên đăng nhập đã tồn tại"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void whenGenericExceptionThrown_thenReturnsInternalServerError() throws Exception {
        mockMvc.perform(get("/test/generic-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau."))
                .andExpect(jsonPath("$.data").value("Null pointer reference!"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void whenValidationFails_thenReturnsBadRequestAndErrorsMap() throws Exception {
        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.username").value("Tên đăng nhập không được để trống"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
