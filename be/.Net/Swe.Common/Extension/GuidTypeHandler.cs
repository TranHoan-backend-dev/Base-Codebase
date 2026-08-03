using System.Data;
using Dapper;

namespace Swe.Common.Extension;

public class GuidTypeHandler : SqlMapper.TypeHandler<Guid>
{
    public override void SetValue(IDbDataParameter parameter, Guid value)
    {
        parameter.Value = value.ToString();
    }

    public override Guid Parse(object value)
    {
        if (value is Guid guid) return guid;
        return Guid.Parse(value.ToString() ?? string.Empty);
    }
}
