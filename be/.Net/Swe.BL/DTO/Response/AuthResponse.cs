using System.Collections.Generic;

namespace Swe.BL.DTO.Response;

/// <summary>
/// DTO phản hồi kết quả xác thực Auth.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class AuthResponse
{
    public string AccessToken { get; set; } = string.Empty;
    public string RefreshToken { get; set; } = string.Empty;
    public string TokenType { get; set; } = "Bearer";
    public long ExpiresIn { get; set; }
    public long UserId { get; set; }
    public string Username { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public long? TenantId { get; set; }
    public string? TenantCode { get; set; }
    public List<string> Roles { get; set; } = new();
}
