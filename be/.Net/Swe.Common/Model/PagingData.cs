namespace Swe.Common.Model;

public class PagingData<T>
{
    public IEnumerable<T>? Data { get; set; }
    public Pageable? Pageable { get; set; }
}
