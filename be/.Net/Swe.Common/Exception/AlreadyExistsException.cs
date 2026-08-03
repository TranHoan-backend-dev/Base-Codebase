namespace Swe.Common.Exception;

public abstract class AlreadyExistsException : System.Exception
{
    protected AlreadyExistsException() : base("Resource already exists")
    {
    }

    protected AlreadyExistsException(string message) : base(message)
    {
    }

    protected AlreadyExistsException(string message, System.Exception? innerException) : base(message, innerException)
    {
    }
}
