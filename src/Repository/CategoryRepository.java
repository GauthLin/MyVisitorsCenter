package Repository;

import Entity.Category;
import Manager.DBManager;

import java.sql.*;
import java.util.ArrayList;

/**
 * Manage the category db
 *
 * Convert the category object to the db or vice versa
 */
public class CategoryRepository
{
    private DBManager dbManager;

    public CategoryRepository() throws ClassNotFoundException, SQLException {
        dbManager = new DBManager();
    }

    /**
     * Insert a new category
     *
     * @param category The category to add
     * @return Category
     * @throws SQLException
     */
    public Category insertCategory(Category category) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("INSERT INTO category(name) VALUES ('"+ category.getName() +"')");

        ResultSet generatedKeys = dbManager.getStatement().getGeneratedKeys();
        if (generatedKeys.next()) {
            category.setId(generatedKeys.getInt(1));
        }

        dbManager.disconnect();

        return category;
    }

    /**
     * Delete a category by his name
     *
     * @param name The name of the category to delete
     * @throws SQLException
     */
    public void deleteCategoryByName(String name) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        dbManager.executeUpdate("DELETE FROM category WHERE name='"+ name +"'");
        dbManager.disconnect();
    }

    /**
     * Get a category by his name
     *
     * @param name The name of the category
     * @return The category found
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Category getCategoryByName(String name) throws SQLException, ClassNotFoundException {
        dbManager.connect();
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM category WHERE name='"+ name +"'");

        Category category = new Category(resultSet.getInt(1), resultSet.getString((2)));

        dbManager.closeCurrentStatement();
        dbManager.disconnect();

        return category;
    }

    /**
     * Get all the categories
     *
     * @return A list of all the categories
     * @throws SQLException
     */
    public ArrayList<Category> getCategories() throws SQLException, ClassNotFoundException {
        dbManager.connect();
        ResultSet resultSet = dbManager.executeQuery("SELECT * FROM category ORDER BY name ASC");

        ArrayList<Category> categories = new ArrayList<>();

        while (resultSet.next()) {
            categories.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
        }

        dbManager.closeCurrentStatement();
        dbManager.disconnect();

        return categories;
    }
}
