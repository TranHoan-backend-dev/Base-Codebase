namespace Swe.Common.Model;

public class ErrorResult
{
    public required string DevMsg { get; set; }
    public required string UserMsg { get; set; }
    public Object? MoreInfo { get; set; }
    public string? TraceId { get; set; }
}
