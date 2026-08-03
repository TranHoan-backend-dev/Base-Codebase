using System;
using System.Threading.Tasks;
using Swe.Common.Model;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service quản lý User (Tài khoản người dùng).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class UserService(UserRepository userRepository)
{
    public async Task<User?> GetByIdAsync(long id)
    {
        return await userRepository.GetByIdAsync(id);
    }

    public async Task<User?> GetByUsernameAsync(string username)
    {
        return await userRepository.GetByUsernameAsync(username);
    }

    public async Task<User?> GetByEmailAsync(string email)
    {
        return await userRepository.GetByEmailAsync(email);
    }
}
