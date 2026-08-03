using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Reflection;
using Swe.Common.Attributes;

namespace Swe.Common.Extension;

/// <summary>
/// Class Utility, dung de mo rong hanh vi cho cac class khac
/// </summary>
public static class ExtensionUtility
{
    /// <summary>
    /// Cho phep lay ra ten bang
    /// </summary>
    /// <param name="type">
    /// Cho phep 1 doi tuong kieu Type goi method nay nhu the no thuoc ve Type
    /// VD: typeof(Candidate). Khi do co the goi typeof(Candidate).GetTableNameOnly()
    /// </param>
    /// <returns></returns>
    public static string GetTableNameOnly(this Type type)
    {
        var attribute = type.GetCustomAttribute<ConfigTableAttribute>(true);
        return attribute?.TableName ?? type.Name;
    }

    /// <summary>
    /// Cho phep lay ra thuoc tinh duoc danh dau la khoa chinh ([Key]) cua bang
    /// </summary>
    /// <param name="type"></param>
    /// <returns></returns>
    public static string GetPrimaryKeyAttribute(this Type type)
    {
        var primaryKeyName = string.Empty;
        // lay ra toan bo property
        PropertyInfo[] properties = type.GetProperties();
        if (properties.Length > 0)
        {
            // tim doi tuong co attribute [Key]
            var propertyInfoKey = properties.SingleOrDefault(p => p
                .GetCustomAttributes<KeyAttribute>(true).Any()
            );
            if (propertyInfoKey != null)
            {
                primaryKeyName = propertyInfoKey.Name;
            }
        }

        return primaryKeyName;
    }

    /// <summary>
    /// Cho phep lay ra ten khoa chinh + ten cot tuong ung trong bang
    /// </summary>
    /// <param name="type">Kieu Model</param>
    /// <returns>
    /// <ul>
    ///     <li>keyModel: Ten cua thuoc tinh trong Model dung lam khoa chinh</li>
    ///     <li>keyTable: Ten cua cot khoa chinh trong DB</li>
    /// </ul>
    /// </returns>
    public static (string keyModel, string keyTable) GetPrimaryKey(this Type type)
    {
        var keyModel = string.Empty;
        var keyTable = string.Empty;
        var properties = type.GetProperties();
        if (properties.Length > 0)
        {
            var propertyInfoKey = properties.SingleOrDefault(p =>
                p.GetCustomAttribute<KeyAttribute>(true) is not null
            );
            if (propertyInfoKey is not null)
            {
                keyModel = propertyInfoKey.Name;
                keyTable = propertyInfoKey.GetCustomAttribute<ConfigColumnAttribute>()?.ColumnName ?? propertyInfoKey.Name;
            }
        }

        return (keyModel, keyTable);
    }

    /// <summary>
    /// Lay ra toan bo cac thuoc tinh duoc map xuong DB (tru NotMapped)
    /// </summary>
    /// <param name="type"></param>
    /// <returns></returns>
    public static List<string> GetAllColumns(this Type type)
    {
        var columns = new List<string>();
        var properties = type.GetProperties();
        if (properties.Length > 0)
        {
            // Tim bat cu ban ghi nao khong co NotMapped
            columns = properties.Where(p => p.GetCustomAttribute<NotMappedAttribute>(true) is null)
                .Select(p => p.GetCustomAttribute<ConfigColumnAttribute>(true)?.ColumnName)
                .Where(colName => colName != null)
                .ToList()!;
        }

        return columns;
    }

    /// <summary>
    /// Tra ve value tuong ung voi key trong Dictionary (HashMap)
    /// </summary>
    /// <param name="dic">Tap hop kieu du lieu key-value (Giong Hashmap trong java)</param>
    /// <param name="key"></param>
    /// <returns></returns>
    public static object? Get(this Dictionary<string, object> dic, string key)
    {
        return dic.GetValueOrDefault(key);
    }

