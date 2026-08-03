using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.Service;
using Swe.Common.Model;

namespace Swe.API.Controllers;

/// <summary>
/// Controller quản lý Cấu hình hệ thống (System Settings).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[Authorize]
[ApiController]
[Route("api/v1/system-settings")]
public class SystemSettingController(SystemSettingService service) : ControllerBase
{
    [HttpGet("id/{id:long}")]
    public async Task<IActionResult> GetById([FromRoute] long id)
    {
        try
        {
            var setting = await service.GetByIdAsync(id);
            if (setting == null)
            {
                return NotFound(new WrapperApiResponse(404, "Không tìm thấy cấu hình", null));
            }
            return Ok(new WrapperApiResponse(200, "Lấy cấu hình thành công", setting));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpGet("key/{key}")]
    public async Task<IActionResult> GetByKey([FromRoute] string key)
    {
        try
        {
            var setting = await service.GetByKeyAsync(key);
            if (setting == null)
            {
                return NotFound(new WrapperApiResponse(404, "Không tìm thấy cấu hình", null));
            }
            return Ok(new WrapperApiResponse(200, "Lấy cấu hình thành công", setting));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpPost]
    public async Task<IActionResult> Create([FromBody] SystemSetting setting)
    {
        try
        {
            var created = await service.CreateAsync(setting);
            return StatusCode(201, new WrapperApiResponse(201, "Tạo cấu hình thành công", created));
        }
        catch (ArgumentException ex)
        {
            return BadRequest(new WrapperApiResponse(400, ex.Message, null));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpPut("{id:long}")]
    public async Task<IActionResult> Update([FromRoute] long id, [FromBody] SystemSetting setting)
    {
        try
        {
            setting.Id = id;
            var updated = await service.UpdateAsync(setting);
            return Ok(new WrapperApiResponse(200, "Cập nhật cấu hình thành công", updated));
        }
        catch (ArgumentException ex)
        {
            return BadRequest(new WrapperApiResponse(400, ex.Message, null));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpDelete("{id:long}")]
    public async Task<IActionResult> Delete([FromRoute] long id)
    {
        try
        {
            await service.DeleteAsync(id);
            return Ok(new WrapperApiResponse(200, "Xóa cấu hình thành công", null));
        }
        catch (ArgumentException ex)
        {
            return BadRequest(new WrapperApiResponse(400, ex.Message, null));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }
}
