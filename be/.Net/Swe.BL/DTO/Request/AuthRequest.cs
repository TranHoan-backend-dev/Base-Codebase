using System.ComponentModel.DataAnnotations;

namespace Swe.BL.DTO.Request;

/// <summary>
/// DTO yêu cầu đăng nhập.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class AuthRequest
{
    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "UsernameLoginRequired")]
    public string Username { get; set; } = string.Empty;

    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "PasswordRequired")]
    public string Password { get; set; } = string.Empty;
}
