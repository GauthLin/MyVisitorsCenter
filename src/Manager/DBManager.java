package Manager;

import org.sqlite.SQLiteConfig;

import java.sql.*;

/**
 * The database manager class
 *
 * TODO: Foreign Key doesn't work
 */
public class DBManager
{
    private Connection connection;
    private Statement statement;

    public DBManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.enforceForeignKeys(true);
        connection = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite", sqLiteConfig.toProperties());
    }

    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public void closeCurrentStatement() throws SQLException {
        statement.close();
    }

    public Statement getStatement() {
        return statement;
    }

    /**
     * Prints the resultSet on the terminal
     *
     * @param resultSet The resultSet to print
     * @throws SQLException
     */
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print("  |  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " \"" + rsmd.getColumnName(i) + "\"");
            }
            System.out.println("");
        }
    }
}
