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
