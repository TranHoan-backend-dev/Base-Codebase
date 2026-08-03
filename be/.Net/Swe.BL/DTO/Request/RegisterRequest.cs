using System.ComponentModel.DataAnnotations;

namespace Swe.BL.DTO.Request;

/// <summary>
/// DTO yêu cầu đăng ký tài khoản & công ty mới.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class RegisterRequest
{
    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "TenantNameRequired")]
    public string TenantName { get; set; } = string.Empty;

    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "TenantCodeRequired")]
    public string TenantCode { get; set; } = string.Empty;

    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "UsernameRequired")]
    [MinLength(3, ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "UsernameMinLength")]
    [MaxLength(50, ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "UsernameMaxLength")]
    public string Username { get; set; } = string.Empty;

    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "EmailRequired")]
    [EmailAddress(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "EmailInvalid")]
    public string Email { get; set; } = string.Empty;

    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "PasswordRequired")]
    public string Password { get; set; } = string.Empty;

    public string? FirstName { get; set; }

    public string? LastName { get; set; }

    public string? PhoneNumber { get; set; }
}
