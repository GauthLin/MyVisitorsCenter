package Manager;

import Entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The country manager class
 */
public class CountryManager
{
    /**
     * Convert the resultSet to a Country Object
     *
     * @param resultSet The ResultSet to convert
     * @return The country based on the resultSet
     * @throws SQLException
     */
    public Country convertResultSet2Country(ResultSet resultSet) throws SQLException {
        return new Country(
                resultSet.getInt(1),
                resultSet.getString(2)
        );
    }
}
