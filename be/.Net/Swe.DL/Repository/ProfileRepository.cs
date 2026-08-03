using System.Data;
using Dapper;
using Swe.Common.Model;
using Swe.Common.Enum;
using Microsoft.AspNetCore.Http;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository quản lý Hồ sơ người dùng (User Profile).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class ProfileRepository(DbContext dbContext, IHttpContextAccessor httpContextAccessor) : BaseRepository<Profile>(dbContext, httpContextAccessor)
{
    public async Task<Profile?> GetByUserIdAsync(long userId)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_user_profiles WHERE user_id = @UserId AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<Profile>(sql, new { UserId = userId });
    }

    public async Task<Profile> CreateAsync(Profile profile)
    {
        profile.CreatedAt = DateTime.UtcNow;
        profile.ModifiedAt = DateTime.UtcNow;
        await SaveAsync(profile, AppEnum.ModelState.Insert);
        return profile;
    }
}
