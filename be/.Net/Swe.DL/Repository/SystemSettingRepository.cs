using System.Data;
using Dapper;
using Swe.Common.Model;
using Microsoft.AspNetCore.Http;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository quản lý Cấu hình hệ thống (System Settings).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class SystemSettingRepository(DbContext dbContext, IHttpContextAccessor httpContextAccessor) : BaseRepository<SystemSetting>(dbContext, httpContextAccessor)
{
    public async Task<SystemSetting?> GetByKeyAsync(string key)
    {
        using var conn = DbContext.GetConnection();
        const string sql = "SELECT * FROM system_settings WHERE setting_key = @Key LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<SystemSetting>(sql, new { Key = key });
    }

    public async Task<SystemSetting?> GetByIdAsync(long id)
    {
        using var conn = DbContext.GetConnection();
        const string sql = "SELECT * FROM system_settings WHERE id = @Id LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<SystemSetting>(sql, new { Id = id });
    }

    public async Task DeleteAsync(long id)
    {
        using var conn = DbContext.GetConnection();
        const string sql = "DELETE FROM system_settings WHERE id = @Id";
        await conn.ExecuteAsync(sql, new { Id = id });
    }
}
