package Manager;

import org.sqlite.SQLiteConfig;

import java.sql.*;

/**
 * The database manager class
 */
public class DBManager
{
    private Connection connection;
    private Statement statement;

    /**
     * Connects to the db
     */
    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);
            connection = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite", sqLiteConfig.toProperties());
        }catch(Exception e){
            System.out.println("Problème connexion : "+e);
        }
    }

    /**
     * Disconnects from the db
     */
    public void disconnect(){
        try {
            connection.close();
        } catch(Exception e){
            System.out.println("Problème déconnexion : "+e);
        }
    }

    /**
     * Executes a query
     *
     * @param sql The query to execute
     * @return the ResultSet of the query
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
        statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    /**
     * Executes an update
     *
     * @param sql The query
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    /**
     * Closes the current statement
     *
     * @throws SQLException
     */
    public void closeCurrentStatement() throws SQLException {
        statement.close();
    }

    /**
     * Get the current statement
     *
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Prints the any resultSet on the terminal. It was used to debug the db connection and queries
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
