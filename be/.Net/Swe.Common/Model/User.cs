using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Swe.Common.Attributes;
using Swe.Common.Base;
using Swe.Common.Enum;

namespace Swe.Common.Model;

/// <summary>
/// Entity đại diện cho Tài khoản người dùng (User Account).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("sys_users")]
public class User : BaseSoftDeleteModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    [ConfigColumn("username")]
    [CheckDuplicate("DuplicatedUsername")]
    public string Username { get; set; } = string.Empty;

    [Required]
    [MaxLength(150)]
    [ConfigColumn("email")]
    [CheckDuplicate("DuplicatedEmail")]
    public string Email { get; set; } = string.Empty;

    [Required]
    [MaxLength(255)]
    [ConfigColumn("password")]
    public string Password { get; set; } = string.Empty;

    [Required]
    [ConfigColumn("status")]
    public string Status { get; set; } = AppEnum.EntityStatus.ACTIVE.ToString();

    [ConfigColumn("tenant_id")]
    public long? TenantId { get; set; }

    [NotMapped]
    public List<string> Roles { get; set; } = new();
}
