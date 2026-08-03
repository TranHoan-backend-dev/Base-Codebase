namespace Swe.Common.Model;

public class Pageable
{
    public int PageIndex { get; set; } = 0;
    public decimal PageSize { get; set; } = 10;
    public string? Sort { get; set; }
    public decimal TotalElements { get; init; }
    public decimal TotalPages => PageSize == 0 ? 0 : Math.Ceiling(TotalElements / PageSize);
}
