using System.ComponentModel.DataAnnotations.Schema;
using Swe.Common.Attributes;
using Swe.Common.Enum;

namespace Swe.Common.Base;

public class BaseModel
{
    [ConfigColumn("created_by")] public string? CreatedBy { get; set; }
    [ConfigColumn("created_at")] public DateTime CreatedAt { get; set; }
    [ConfigColumn("modified_by")] public string? ModifiedBy { get; set; }
    [ConfigColumn("modified_at")] public DateTime? ModifiedAt { get; set; }
    [NotMapped] public AppEnum.ModelState? State { get; set; }
}
