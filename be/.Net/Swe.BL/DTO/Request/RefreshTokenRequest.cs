using System.ComponentModel.DataAnnotations;

namespace Swe.BL.DTO.Request;

/// <summary>
/// DTO yêu cầu làm mới Token.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class RefreshTokenRequest
{
    [Required(ErrorMessageResourceType = typeof(Swe.Common.Resources.ResourcesVN), ErrorMessageResourceName = "RefreshTokenRequired")]
    public string RefreshToken { get; set; } = string.Empty;
}
