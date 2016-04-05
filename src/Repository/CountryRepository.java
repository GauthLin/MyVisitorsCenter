package Repository;

import Entity.Country;
import Manager.CountryManager;
import Manager.DBManager;

import java.sql.*;
import java.util.ArrayList;

public class CountryRepository
{
    private DBManager dbManager;
    private CountryManager countryManager;

    public CountryRepository() throws ClassNotFoundException, SQLException {
        this.dbManager = new DBManager();
        this.countryManager = new CountryManager();
    }

    /**
     * Insert a new country
     *
     * @param country The country to add
     * @return A country with his id
     * @throws SQLException
     *
     * TODO: get the last id
     */
    public Country insertCountry(Country country) throws SQLException, ClassNotFoundException {
        this.dbManager.executeUpdate("INSERT INTO country(name) VALUES ('"+ country.getName() +"')");

        return country;
    }

    /**
     * Delete a country by his name
     *
     * @param name The name of the country to delete
     * @throws SQLException
     */
    public void deleteCountryByName(String name) throws SQLException, ClassNotFoundException {
        this.dbManager.executeUpdate("DELETE FROM country WHERE name='"+ name +"'");
    }

    /**
     * Get all the countries of the platform
     *
     * @return A list of countries
     * @throws SQLException
     */
    public ArrayList<Country> getCountries() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = this.dbManager.executeQuery("SELECT * FROM country ORDER BY name ASC");

        ArrayList<Country> countries = new ArrayList<>();
        while (resultSet.next()) {
            countries.add(countryManager.convertResultSet2Country(resultSet));
        }
        return countries;
    }

    /**
     * Get a country by his name
     *
     * @param name The name of the country
     * @return A country
     * @throws SQLException
     */
    public Country getCountryByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = this.dbManager.executeQuery("SELECT * FROM country WHERE name='"+ name +"'");

        return countryManager.convertResultSet2Country(resultSet);
    }
}
