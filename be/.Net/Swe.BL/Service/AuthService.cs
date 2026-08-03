using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Swe.Common.Extension;
using Swe.Common.Model;
using Swe.Common.Enum;
using Swe.BL.DTO.Request;
using Swe.BL.DTO.Response;
using Swe.DL.Repository;

namespace Swe.BL.Service;

/// <summary>
/// Service xử lý các nghiệp vụ xác thực Auth (Đăng ký, Đăng nhập, Refresh Token).
/// </summary>
/// <created_at>2026-08-03</created_at>
/// <author>txhoan</author>
public class AuthService(
    UserRepository userRepository,
    TenantRepository tenantRepository,
    ProfileRepository profileRepository,
    JwtTokenProvider jwtTokenProvider)
{
    public async Task<AuthResponse> RegisterAsync(RegisterRequest request)
    {
        if (await userRepository.ExistsByUsernameAsync(request.Username))
        {
            throw new ArgumentException("Tên tài khoản đã tồn tại");
        }
        if (await userRepository.ExistsByEmailAsync(request.Email))
        {
            throw new ArgumentException("Email đã tồn tại");
        }

        // Tìm hoặc tạo mới Tenant
        var tenant = await tenantRepository.GetByCodeAsync(request.TenantCode);
        if (tenant == null)
        {
            tenant = new Tenant
            {
                Name = request.TenantName,
                Code = request.TenantCode,
                Status = AppEnum.EntityStatus.ACTIVE.ToString(),
                CreatedBy = "SYSTEM",
                ModifiedBy = "SYSTEM"
            };
            tenant = await tenantRepository.CreateAsync(tenant);
        }

        // Tạo mới User
        var user = new User
        {
            Username = request.Username,
            Email = request.Email,
            Password = PasswordHasher.HashPassword(request.Password),
            Status = AppEnum.EntityStatus.ACTIVE.ToString(),
            TenantId = tenant.Id,
            CreatedBy = "SYSTEM",
            ModifiedBy = "SYSTEM"
        };
        user = await userRepository.CreateAsync(user);

        // Thêm role mặc định cho user
        await userRepository.AddRoleAsync(user.Id, AppEnum.UserRole.USER.ToString());
        user.Roles.Add(AppEnum.UserRole.USER.ToString());

        // Tạo Profile
        var profile = new Profile
        {
            FirstName = request.FirstName,
            LastName = request.LastName,
            PhoneNumber = request.PhoneNumber,
            UserId = user.Id,
            CreatedBy = "SYSTEM",
            ModifiedBy = "SYSTEM"
        };
        await profileRepository.CreateAsync(profile);

        return await BuildAuthResponseAsync(user, tenant);
    }

    public async Task<AuthResponse> LoginAsync(AuthRequest request)
    {
        var user = await userRepository.GetByUsernameAsync(request.Username);
        if (user == null)
        {
            user = await userRepository.GetByEmailAsync(request.Username);
        }

        if (user == null || !PasswordHasher.VerifyPassword(request.Password, user.Password))
        {
            throw new ArgumentException("Thông tin tài khoản hoặc mật khẩu không chính xác");
        }

        if (user.Status != AppEnum.EntityStatus.ACTIVE.ToString())
        {
            throw new ArgumentException("Tài khoản đã bị khóa hoặc chưa được kích hoạt");
        }

        var tenant = user.TenantId.HasValue ? await tenantRepository.GetByIdAsync(user.TenantId.Value) : null;
        user.Roles = await userRepository.GetRolesAsync(user.Id);

        return await BuildAuthResponseAsync(user, tenant);
    }

    public async Task<AuthResponse> RefreshTokenAsync(RefreshTokenRequest request)
    {
        var token = request.RefreshToken;
        if (!jwtTokenProvider.ValidateToken(token))
        {
            throw new ArgumentException("Refresh token không hợp lệ");
        }

        var username = jwtTokenProvider.ExtractUsername(token);
        if (string.IsNullOrEmpty(username))
        {
            throw new ArgumentException("Refresh token không hợp lệ");
        }

        var user = await userRepository.GetByUsernameAsync(username);
        if (user == null)
        {
            throw new ArgumentException("Không tìm thấy người dùng");
        }

        var tenant = user.TenantId.HasValue ? await tenantRepository.GetByIdAsync(user.TenantId.Value) : null;
        user.Roles = await userRepository.GetRolesAsync(user.Id);

        return await BuildAuthResponseAsync(user, tenant);
    }

    public async Task<UserDTO> GetCurrentUserAsync(string username)
    {
        var user = await userRepository.GetByUsernameAsync(username);
        if (user == null)
        {
            throw new ArgumentException("Không tìm thấy người dùng");
        }

        var profile = await profileRepository.GetByUserIdAsync(user.Id);
        var tenant = user.TenantId.HasValue ? await tenantRepository.GetByIdAsync(user.TenantId.Value) : null;
        var roles = await userRepository.GetRolesAsync(user.Id);

        var profileDto = profile != null ? new ProfileDTO
        {
            Id = profile.Id,
            FirstName = profile.FirstName,
            LastName = profile.LastName,
            PhoneNumber = profile.PhoneNumber,
            AvatarUrl = profile.AvatarUrl,
            Position = profile.Position,
            Department = profile.Department,
            UserId = user.Id,
            CreatedAt = profile.CreatedAt,
            ModifiedAt = profile.ModifiedAt
        } : null;

        return new UserDTO
        {
            Id = user.Id,
            Username = user.Username,
            Email = user.Email,
            Status = user.Status,
            TenantId = user.TenantId,
            TenantCode = tenant?.Code,
            Roles = roles,
            Profile = profileDto,
            CreatedAt = user.CreatedAt,
            ModifiedAt = user.ModifiedAt
        };
    }

    private async Task<AuthResponse> BuildAuthResponseAsync(User user, Tenant? tenant)
    {
        var roles = user.Roles.Count > 0 ? user.Roles : await userRepository.GetRolesAsync(user.Id);
        var accessToken = jwtTokenProvider.GenerateAccessToken(
            user.Id, user.Username, user.Email, user.TenantId, tenant?.Code, roles);
        var refreshToken = jwtTokenProvider.GenerateRefreshToken(user.Username);

        return new AuthResponse
        {
            AccessToken = accessToken,
            RefreshToken = refreshToken,
            TokenType = "Bearer",
            ExpiresIn = 3600,
            UserId = user.Id,
            Username = user.Username,
            Email = user.Email,
            TenantId = user.TenantId,
            TenantCode = tenant?.Code,
            Roles = roles
        };
    }
}
