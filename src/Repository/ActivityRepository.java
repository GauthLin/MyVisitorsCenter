package Repository;

import Entity.Activity;
import Entity.City;
import Entity.Country;
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
    public Activity insertActivity(Activity activity) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("INSERT INTO activity(name, description, hours, minutes, category_id, rating, city_id) VALUES ('"+ activity.getName() +"', '"+ activity.getDescription() +"', "+ activity.getTime().getHours() +", "+ activity.getTime().getMinutes() +", '"+ activity.getCategory().getId() +"', "+ activity.getRating() +", "+ activity.getCity().getId() +")");

        ResultSet generatedKeys = dbManager.getStatement().getGeneratedKeys();
        if (generatedKeys.next()) {
            activity.setID(generatedKeys.getInt(1));
        }
        dbManager.disconnect();

        return activity;
    }

    /**
     * Deletes the activity by his id
     * @param id The id of the activity to delete
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void deleteActivityById(String id) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("DELETE FROM activity WHERE id="+ id);
        dbManager.disconnect();
    }

    /**
     * Get a list of activities bound to the city
     *
     * @param city The city to search into
     * @return A list of activities
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByCity(City city) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id="+ city.getId() +" ORDER BY activity.name ASC");
        ArrayList<Activity> activities = new ArrayList<>();

        while (resultSet.next()) {
            activities.add(activityManager.convertResultSet2Activity(resultSet, city));
        }

        dbManager.closeCurrentStatement();
        dbManager.disconnect();

        return activities;
    }

    /**
     * Get a list of all the activities
     *
     * @return A list of activities
     * @throws SQLException
     */
    public ArrayList<Activity> getActivities() throws SQLException, ClassNotFoundException {
        dbManager.connect();
        ResultSet resultSet = dbManager.executeQuery(
                "SELECT *, city.name as city_name, country.name as country_name FROM activity " +
                        "INNER JOIN category ON category.id = activity.category_id " +
                        "INNER JOIN city ON city.id = activity.city_id " +
                        "INNER JOIN country ON country.id = city.country_id " +
                        "ORDER BY activity.id ASC"
        );
        ArrayList<Activity> activities = new ArrayList<>();

        while (resultSet.next()) {
            City city = new City(
                    resultSet.getInt("city_id"),
                    resultSet.getString("city_name"),
                    new Country(resultSet.getInt("country_id"), resultSet.getString("country_name"))
            );
            activities.add(activityManager.convertResultSet2Activity(resultSet, city));
        }

        dbManager.closeCurrentStatement();
        dbManager.disconnect();

        return activities;
    }

}
