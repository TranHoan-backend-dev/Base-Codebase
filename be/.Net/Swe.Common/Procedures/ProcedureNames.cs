namespace Swe.Common.Procedures;

public class ProcedureNames
{
    private const string Prefix = "Proc_{0}_";
    public static readonly string Insert = string.Concat(Prefix, "Insert");
    public static readonly string Update = string.Concat(Prefix, "Update");
    public static readonly string Delete = string.Concat(Prefix, "Delete");
    public static readonly string GetAll = string.Concat(Prefix, "GetAll");
    public static readonly string GetDetails = string.Concat(Prefix, "GetById");
    public static readonly string CheckDuplicate = string.Concat(Prefix, "CheckDuplicate");
}
