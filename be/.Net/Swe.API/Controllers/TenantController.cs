using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.DTO.Response;
using Swe.BL.Service;
using Swe.Common.Model;
using Swe.Common.Enum;

namespace Swe.API.Controllers;

/// <summary>
/// Controller quản lý Tenant (Công ty / Tổ chức).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[Authorize]
[ApiController]
[Route("api/v1/tenants")]
public class TenantController(TenantService tenantService) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<IActionResult> GetById([FromRoute] long id)
    {
        try
        {
            var tenant = await tenantService.GetByIdAsync(id);
            if (tenant == null)
            {
                return NotFound(new WrapperApiResponse(404, "Không tìm thấy tổ chức", null));
            }

            var dto = new TenantDTO
            {
                Id = tenant.Id,
                Name = tenant.Name,
                Code = tenant.Code,
                Domain = tenant.Domain,
                Status = tenant.Status,
                CreatedAt = tenant.CreatedAt,
                ModifiedAt = tenant.ModifiedAt
            };

            return Ok(new WrapperApiResponse(200, "Lấy thông tin tổ chức thành công", dto));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpPost]
    public async Task<IActionResult> Create([FromBody] TenantDTO request)
    {
        try
        {
            var tenant = new Tenant
            {
                Name = request.Name,
                Code = request.Code,
                Domain = request.Domain,
                Status = string.IsNullOrEmpty(request.Status) ? AppEnum.EntityStatus.ACTIVE.ToString() : request.Status,
                CreatedBy = "SYSTEM",
                ModifiedBy = "SYSTEM"
            };

            var created = await tenantService.CreateAsync(tenant);
            request.Id = created.Id;
            request.CreatedAt = created.CreatedAt;
            request.ModifiedAt = created.ModifiedAt;

            return StatusCode(201, new WrapperApiResponse(201, "Tạo tổ chức thành công", request));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }
}
