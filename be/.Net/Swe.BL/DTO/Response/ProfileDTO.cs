using System;

namespace Swe.BL.DTO.Response;

/// <summary>
/// DTO đại diện cho dữ liệu Hồ sơ người dùng.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class ProfileDTO
{
    public long Id { get; set; }
    public string? FirstName { get; set; }
    public string? LastName { get; set; }
    public string? PhoneNumber { get; set; }
    public string? AvatarUrl { get; set; }
    public string? Position { get; set; }
    public string? Department { get; set; }
    public long UserId { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? ModifiedAt { get; set; }
}
