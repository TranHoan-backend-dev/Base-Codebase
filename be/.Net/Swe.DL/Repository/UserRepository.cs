using System.Data;
using Dapper;
using Swe.Common.Model;
using Swe.Common.Enum;
using Microsoft.AspNetCore.Http;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository quản lý Tài khoản người dùng (User Account).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class UserRepository(DbContext dbContext, IHttpContextAccessor httpContextAccessor) : BaseRepository<User>(dbContext, httpContextAccessor)
{
    public async Task<User?> GetByUsernameAsync(string username)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_users WHERE username = @Username AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<User>(sql, new { Username = username });
    }

    public async Task<User?> GetByEmailAsync(string email)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_users WHERE email = @Email AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<User>(sql, new { Email = email });
    }

    public Task<bool> ExistsByUsernameAsync(string username) => CheckDuplicateAsync("username", username);

    public Task<bool> ExistsByEmailAsync(string email) => CheckDuplicateAsync("email", email);

    public async Task<User> CreateAsync(User user)
    {
        user.CreatedAt = DateTime.UtcNow;
        user.ModifiedAt = DateTime.UtcNow;
        await SaveAsync(user, AppEnum.ModelState.Insert);
        return user;
    }

    public async Task<List<string>> GetRolesAsync(long userId)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT role FROM sys_user_roles WHERE user_id = @UserId";
        var roles = await conn.QueryAsync<string>(sql, new { UserId = userId });
        return roles.ToList();
    }

    public async Task AddRoleAsync(long userId, string role)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "INSERT INTO sys_user_roles (user_id, role) VALUES (@UserId, @Role)";
        await conn.ExecuteAsync(sql, new { UserId = userId, Role = role });
    }

    public async Task<User?> GetByIdAsync(long id)
    {
        using var conn = dbContext.GetConnection();
        const string sql = "SELECT * FROM sys_users WHERE id = @Id AND deleted = 0 LIMIT 1";
        return await conn.QueryFirstOrDefaultAsync<User>(sql, new { Id = id });
    }
}
