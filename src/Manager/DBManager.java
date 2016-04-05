package Manager;

import java.sql.*;

/**
 * The database manager class
 */
public class DBManager
{
    private Connection connection;

    public DBManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite");
    }

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        return connection.createStatement().executeQuery(sql);
    }

    public void executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        connection.createStatement().executeUpdate(sql);
    }
}
