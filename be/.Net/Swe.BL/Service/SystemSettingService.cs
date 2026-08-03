using System;
using System.Threading.Tasks;
using Swe.Common.Enum;
using Swe.Common.Model;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service quản lý Cấu hình hệ thống (System Settings).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class SystemSettingService(SystemSettingRepository repository)
{
    public async Task<SystemSetting?> GetByKeyAsync(string key)
    {
        return await repository.GetByKeyAsync(key);
    }

    public async Task<SystemSetting?> GetByIdAsync(long id)
    {
        return await repository.GetByIdAsync(id);
    }

    public async Task<SystemSetting> CreateAsync(SystemSetting setting)
    {
        if (await repository.CheckDuplicateAsync("setting_key", setting.SettingKey))
        {
            throw new ArgumentException("Khóa cấu hình đã tồn tại");
        }

        await repository.SaveAsync(setting, AppEnum.ModelState.Insert);
        return setting;
    }

    public async Task<SystemSetting> UpdateAsync(SystemSetting setting)
    {
        var existing = await repository.GetByIdAsync(setting.Id);
        if (existing == null)
        {
            throw new ArgumentException("Không tìm thấy cấu hình");
        }

        if (await repository.CheckDuplicateAsync("setting_key", setting.SettingKey, setting.Id))
        {
            throw new ArgumentException("Khóa cấu hình đã tồn tại");
        }

        existing.SettingKey = setting.SettingKey;
        existing.SettingValue = setting.SettingValue;
        existing.Description = setting.Description;

        await repository.SaveAsync(existing, AppEnum.ModelState.Update);
        return existing;
    }

    public async Task DeleteAsync(long id)
    {
        var existing = await repository.GetByIdAsync(id);
        if (existing == null)
        {
            throw new ArgumentException("Không tìm thấy cấu hình");
        }

        await repository.DeleteAsync(id);
    }
}
