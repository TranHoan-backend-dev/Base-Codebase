using System;
using System.Threading.Tasks;
using Swe.Common.Model;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service quản lý Tenant (Công ty / Tổ chức).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class TenantService(TenantRepository tenantRepository)
{
    public async Task<Tenant?> GetByIdAsync(long id)
    {
        return await tenantRepository.GetByIdAsync(id);
    }

    public async Task<Tenant?> GetByCodeAsync(string code)
    {
        return await tenantRepository.GetByCodeAsync(code);
    }

    public async Task<Tenant> CreateAsync(Tenant tenant)
    {
        return await tenantRepository.CreateAsync(tenant);
    }
}
