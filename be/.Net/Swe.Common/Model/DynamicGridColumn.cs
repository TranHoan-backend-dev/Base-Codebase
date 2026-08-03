using System.ComponentModel.DataAnnotations;
using Swe.Common.Attributes;
using Swe.Common.Base;

namespace Swe.Common.Model;

/// <summary>
/// Thực thể lưu cấu hình từng cột trong bảng động.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("dynamic_grid_columns")]
public class DynamicGridColumn : BaseModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [ConfigColumn("grid_config_id")]
    public long GridConfigId { get; set; }

    [Required]
    [MaxLength(100)]
    [ConfigColumn("field_name")]
    public string FieldName { get; set; } = string.Empty;

    [Required]
    [MaxLength(255)]
    [ConfigColumn("header_label")]
    public string HeaderLabel { get; set; } = string.Empty;

    [Required]
    [MaxLength(20)]
    [ConfigColumn("data_type")]
    public string DataType { get; set; } = string.Empty;

    [ConfigColumn("width")]
    public int? Width { get; set; }

    [ConfigColumn("min_width")]
    public int? MinWidth { get; set; }

    [MaxLength(10)]
    [ConfigColumn("pinned")]
    public string? Pinned { get; set; }

    [ConfigColumn("resizable")]
    public bool Resizable { get; set; } = true;

    [ConfigColumn("sortable")]
    public bool Sortable { get; set; } = true;

    [ConfigColumn("filterable")]
    public bool Filterable { get; set; } = false;

    [ConfigColumn("visible")]
    public bool Visible { get; set; } = true;

    [ConfigColumn("display_order")]
    public int DisplayOrder { get; set; } = 0;
}
