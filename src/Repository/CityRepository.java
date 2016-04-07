package Repository;

import Entity.City;
import Entity.Country;
import Manager.ActivityManager;
import Manager.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM city INNER JOIN country ON country.id = city.country_id");
        ArrayList<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            Country country = new Country(resultSet.getInt(4), resultSet.getString(5));
            cities.add(new City(resultSet.getInt(1), resultSet.getString(2), country));
        }

        return cities;
    }

    /**
     * Insert a new city in the database
     *
     * @param city The new city to insert
     * @return The city with his id
     * @throws SQLException
     *
     * TODO: Get the last insert id
     */
    public City insertCity(City city) throws SQLException, ClassNotFoundException {
        dbManager.executeUpdate("INSERT INTO city(name, country_id) VALUES ('"+ city.getName() +"', "+ city.getCountry().getId() +")");

        City newCity = new City(city.getName(), city.getCountry());

        return newCity;
    }

    /**
     * Get the city by the name of it. It returns the City object with all the activities bound
     *
     * @param name The name of the researched city
     * @return city
     * @throws SQLException
     */
    public City getCityByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM city INNER JOIN country ON country.id = city.country_id WHERE name='"+ name +"'");
        Country country = new Country(resultSet.getInt(4), resultSet.getString(5));
        City city = new City(resultSet.getInt(1), name, country);

        ResultSet resultSet1 = dbManager.executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id='"+ city.getId() +"'");
        while (resultSet1.next()) {
            city.addActivity(activityManager.convertResultSet2Activity(resultSet1, city));
        }

        return city;
    }

    /**
     * Delete the city
     *
     * @param city The city to delete
     * @throws SQLException
     */
    public void deleteCity(City city) throws SQLException, ClassNotFoundException {
        dbManager.executeUpdate("DELETE FROM city WHERE id="+ city.getId());
    }
}
