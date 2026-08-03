using System.ComponentModel.DataAnnotations;
using Swe.Common.Attributes;
using Swe.Common.Base;
using Swe.Common.Enum;

namespace Swe.Common.Model;

/// <summary>
/// Entity đại diện cho Công ty / Tổ chức (Tenant Multitenancy).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("sys_tenants")]
public class Tenant : BaseSoftDeleteModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    [ConfigColumn("name")]
    public string Name { get; set; } = string.Empty;

    [Required]
    [MaxLength(50)]
    [ConfigColumn("code")]
    [CheckDuplicate("DuplicatedTenantCode")]
    public string Code { get; set; } = string.Empty;

    [MaxLength(255)]
    [ConfigColumn("domain")]
    public string? Domain { get; set; }

    [Required]
    [ConfigColumn("status")]
    public string Status { get; set; } = AppEnum.EntityStatus.ACTIVE.ToString();
}