    /// <summary>
    /// Ep kieu obj sang Dictionary
    /// </summary>
    /// <param name="obj"></param>
    /// <returns></returns>
    public static Dictionary<string, object> ToDictionary(this object? obj)
    {
        // neu obj cast sang Dictionary<string, object> thanh cong thi lay, con khong thi lay doi tuong Dictionary moi
        return obj as Dictionary<string, object> ?? new Dictionary<string, object>();
    }

    /// <summary>
    /// Lay value theo propertyName
    /// <ul>
    ///     <li>Neu la Dictionary, thi lay tu key</li>
    ///     <li>Neu la object binh thuong thi lay tu property name</li>
    /// </ul>
    /// </summary>
    /// <param name="objEntity"></param>
    /// <param name="propertyName"></param>
    /// <returns></returns>
    public static object? GetValue(this object? objEntity, string propertyName)
    {
        if (objEntity is null || string.IsNullOrEmpty(propertyName))
            return null;

        // neu la Dictionary / Expando, IDictionary
        if (objEntity is IDictionary<string, object> objects)
        {
            return objects.TryGetValue(propertyName, out var value) ? value : null;
        }

        // neu la obj binh thuong
        var type = objEntity.GetType();
        var info = type.GetProperty(propertyName);

        // Neu khong tim thay theo property name, thu tim theo ConfigColumnAttribute (snake_case)
        if (info == null)
        {
            info = type.GetProperties().FirstOrDefault(p =>
                p.GetCustomAttribute<ConfigColumnAttribute>()?.ColumnName == propertyName);
        }

        return info?.GetValue(objEntity);
    }

    /// <summary>
    /// Cho phep lay ra danh sach ten cot va ten thuoc tinh tuong ung cua no
    /// </summary>
    /// <param name="type"></param>
    /// <returns>
    /// <ul>
    ///     <li>propertiesModel: Ten cua thuoc tinh trong Model</li>
    ///     <li>columnsTable: Ten cua cot trong DB</li>
    /// </ul>
    /// </returns>
    public static (List<string> columnsTable, List<string> propertiesModel) GetAllColumnsAndProperties(this Type type)
    {
        var columnsTable = new List<string>();
        var propertiesModel = new List<string>();
        var properties = type.GetProperties();

        if (properties.Length > 0)
        {
            columnsTable = properties.Where(p => p.GetCustomAttribute<NotMappedAttribute>(true) is null) // loc va loai bo tat ca cac phan tu not mapped
                .Select(p => p.GetCustomAttribute<ConfigColumnAttribute>()?.ColumnName) // chon va lay ra column name
                .Where(p => p is not null) // loc nhung phan tu bi null
                .ToList()!;
            propertiesModel = properties.Where(p =>
                    p.GetCustomAttribute<NotMappedAttribute>(true) is null &&
                    p.GetCustomAttribute<ConfigColumnAttribute>(true) is not null)
                .Select(p => p.Name)
                .ToList();
        }

        return (columnsTable, propertiesModel);
    }

    /// <summary>
    /// Lay tat ca cac thuoc tinh co the tim kiem duoc
    /// </summary>
    /// <param name="type"></param>
    /// <returns></returns>
    public static List<string> GetSearchableColumns(this Type type)
    {
        var properties = type.GetProperties();
        return properties.Where(p => p.GetCustomAttribute<ConfigSearchableColumnAttribute>(true) is not null)
            .Select(p => p.GetCustomAttribute<ConfigColumnAttribute>(true)?.ColumnName)
            .Where(colName => colName != null)
            .ToList()!;
    }

    public static string? GetPropertyInModel(this Type type, string column)
    {
        var properties = type.GetProperties();
        return properties.Where(p => p.Name.Equals(column, StringComparison.OrdinalIgnoreCase))
            .Select(p => p.GetCustomAttribute<ConfigColumnAttribute>()?.ColumnName)
            .FirstOrDefault();
    }
}
