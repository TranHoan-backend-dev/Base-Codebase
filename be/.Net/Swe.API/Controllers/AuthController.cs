using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Swe.BL.DTO.Request;
using Swe.BL.DTO.Response;
using Swe.BL.Service;
using Swe.Common.Model;

namespace Swe.API.Controllers;

/// <summary>
/// Controller xử lý các API xác thực Auth (Đăng ký, Đăng nhập, Refresh Token, Đăng xuất, User Info).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ApiController]
[Route("api/v1/auth")]
public class AuthController(AuthService authService) : ControllerBase
{
    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] RegisterRequest request)
    {
        try
        {
            var response = await authService.RegisterAsync(request);
            return StatusCode(201, new WrapperApiResponse(201, "Đăng ký tài khoản thành công", response));
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

    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] AuthRequest request)
    {
        try
        {
            var response = await authService.LoginAsync(request);
            return Ok(new WrapperApiResponse(200, "Đăng nhập thành công", response));
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

    [HttpPost("refresh")]
    public async Task<IActionResult> Refresh([FromBody] RefreshTokenRequest request)
    {
        try
        {
            var response = await authService.RefreshTokenAsync(request);
            return Ok(new WrapperApiResponse(200, "Làm mới token thành công", response));
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

    [Authorize]
    [HttpPost("logout")]
    public IActionResult Logout()
    {
        // Phía client chỉ cần xóa token. Ở đây trả về thành công.
        return Ok(new WrapperApiResponse(200, "Đăng xuất thành công", null));
    }

    [Authorize]
    [HttpGet("me")]
    public async Task<IActionResult> GetCurrentUser()
    {
        var username = User.Identity?.Name ?? User.FindFirst(ClaimTypes.Name)?.Value;
        if (string.IsNullOrEmpty(username))
        {
            return Unauthorized(new WrapperApiResponse(401, "Chưa xác thực người dùng", null));
        }

        try
        {
            var userDto = await authService.GetCurrentUserAsync(username);
            return Ok(new WrapperApiResponse(200, "Lấy thông tin người dùng thành công", userDto));
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
