using System.Data;
using Dapper;
using Swe.Common.Model;
using Swe.Common.Enum;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository quản lý Công ty / Tổ chức (Tenant Multitenancy).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class TenantRepository(DbContext dbContext) : BaseRepository<Tenant>(dbContext)
{
    public async Task<Tenant?> GetByCodeAsync(string code)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_tenants WHERE code = @Code AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<Tenant>(sql, new { Code = code });
    }

    public async Task<Tenant> CreateAsync(Tenant tenant)
    {
        tenant.CreatedAt = DateTime.UtcNow;
        tenant.ModifiedAt = DateTime.UtcNow;
        await SaveAsync(tenant, AppEnum.ModelState.Insert);
        return tenant;
    }

    public async Task<Tenant?> GetByIdAsync(long id)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_tenants WHERE id = @Id AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<Tenant>(sql, new { Id = id });
    }
}
