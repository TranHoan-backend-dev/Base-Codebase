using Swe.Common.Attributes;

namespace Swe.Common.Base;

public abstract class BaseSoftDeleteModel : BaseModel
{
    [ConfigColumn("deleted")]
    public bool Deleted { get; set; } = false;

    [ConfigColumn("deleted_at")]
    public DateTime? DeletedAt { get; set; }

    [ConfigColumn("deleted_by")]
    public string? DeletedBy { get; set; }
}
