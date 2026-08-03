using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.DTO.Response;
using Swe.BL.Service;
using Swe.Common.Model;

namespace Swe.API.Controllers;

/// <summary>
/// Controller quản lý Hồ sơ người dùng (User Profile).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[Authorize]
[ApiController]
[Route("api/v1/profiles")]
public class ProfileController(ProfileService profileService) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetCurrentProfile()
    {
        var userIdStr = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!long.TryParse(userIdStr, out long userId))
        {
            return Unauthorized(new WrapperApiResponse(401, "Chưa xác thực người dùng", null));
        }

        try
        {
            var profile = await profileService.GetByUserIdAsync(userId);
            if (profile == null)
            {
                return NotFound(new WrapperApiResponse(404, "Hồ sơ người dùng không tồn tại", null));
            }

            var dto = new ProfileDTO
            {
                Id = profile.Id,
                FirstName = profile.FirstName,
                LastName = profile.LastName,
                PhoneNumber = profile.PhoneNumber,
                AvatarUrl = profile.AvatarUrl,
                Position = profile.Position,
                Department = profile.Department,
                UserId = profile.UserId,
                CreatedAt = profile.CreatedAt,
                ModifiedAt = profile.ModifiedAt
            };

            return Ok(new WrapperApiResponse(200, "Lấy hồ sơ người dùng thành công", dto));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }

    [HttpPut]
    public async Task<IActionResult> UpdateProfile([FromBody] ProfileDTO request)
    {
        var userIdStr = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
        if (!long.TryParse(userIdStr, out long userId))
        {
            return Unauthorized(new WrapperApiResponse(401, "Chưa xác thực người dùng", null));
        }

        try
        {
            var profile = new Profile
            {
                FirstName = request.FirstName,
                LastName = request.LastName,
                PhoneNumber = request.PhoneNumber,
                AvatarUrl = request.AvatarUrl,
                Position = request.Position,
                Department = request.Department,
                UserId = userId
            };

            var updated = await profileService.UpdateAsync(profile);

            request.Id = updated.Id;
            request.UserId = userId;
            request.CreatedAt = updated.CreatedAt;
            request.ModifiedAt = updated.ModifiedAt;

            return Ok(new WrapperApiResponse(200, "Cập nhật hồ sơ người dùng thành công", request));
        }
        catch (Exception ex)
        {
            return StatusCode(500, new WrapperApiResponse(500, "Đã xảy ra lỗi hệ thống: " + ex.Message, null));
        }
    }
}
