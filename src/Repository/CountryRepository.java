package Repository;

import Entity.City;
import Entity.Country;
import Manager.CountryManager;
import Manager.DBManager;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class CountryRepository
{
    private DBManager dbManager;
    private CountryManager countryManager;

    public CountryRepository() throws ClassNotFoundException, SQLException {
        dbManager = new DBManager();
        countryManager = new CountryManager();
    }

    /**
     * Insert a new country
     *
     * @param country The country to add
     * @return A country with his id
     * @throws SQLException
     */
    public Country insertCountry(Country country) throws SQLException, ClassNotFoundException {
        dbManager.executeUpdate("INSERT INTO country(name) VALUES ('"+ country.getName() +"')");

        ResultSet generatedKey = dbManager.getStatement().getGeneratedKeys();
        if (generatedKey.next()) {
            country.setId(generatedKey.getInt(1));
        }

        return country;
    }

    /**
     * Delete a country by his name
     *
     * @param name The name of the country to delete
     * @throws SQLException
     */
    public void deleteCountryByName(String name) throws SQLException, ClassNotFoundException {
        dbManager.executeUpdate("DELETE FROM country WHERE name='"+ name +"'");
    }

    /**
     * Get all the countries of the platform
     *
     * @return A list of countries
     * @throws SQLException
     */
    public ArrayList<Country> getCountries() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM country ORDER BY name ASC");

        ArrayList<Country> countries = new ArrayList<>();
        while (resultSet.next()) {
            countries.add(countryManager.convertResultSet2Country(resultSet));
        }

        dbManager.closeCurrentStatement();

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
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM country WHERE name='"+ name +"'");

        Country country = countryManager.convertResultSet2Country(resultSet);
        dbManager.closeCurrentStatement();

        ResultSet resultSet1 = dbManager.executeQuery("SELECT * FROM city WHERE country_id="+ country.getId());
        while (resultSet1.next()) {
            country.addCity(new City(resultSet1.getInt(1), resultSet1.getString(2)));
        }
        dbManager.closeCurrentStatement();

        System.out.println(country.toString());
        return country;
    }
}
