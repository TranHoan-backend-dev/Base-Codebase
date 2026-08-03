using Microsoft.AspNetCore.Diagnostics;
using Swe.Common.Enum;
using Swe.Common.Exception;
using Swe.Common.Model;
using Swe.Common.Resources;

namespace Swe.API.Exception;

public class GlobalExceptionHandler(ILogger<GlobalExceptionHandler> log) : IExceptionHandler
{
    public async ValueTask<bool> TryHandleAsync(HttpContext httpContext, System.Exception exception,
        CancellationToken cancellationToken)
    {
        log.LogError(exception, "[GlobalExceptionHandler] Exception occurred: {Message}", exception.Message);

        var (status, message) = exception switch
        {
            AlreadyExistsException => (AppEnum.StatusCode.BadRequest, exception.Message),
            NotFoundException => (AppEnum.StatusCode.NotFound, exception.Message),
            UnauthorizedAccessException => (AppEnum.StatusCode.Unauthorized, exception.Message),
            _ => (AppEnum.StatusCode.InternalServerError, exception.Message)
        };

        var response = new ErrorResult()
        {
            DevMsg = message,
            UserMsg = ResourcesVN.Exception,
            MoreInfo = exception.ToString(),
        };
        log.LogError($"[GlobalExceptionHandler] Error happening when solving the request: {response}");

        httpContext.Response.StatusCode = (int)status;
        httpContext.Response.ContentType = "application/json";

        await httpContext.Response.WriteAsJsonAsync(response, cancellationToken);
        return true;
    }
}
