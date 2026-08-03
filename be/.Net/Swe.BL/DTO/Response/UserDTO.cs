using System;
using System.Collections.Generic;

namespace Swe.BL.DTO.Response;

/// <summary>
/// DTO đại diện cho dữ liệu User.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class UserDTO
{
    public long Id { get; set; }
    public string Username { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Status { get; set; } = string.Empty;
    public long? TenantId { get; set; }
    public string? TenantCode { get; set; }
    public List<string> Roles { get; set; } = new();
    public ProfileDTO? Profile { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? ModifiedAt { get; set; }
}
