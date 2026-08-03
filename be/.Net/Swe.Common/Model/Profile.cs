using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Swe.Common.Attributes;
using Swe.Common.Base;

namespace Swe.Common.Model;

/// <summary>
/// Entity đại diện cho Hồ sơ người dùng (User Profile).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
[ConfigTable("sys_user_profiles")]
public class Profile : BaseSoftDeleteModel
{
    [Key]
    [ConfigColumn("id")]
    public long Id { get; set; }

    [MaxLength(100)]
    [ConfigColumn("first_name")]
    public string? FirstName { get; set; }

    [MaxLength(100)]
    [ConfigColumn("last_name")]
    public string? LastName { get; set; }

    [MaxLength(20)]
    [ConfigColumn("phone_number")]
    public string? PhoneNumber { get; set; }

    [MaxLength(500)]
    [ConfigColumn("avatar_url")]
    public string? AvatarUrl { get; set; }

    [MaxLength(100)]
    [ConfigColumn("position")]
    public string? Position { get; set; }

    [MaxLength(100)]
    [ConfigColumn("department")]
    public string? Department { get; set; }

    [ConfigColumn("user_id")]
    public long UserId { get; set; }
}
