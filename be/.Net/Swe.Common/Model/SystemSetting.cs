using System.ComponentModel.DataAnnotations;
using Swe.Common.Attributes;
using Swe.Common.Base;

namespace Swe.Common.Model;

/// <summary>
/// Thực thể lưu trữ cấu hình, tham số hệ thống chạy động trong cơ sở dữ liệu SQL.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("system_settings")]
public class SystemSetting : BaseModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [Required]
    [MaxLength(255)]
    [ConfigColumn("setting_key")]
    [CheckDuplicate("DuplicatedSettingKey")]
    public string SettingKey { get; set; } = string.Empty;

    [Required]
    [MaxLength(1000)]
    [ConfigColumn("setting_value")]
    public string SettingValue { get; set; } = string.Empty;

    [MaxLength(500)]
    [ConfigColumn("description")]
    public string? Description { get; set; }
}
