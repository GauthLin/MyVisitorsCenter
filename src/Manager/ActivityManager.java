package Manager;

import Entity.Activity;
import Entity.Category;
import Entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The activity manager class
 */
public class ActivityManager
{
    /**
     * Convert the ResultSet of a query to an Activity object
     *
     * @param resultSet The result of the query
     * @param city The city of the activity
     * @return The activity
     * @throws SQLException
     */
    public Activity convertResultSet2Activity(ResultSet resultSet, City city) throws SQLException {
        Activity activity = new Activity(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                new Category(resultSet.getInt(8), resultSet.getString(9)),
                resultSet.getInt(7),
                city
        );
        activity.setID(resultSet.getInt(1));

        return activity;
    }
}
