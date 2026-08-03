using System;
using System.Security.Cryptography;
using System.Text;

namespace Swe.Common.Extension;

/// <summary>
/// Lớp tiện ích mã hóa mật khẩu an toàn.
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public static class PasswordHasher
{
    public static string HashPassword(string password)
    {
        var bytes = Encoding.UTF8.GetBytes(password);
        var hash = SHA256.HashData(bytes);
        return Convert.ToBase64String(hash);
    }

    public static bool VerifyPassword(string password, string hashedPassword)
    {
        return HashPassword(password) == hashedPassword;
    }
}
