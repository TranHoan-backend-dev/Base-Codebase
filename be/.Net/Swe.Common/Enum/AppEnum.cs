namespace Swe.Common.Enum;

public static class AppEnum
{
    public enum StatusCode : int
    {
        Success = 200,
        BadRequest = 400,
        Created = 201,
        NoContent = 204,
        NotFound = 404,
        Unauthorized = 401,
        Forbidden = 403,
        InternalServerError = 500,
    }

    public enum ModelState : int
    {
        None = 0,
        Insert = 1,
        Update = 2,
        Delete = 3
    }

    public enum FilterType
    {
        Contains = 0,
        NotContains = 1,
        StartWith = 2,
        EndWith = 3,
        Equals = 4,
        NotEquals = 5,
        GreaterThanOrEqual = 6,
        LessThanOrEqual = 7,
    }

    public enum DataType : int
    {
        String = 0,
        DateTime = 1
    }

    public static class Operand
    {
        public const string NotLike = "NOT LIKE";
        public const string Like = "LIKE";
        public const string Equal = "=";
        public const string NotEqual = "<>";
        public const string GreaterThanOrEqual = ">=";
        public const string LessThanOrEqual = "<=";
        public const string And = " AND ";
        public const string Or = " OR ";
    }

    public enum EntityStatus
    {
        ACTIVE,
        INACTIVE,
        PENDING
    }

    public enum UserRole
    {
        SUPER_ADMIN,
        ADMIN,
        USER
    }

    public enum ColumnDataType
    {
        TEXT,
        NUMBER,
        DATE,
        DATETIME,
        BOOLEAN,
        ENUM,
        BADGE,
        LINK,
        ACTION
    }

    public enum PinnedPosition
    {
        LEFT,
        RIGHT
    }
}
