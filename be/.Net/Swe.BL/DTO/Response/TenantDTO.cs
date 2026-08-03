using System;

namespace Swe.BL.DTO.Response;

/// <summary>
/// DTO đại diện cho dữ liệu Tenant.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class TenantDTO
{
    public long Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Code { get; set; } = string.Empty;
    public string? Domain { get; set; }
    public string Status { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; }
    public DateTime? ModifiedAt { get; set; }
}
