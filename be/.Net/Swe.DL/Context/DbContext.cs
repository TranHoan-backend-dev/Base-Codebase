using System.Data;
using Microsoft.Extensions.Configuration;
using MySqlConnector;

namespace Swe.DL.Context;

public class DbContext(IConfiguration config)
{
    private readonly string _connString = config.GetConnectionString("Swe_AMIS_Conn")!;

    public IDbConnection GetConnection()
    {
        return new MySqlConnection(_connString);
    }
}
