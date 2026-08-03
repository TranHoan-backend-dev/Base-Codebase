using System;
using System.Threading.Tasks;
using Swe.Common.Enum;
using Swe.Common.Model;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service quản lý Hồ sơ người dùng (User Profile).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class ProfileService(ProfileRepository repository)
{
    public async Task<Profile?> GetByUserIdAsync(long userId)
    {
        return await repository.GetByUserIdAsync(userId);
    }

    public async Task<Profile> UpdateAsync(Profile profile)
    {
        var existing = await repository.GetByUserIdAsync(profile.UserId);
        if (existing == null)
        {
            // Nếu chưa có profile, tạo mới luôn
            profile.CreatedAt = DateTime.UtcNow;
            profile.ModifiedAt = DateTime.UtcNow;
            profile.CreatedBy = "SYSTEM";
            profile.ModifiedBy = "SYSTEM";
            await repository.SaveAsync(profile, AppEnum.ModelState.Insert);
            return profile;
        }

        existing.FirstName = profile.FirstName;
        existing.LastName = profile.LastName;
        existing.PhoneNumber = profile.PhoneNumber;
        existing.AvatarUrl = profile.AvatarUrl;
        existing.Position = profile.Position;
        existing.Department = profile.Department;
        existing.ModifiedAt = DateTime.UtcNow;
        existing.ModifiedBy = "SYSTEM";

        await repository.SaveAsync(existing, AppEnum.ModelState.Update);
        return existing;
    }
}
