package Entity;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Database manager
 */
public class DBManager
{
    private Connection connexion;
    private Statement statement;

    public DBManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            this.connexion = DriverManager.getConnection("jdbc:sqlite:myvisitorscenter.sqlite");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        try {
            this.statement = connexion.createStatement();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /*
     * COUNTRY
     */

    /**
     * Insert a new country
     *
     * @param country The country to add
     * @return A country with his id
     * @throws SQLException
     *
     * TODO: get the last id
     */
    public Country insertCountry(Country country) throws SQLException {
        this.statement.executeUpdate("INSERT INTO country(name) VALUES ('"+ country.getName() +"')");

        this.connexion.close();

        return country;
    }

    /**
     * Delete a country by his name
     *
     * @param name The name of the country to delete
     * @throws SQLException
     */
    public void deleteCountryByName(String name) throws SQLException {
        this.statement.executeUpdate("DELETE FROM country WHERE name='"+ name +"'");
        this.connexion.close();
    }

    /**
     * Get all the countries of the platform
     *
     * @return A list of countries
     * @throws SQLException
     */
    public ArrayList<Country> getCountries() throws SQLException {
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM country ORDER BY name ASC");

        ArrayList<Country> countries = new ArrayList<>();
        while (resultSet.next()) {
            countries.add(convertResultSet2Country(resultSet));
        }

        this.connexion.close();
        return countries;
    }

    /**
     * Get a country by his name
     *
     * @param name The name of the country
     * @return A country
     * @throws SQLException
     */
    public Country getCountryByName(String name) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM country WHERE name='"+ name +"'");
        Country country = convertResultSet2Country(resultSet);
        this.connexion.close();

        return country;
    }

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

    /*
     * CITY
     */

    /**
     * Get all the cities
     *
     * @return List of cities
     * @throws SQLException
     */
    public ArrayList<City> getCities() throws SQLException {
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM city");
        ArrayList<City> cities = new ArrayList<>();

        while (resultSet.next()) {
            cities.add(new City(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
        }

        this.connexion.close();

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
        this.statement.executeUpdate("INSERT INTO city(name, country_id) VALUES ('"+ city.getName() +"', "+ city.getCountryId() +")");

        City newCity = new City(city.getName(), city.getCountryId());
        this.connexion.close();

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
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM city WHERE name='"+ name +"'");
        City city = new City(resultSet.getInt(1), name, resultSet.getInt(3));

        ResultSet resultSet1 = this.statement.executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id='"+ city.getId() +"'");
        while (resultSet1.next()) {
            city.addActivity(convertResultSet2Activity(resultSet1, city));
        }

        this.connexion.close();

        return city;
    }

    /**
     * Delete the city
     *
     * @param city The city to delete
     * @throws SQLException
     */
    public void deleteCity(City city) throws SQLException {
        this.statement.executeUpdate("DELETE FROM city WHERE id="+ city.getId());

        this.connexion.close();
    }

    /*
     * ACTIVITY
     */

    /**
     * Insert a new activity in the database
     *
     * @param activity The activity to add
     * @throws SQLException
     */
    public void insertActivity(Activity activity) throws SQLException {
        this.statement.executeUpdate("INSERT INTO activity(name, description, hours, minutes, category_id, rating, city_id) VALUES ('"+ activity.getName() +"', '"+ activity.getDescription() +"', "+ activity.getTime().getHours() +", "+ activity.getTime().getMinutes() +", '"+ activity.getCategory().getId() +"', "+ activity.getRating() +", "+ activity.getCity().getId() +")");

        this.connexion.close();
    }

    /**
     * Get a list of activities bound to the city
     *
     * @param city The city to search into
     * @return A list of activities
     * @throws SQLException
     */
    public ArrayList<Activity> getActivitiesByCity(City city) throws SQLException {
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id WHERE city_id="+ city.getId() +" ORDER BY activity.name ASC");
        ArrayList<Activity> activities = new ArrayList<>();

        while (resultSet.next()) {
            activities.add(convertResultSet2Activity(resultSet, city));
        }

        this.connexion.close();

        return activities;
    }

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

    /*
     * CATEGORY of an activity
     */

    /**
     * Insert a new category
     *
     * @param category The category to add
     * @return Category
     * @throws SQLException
     *
     * TODO Get the last insert id
     */
    public Category insertCategory(Category category) throws SQLException {
        this.statement.executeUpdate("INSERT INTO category(name) VALUES ('"+ category.getName() +"')");

        this.connexion.close();

        return category;
    }

    /**
     * Delete a category by his name
     *
     * @param name The name of the category to delete
     * @throws SQLException
     */
    public void deleteCategoryByName(String name) throws SQLException {
        this.statement.executeUpdate("DELETE FROM category WHERE name='"+ name +"'");
        this.connexion.close();
    }

    /**
     * Get all the categories
     *
     * @return A list of all the categories
     * @throws SQLException
     */
    public ArrayList<Category> getCategories() throws SQLException {
        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM category ORDER BY name ASC");

        ArrayList<Category> categories = new ArrayList<>();

        while (resultSet.next()) {
            categories.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
        }

        this.connexion.close();

        return categories;
    }
}
