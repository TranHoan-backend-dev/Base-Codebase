using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using Swe.BL.Service;
using Swe.DL.Repository;
using Dapper;
using Swe.Common.Extension;
using Swe.BL.Base;
using Swe.DL.Base;
using Swe.DL.Context;
using Swe.API.Exception;
using Serilog;
using Serilog.Sinks.SystemConsole.Themes;

var builder = WebApplication.CreateBuilder(args);

// Register Dapper TypeHandlers
SqlMapper.AddTypeHandler(new GuidTypeHandler());
DefaultTypeMap.MatchNamesWithUnderscores = true;

// Add services to the container.
builder.Services.AddOpenApi();
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuerSigningKey = true,
        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("SuperSecretKeyForSigningJwtTokensDoNotUseThisInProduction")),
        ValidateIssuer = false,
        ValidateAudience = false,
        ClockSkew = TimeSpan.Zero
    };
});
builder.Services.AddAuthorization();

builder.Services.AddScoped<DbContext>();

builder.Services.AddSingleton<JwtTokenProvider>();
builder.Services.AddScoped<UserRepository>();
builder.Services.AddScoped<TenantRepository>();
builder.Services.AddScoped<ProfileRepository>();
builder.Services.AddScoped<AuthService>();
builder.Services.AddScoped<UserService>();
builder.Services.AddScoped<TenantService>();

builder.Services.AddExceptionHandler<GlobalExceptionHandler>();
builder.Services.AddProblemDetails();

# region Theme for console log
var theme = new AnsiConsoleTheme(new Dictionary<ConsoleThemeStyle, string>
{
    [ConsoleThemeStyle.Text] = "\x1b[37m", // trắng
    [ConsoleThemeStyle.SecondaryText] = "\x1b[30m", // xám
    [ConsoleThemeStyle.TertiaryText] = "\x1b[90m",

    [ConsoleThemeStyle.Name] = "\x1b[36m", // cyan
    [ConsoleThemeStyle.String] = "\x1b[32m", // xanh lá

    [ConsoleThemeStyle.LevelInformation] = "\x1b[32m",
    [ConsoleThemeStyle.LevelWarning] = "\x1b[33m",
    [ConsoleThemeStyle.LevelError] = "\x1b[31m",
    [ConsoleThemeStyle.LevelFatal] = "\x1b[41m", // nền đỏ
    [ConsoleThemeStyle.LevelDebug] = "\x1b[45m",
});
Log.Logger = new LoggerConfiguration()
    .MinimumLevel.Information()
    .WriteTo.Console(theme: theme,
        outputTemplate:
        "[{Timestamp:HH:mm:ss} {Level:u3}] [{SourceContext}] {Message}{NewLine}{Exception}")
    .CreateLogger();
#endregion

#region CORS config

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowSpecificOrigins", policy =>
    {
        policy.WithOrigins("http://localhost:3000")
        .AllowAnyHeader()
        .AllowAnyMethod();
    });

    options.AddPolicy("AllowAllDev", policy =>
    {
        policy.AllowAnyOrigin()
        .AllowAnyHeader()
        .AllowAnyMethod();
    });
});

#endregion

builder.Host.UseSerilog();

var app = builder.Build();

app.UseCors("AllowSpecificOrigins");

app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();

app.UseExceptionHandler();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.MapControllers();


// app.UseHttpsRedirection();


app.Run();
