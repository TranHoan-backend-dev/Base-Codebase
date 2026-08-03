namespace Swe.Common.Attributes;

[AttributeUsage(AttributeTargets.Class)]
public class ConfigTableAttribute(string tableName) : Attribute
{
    public string TableName { get; set; } = tableName;
}

[AttributeUsage(AttributeTargets.Property)]
public class ConfigColumnAttribute(string columnName) : Attribute
{
    public string ColumnName { get; set; } = columnName;
}

[AttributeUsage(AttributeTargets.Property)]
public class ConfigSearchableColumnAttribute : Attribute
{
    
}
