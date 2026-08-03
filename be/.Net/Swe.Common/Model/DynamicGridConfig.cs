using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Swe.Common.Attributes;
using Swe.Common.Base;

namespace Swe.Common.Model;

/// <summary>
/// Thực thể lưu cấu hình bảng động (Dynamic Grid) để render lên web.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("dynamic_grid_configs")]
public class DynamicGridConfig : BaseModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    [ConfigColumn("grid_key")]
    public string GridKey { get; set; } = string.Empty;

    [Required]
    [MaxLength(255)]
    [ConfigColumn("grid_label")]
    public string GridLabel { get; set; } = string.Empty;

    [ConfigColumn("tree_grid_enabled")]
    public bool TreeGridEnabled { get; set; } = false;

    [MaxLength(100)]
    [ConfigColumn("tree_id_field")]
    public string? TreeIdField { get; set; }

    [MaxLength(100)]
    [ConfigColumn("tree_parent_field")]
    public string? TreeParentField { get; set; }

    [NotMapped]
    public List<DynamicGridColumn> Columns { get; set; } = new();
}
