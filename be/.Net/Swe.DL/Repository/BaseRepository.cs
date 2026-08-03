using System.Data;
using System.Reflection;
using System.Text;
using Dapper;
using Microsoft.AspNetCore.Http;
using System.Security.Claims;
using Swe.Common.Attributes;
using Swe.Common.Extension;
using Swe.Common.Enum;
using Swe.DL.Context;

namespace Swe.DL.Repository;

/// <summary>
/// Repository cơ sở chứa các phương thức hỗ trợ truy vấn động chung cho các bảng.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public abstract class BaseRepository<T>(DbContext dbContext, IHttpContextAccessor httpContextAccessor) where T : class
{
    protected readonly DbContext DbContext = dbContext;
    protected readonly IHttpContextAccessor HttpContextAccessor = httpContextAccessor;

    protected string GetCurrentUserName()
    {
        var httpContext = HttpContextAccessor.HttpContext;
        var username = httpContext?.User?.Identity?.Name ?? httpContext?.User?.FindFirst(ClaimTypes.Name)?.Value;
        return string.IsNullOrEmpty(username) ? "SYSTEM" : username;
    }

    /// <summary>
    /// Kiểm tra trùng lặp cho một trường (cột) cụ thể.
    /// </summary>
    public async Task<bool> CheckDuplicateAsync(string columnName, object value, object? excludeId = null)
    {
        var type = typeof(T);
        var tableName = type.GetTableNameOnly();
        var primaryKey = type.GetPrimaryKey().keyTable;

        using var conn = DbContext.GetConnection();
        var sql = new StringBuilder($"SELECT COUNT(1) FROM `{tableName}` WHERE `{columnName}` = @Value AND `deleted` = 0");

        var parameters = new DynamicParameters();
        parameters.Add("@Value", value);

        if (excludeId != null)
        {
            sql.Append($" AND `{primaryKey}` <> @Id");
            parameters.Add("@Id", excludeId);
        }

        return await conn.ExecuteScalarAsync<int>(sql.ToString(), parameters) > 0;
    }

    /// <summary>
    /// Kiểm tra trùng lặp toàn bộ Entity dựa trên thuộc tính được cấu hình [CheckDuplicate].
    /// </summary>
    public async Task<bool> CheckDuplicateEntityAsync(T entity)
    {
        var type = typeof(T);
        var tableName = type.GetTableNameOnly();
        var primaryKey = type.GetPrimaryKey();

        var properties = type.GetProperties()
            .Where(p => p.GetCustomAttribute<CheckDuplicateAttribute>(true) is not null)
            .ToList();

        if (properties.Count == 0) return false;

        using var conn = DbContext.GetConnection();
        var parameters = new DynamicParameters();

        var conditions = new List<string>();
        foreach (var prop in properties)
        {
            var colName = prop.GetCustomAttribute<ConfigColumnAttribute>()?.ColumnName ?? prop.Name;
            var val = prop.GetValue(entity);
            if (val != null)
            {
                conditions.Add($"`{colName}` = @{prop.Name}");
                parameters.Add(prop.Name, val);
            }
        }

        if (conditions.Count == 0) return false;

        var sql = new StringBuilder($"SELECT COUNT(1) FROM `{tableName}` WHERE ({string.Join(" OR ", conditions)}) AND `deleted` = 0");

        // Exclude current entity ID if updating
        var idVal = type.GetProperty(primaryKey.keyModel)?.GetValue(entity);
        if (idVal != null && !idVal.Equals(default))
        {
            sql.Append($" AND `{primaryKey.keyTable}` <> @Id");
            parameters.Add("@Id", idVal);
        }

        return await conn.ExecuteScalarAsync<int>(sql.ToString(), parameters) > 0;
    }

    /// <summary>
    /// Lưu thông tin thực thể (Thêm mới hoặc Cập nhật) dựa trên trạng thái (ModelState).
    /// </summary>
    public async Task<long> SaveAsync(T entity, AppEnum.ModelState state)
    {
        var type = typeof(T);
        var tableName = type.GetTableNameOnly();
        var primaryKey = type.GetPrimaryKey();
        
        var now = DateTime.UtcNow;
        var currentUser = GetCurrentUserName();

        if (state == AppEnum.ModelState.Insert)
        {
            type.GetProperty("CreatedAt")?.SetValue(entity, now);
            type.GetProperty("CreatedBy")?.SetValue(entity, currentUser);
            type.GetProperty("ModifiedAt")?.SetValue(entity, now);
            type.GetProperty("ModifiedBy")?.SetValue(entity, currentUser);
        }
        else if (state == AppEnum.ModelState.Update)
        {
            type.GetProperty("ModifiedAt")?.SetValue(entity, now);
            type.GetProperty("ModifiedBy")?.SetValue(entity, currentUser);
        }

        var (columnsTable, propertiesModel) = type.GetAllColumnsAndProperties();
        using var conn = DbContext.GetConnection();

        if (state == AppEnum.ModelState.Insert)
        {
            var insertCols = new List<string>();
            var insertProps = new List<string>();

            for (int i = 0; i < columnsTable.Count; i++)
            {
                if (columnsTable[i] == primaryKey.keyTable) continue;
                insertCols.Add($"`{columnsTable[i]}`");
                insertProps.Add($"@{propertiesModel[i]}");
            }

            var sql = $"INSERT INTO `{tableName}` ({string.Join(", ", insertCols)}) VALUES ({string.Join(", ", insertProps)}); SELECT LAST_INSERT_ID();";
            var id = await conn.ExecuteScalarAsync<long>(sql, entity);
            
            type.GetProperty(primaryKey.keyModel)?.SetValue(entity, id);
            return id;
        }
        else if (state == AppEnum.ModelState.Update)
        {
            var updateSets = new List<string>();

            for (int i = 0; i < columnsTable.Count; i++)
            {
                if (columnsTable[i] == primaryKey.keyTable) continue;
                updateSets.Add($"`{columnsTable[i]}` = @{propertiesModel[i]}");
            }

            var sql = $"UPDATE `{tableName}` SET {string.Join(", ", updateSets)} WHERE `{primaryKey.keyTable}` = @{primaryKey.keyModel}";
            await conn.ExecuteAsync(sql, entity);

            var idVal = type.GetProperty(primaryKey.keyModel)?.GetValue(entity);
            return idVal is long longVal ? longVal : 0;
        }

        return 0;
    }
}
