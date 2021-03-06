package Repository;

import Entity.City;
import Entity.Country;
import Manager.ActivityManager;
import Manager.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manage the city db
 *
 * Convert the City object to the db or vice versa
 */
public class CityRepository
{
    private DBManager dbManager;
    private ActivityManager activityManager;

    public CityRepository() throws ClassNotFoundException, SQLException {
        dbManager = new DBManager();
        activityManager = new ActivityManager();
    }

    /**
     * Get all the cities
     *
     * @return List of cities
     * @throws SQLException
     */
    public ArrayList<City> getCities() throws SQLException, ClassNotFoundException {
        dbManager.connect();

        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM city INNER JOIN country ON country.id = city.country_id ORDER BY country.name ASC");
        ArrayList<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            Country country = new Country(resultSet.getInt(4), resultSet.getString(5));
            cities.add(new City(resultSet.getInt(1), resultSet.getString(2), country));
        }

        dbManager.closeCurrentStatement();
        dbManager.disconnect();
        return cities;
    }

    /**
     * Insert a new city in the database
     *
     * @param city The new city to insert
     * @return The city with his id
     * @throws SQLException
     */
    public City insertCity(City city) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("INSERT INTO city(name, country_id) VALUES ('"+ city.getName() +"', "+ city.getCountry().getId() +")");

        ResultSet generatedKeys = dbManager.getStatement().getGeneratedKeys();
        if (generatedKeys.next()) {
            city.setId(generatedKeys.getInt(1));
        }

        dbManager.closeCurrentStatement();

        dbManager.disconnect();
        return city;
    }

    /**
     * Get the city by the name of it. It returns the City object with all the activities bound
     *
     * @param name The name of the researched city
     * @return city
     * @throws SQLException
     */
    public City getCityByName(String name) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM city INNER JOIN country ON country.id = city.country_id WHERE city.name='"+ name +"'");
        Country country = new Country(resultSet.getInt(4), resultSet.getString(5));
        City city = new City(resultSet.getInt(1), name, country);

        ResultSet resultSet1 = dbManager.executeQuery(
                "SELECT a.name as act_name, a.id as act_id, a.description as act_desc, a.hours as act_hours, a.minutes as act_min, a.rating as act_rating, ci.name as city_name, co.name as country_name, cat.name as cat_name, cat.id as cat_id " +
                        "FROM activity a " +
                        "INNER JOIN category cat ON cat.id = a.category_id " +
                        "INNER JOIN city ci ON ci.id = a.city_id " +
                        "INNER JOIN country co ON co.id = ci.country_id " +
                        "WHERE a.city_id='"+ city.getId() +"'");
        while (resultSet1.next()) {
            city.addActivity(activityManager.convertResultSet2Activity(resultSet1, city));
        }

        dbManager.closeCurrentStatement();

        dbManager.disconnect();
        return city;
    }

    /**
     * Delete the city
     *
     * @param city The city to delete
     * @throws SQLException
     */
    public void deleteCity(City city) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("DELETE FROM city WHERE id="+ city.getId());
        dbManager.disconnect();
    }
}
