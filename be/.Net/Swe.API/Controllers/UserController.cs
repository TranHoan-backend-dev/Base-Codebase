using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.DTO.Response;
using Swe.BL.Service;
using Swe.Common.Model;

namespace Swe.API.Controllers;

/// <summary>
/// Controller quản lý User (Tài khoản người dùng).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[Authorize]
[ApiController]
[Route("api/v1/users")]
public class UserController(UserService userService, UserRepository userRepository, TenantRepository tenantRepository) : ControllerBase
{
    [HttpGet("{id:long}")]
    public async Task<IActionResult> GetById([FromRoute] long id)
    {
        try
        {
            var user = await userService.GetByIdAsync(id);
            if (user == null)
            {
                return NotFound(new WrapperApiResponse(404, "Không tìm thấy người dùng", null));
            }

            var roles = await userRepository.GetRolesAsync(user.Id);
            var tenant = user.TenantId.HasValue ? await tenantRepository.GetByIdAsync(user.TenantId.Value) : null;

            var dto = new UserDTO
            {
                Id = user.Id,
                Username = user.Username,
                Email = user.Email,
                Status = user.Status,
                TenantId = user.TenantId,
                TenantCode = tenant?.Code,
                Roles = roles,
                CreatedAt = user.CreatedAt,
                ModifiedAt = user.ModifiedAt
            };

            return Ok(new WrapperApiResponse(200, "Lấy thông tin người dùng thành công", dto));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }
}
