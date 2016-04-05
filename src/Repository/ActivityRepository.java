package Repository;

import Entity.Activity;
import Entity.City;
import Manager.ActivityManager;
import Manager.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class ActivityRepository
{
    private DBManager dbManager;
    private ActivityManager activityManager;

    public ActivityRepository() throws ClassNotFoundException, SQLException {
        dbManager = new DBManager();
        activityManager = new ActivityManager();
    }

    /**
     * Insert a new activity in the database
     *
     * @param activity The activity to add
     * @throws SQLException
     */
    public void insertActivity(Activity activity) throws SQLException, ClassNotFoundException {
        dbManager.executeUpdate("INSERT INTO activity(name, description, hours, minutes, category_id, rating, city_id) VALUES ('"+ activity.getName() +"', '"+ activity.getDescription() +"', "+ activity.getTime().getHours() +", "+ activity.getTime().getMinutes() +", '"+ activity.getCategory().getId() +"', "+ activity.getRating() +", "+ activity.getCity().getId() +")");
    }

    /**
     * Get a list of activities bound to the city
     *
     * @param city The city to search into
     * @return A list of activities
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByCity(City city) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id="+ city.getId() +" ORDER BY activity.name ASC");
        ArrayList<Activity> activities = new ArrayList<>();

        while (resultSet.next()) {
            activities.add(activityManager.convertResultSet2Activity(resultSet, city));
        }

        return activities;
    }

}
