using System;

namespace Swe.Common.Model;

/// <summary>
/// Lớp wrapper dùng để đóng gói 1 response trả về client.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class WrapperApiResponse
{
    public int Status { get; set; }
    public string Message { get; set; } = string.Empty;
    public object? Data { get; set; }
    public DateTime Timestamp { get; set; } = DateTime.UtcNow;

    public WrapperApiResponse() { }

    public WrapperApiResponse(int status, string message, object? data)
    {
        Status = status;
        Message = message;
        Data = data;
        Timestamp = DateTime.UtcNow;
    }
}
