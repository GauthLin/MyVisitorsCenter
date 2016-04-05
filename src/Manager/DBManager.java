package Manager;

import java.sql.*;

/**
 * The database manager class
 *
 * To create a connection, you must open a connection, execute your updates and then close the connection to not block the database
 *
 * TODO: save the resultSet to close the db connection
 */
public class DBManager
{
    private Connection connection;
    private Statement statement;

    public DBManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite");
        statement = connection.createStatement();
    }

//    public void openConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        connection = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite");
//        statement = connection.createStatement();
//    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        return connection.createStatement().executeQuery(sql);
    }

    public void executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        connection.createStatement().executeUpdate(sql);
    }
}
