using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.Service;
using Swe.Common.Model;

namespace Swe.API.Controllers;

/// <summary>
/// Controller xử lý các API liên quan đến Dynamic Grid.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[Authorize]
[ApiController]
[Route("api/v1/dynamic-grid")]
public class DynamicGridController(DynamicGridService service) : ControllerBase
{
    [HttpGet("{gridCode}/config")]
    public async Task<IActionResult> GetGridConfig([FromRoute] string gridCode)
    {
        try
        {
            var config = await service.GetGridConfigAsync(gridCode);
            if (config == null)
            {
                return NotFound(new WrapperApiResponse(404, "Cấu hình lưới hiển thị không tồn tại", null));
            }
            return Ok(new WrapperApiResponse(200, "Lấy cấu hình lưới hiển thị thành công", config));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }
}
