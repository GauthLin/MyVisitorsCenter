package Repository;

import Entity.Activity;
import Entity.City;
import Entity.Country;
import Manager.ActivityManager;
import Manager.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Activity getActivityById(String id) throws SQLException, ClassNotFoundException {
        dbManager.connect();

        ResultSet resultSet = dbManager.executeQuery(
                "SELECT a.name as act_name, a.id as act_id, a.description as act_desc, a.hours as act_hours, a.minutes as act_min, a.rating as act_rating, ci.name as city_name, ci.id as city_id, co.name as country_name, co.id as country_id, cat.name as cat_name, cat.id as cat_id " +
                        "FROM activity a " +
                        "INNER JOIN category cat ON cat.id = a.category_id " +
                        "INNER JOIN city ci ON ci.id = a.city_id " +
                        "INNER JOIN country co ON co.id = ci.country_id " +
                        "WHERE a.id = " + id + " " +
                        "ORDER BY a.id ASC"
        );

        City city = new City(
                resultSet.getInt("city_id"),
                resultSet.getString("city_name"),
                new Country(resultSet.getInt("country_id"), resultSet.getString("country_name"))
        );
        Activity activity = activityManager.convertResultSet2Activity(resultSet, city);

        dbManager.closeCurrentStatement();
        dbManager.disconnect();

        return activity;
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

        ResultSet resultSet = dbManager.executeQuery(
                "SELECT a.name as act_name, a.id as act_id, a.description as act_desc, a.hours as act_hours, a.minutes as act_min, a.rating as act_rating, ci.name as city_name, co.name as country_name, cat.name as cat_name, cat.id as cat_id " +
                        "FROM activity a " +
                        "INNER JOIN category cat ON cat.id = a.category_id " +
                        "INNER JOIN city ci ON ci.id = a.city_id " +
                        "INNER JOIN country co ON co.id = ci.country_id " +
                        "WHERE city_id="+ city.getId() + " " +
                        "ORDER BY a.rating DESC"
        );
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
                "SELECT a.name as act_name, a.id as act_id, a.description as act_desc, a.hours as act_hours, a.minutes as act_min, a.rating as act_rating, ci.name as city_name, ci.id as city_id, co.name as country_name, co.id as country_id, cat.name as cat_name, cat.id as cat_id " +
                        "FROM activity a " +
                        "INNER JOIN category cat ON cat.id = a.category_id " +
                        "INNER JOIN city ci ON ci.id = a.city_id " +
                        "INNER JOIN country co ON co.id = ci.country_id " +
                        "ORDER BY a.id ASC"
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
