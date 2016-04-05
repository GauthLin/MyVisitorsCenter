package Repository;

import Entity.City;
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
        this.dbManager = new DBManager();
        this.activityManager = new ActivityManager();
    }

    /**
     * Get all the cities
     *
     * @return List of cities
     * @throws SQLException
     */
    public ArrayList<City> getCities() throws SQLException {
        ResultSet resultSet = this.dbManager.getStatement().executeQuery("SELECT * FROM city");
        ArrayList<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            cities.add(new City(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
        }

        this.dbManager.closeConnection();

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
    public City insertCity(City city) throws SQLException {
        this.dbManager.getStatement().executeUpdate("INSERT INTO city(name, country_id) VALUES ('"+ city.getName() +"', "+ city.getCountryId() +")");

        City newCity = new City(city.getName(), city.getCountryId());
        this.dbManager.closeConnection();

        return newCity;
    }

    /**
     * Get the city by the name of it. It returns the City object with all the activities bound
     *
     * @param name The name of the researched city
     * @return city
     * @throws SQLException
     */
    public City getCityByName(String name) throws SQLException {
        ResultSet resultSet = this.dbManager.getStatement().executeQuery("SELECT * FROM city WHERE name='"+ name +"'");
        City city = new City(resultSet.getInt(1), name, resultSet.getInt(3));

        ResultSet resultSet1 = this.dbManager.getStatement().executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id='"+ city.getId() +"'");
        while (resultSet1.next()) {
            city.addActivity(activityManager.convertResultSet2Activity(resultSet1, city));
        }

        this.dbManager.closeConnection();

        return city;
    }

    /**
     * Delete the city
     *
     * @param city The city to delete
     * @throws SQLException
     */
    public void deleteCity(City city) throws SQLException {
        this.dbManager.getStatement().executeUpdate("DELETE FROM city WHERE id="+ city.getId());

        this.dbManager.closeConnection();
    }
}
