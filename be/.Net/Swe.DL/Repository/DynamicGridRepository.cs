using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Dapper;
using Swe.Common.Model;
using Microsoft.AspNetCore.Http;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository quản lý Cấu hình bảng động (Dynamic Grid).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class DynamicGridRepository(DbContext dbContext, IHttpContextAccessor httpContextAccessor) : BaseRepository<DynamicGridConfig>(dbContext, httpContextAccessor)
{
    public async Task<DynamicGridConfig?> GetByGridKeyAsync(string gridKey)
    {
        using var conn = DbContext.GetConnection();
        const string configSql = "SELECT * FROM dynamic_grid_configs WHERE grid_key = @GridKey AND deleted = 0 LIMIT 1";
        
        var config = await conn.QueryFirstOrDefaultAsync<DynamicGridConfig>(configSql, new { GridKey = gridKey });
        if (config == null) return null;

        const string columnsSql = "SELECT * FROM dynamic_grid_columns WHERE grid_config_id = @ConfigId AND deleted = 0 ORDER BY display_order ASC";
        var columns = await conn.QueryAsync<DynamicGridColumn>(columnsSql, new { ConfigId = config.Id });
        config.Columns = columns.ToList();

        return config;
    }
}
