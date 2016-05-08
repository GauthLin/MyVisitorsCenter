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
                resultSet.getString("act_name"),
                resultSet.getString("act_desc"),
                resultSet.getInt("act_hours"),
                resultSet.getInt("act_min"),
                new Category(resultSet.getInt("cat_id"), resultSet.getString("cat_name")),
                resultSet.getInt("act_rating"),
                city
        );
        activity.setID(resultSet.getInt("act_id"));

        return activity;
    }
}
