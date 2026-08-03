using System.ComponentModel.DataAnnotations;
using Swe.Common.Enum;

namespace Swe.BL.DTO.Request;

public class FilterRequest
{
    public string? Keyword { get; set; }
    public IEnumerable<FilterColumn>? ColumnFilters { get; set; }

    public class FilterColumn
    {
        public required string Column { get; set; }
        public required string Value { get; set; }
        public AppEnum.DataType DataType { get; set; }
        public AppEnum.FilterType FilterType { get; set; }
    }
}
