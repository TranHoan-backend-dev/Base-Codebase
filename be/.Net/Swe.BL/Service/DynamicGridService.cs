using System.Threading.Tasks;
using Swe.Common.Model;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service quản lý Cấu hình bảng động (Dynamic Grid).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class DynamicGridService(DynamicGridRepository repository)
{
    public async Task<DynamicGridConfig?> GetGridConfigAsync(string gridKey)
    {
        return await repository.GetByGridKeyAsync(gridKey);
    }
}
