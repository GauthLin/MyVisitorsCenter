package Repository;

import Entity.Category;
import Manager.DBManager;

import java.sql.*;
import java.util.ArrayList;

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
     *
     * TODO Get the last insert id
     */
    public Category insertCategory(Category category) throws SQLException {
        this.dbManager.getStatement().executeUpdate("INSERT INTO category(name) VALUES ('"+ category.getName() +"')");

        this.dbManager.closeConnection();

        return category;
    }

    /**
     * Delete a category by his name
     *
     * @param name The name of the category to delete
     * @throws SQLException
     */
    public void deleteCategoryByName(String name) throws SQLException {
        this.dbManager.getStatement().executeUpdate("DELETE FROM category WHERE name='"+ name +"'");
        this.dbManager.closeConnection();
    }

    /**
     * Get all the categories
     *
     * @return A list of all the categories
     * @throws SQLException
     */
    public ArrayList<Category> getCategories() throws SQLException {
        ResultSet resultSet = this.dbManager.getStatement().executeQuery("SELECT * FROM category ORDER BY name ASC");

        ArrayList<Category> categories = new ArrayList<>();

        while (resultSet.next()) {
            categories.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
        }

        this.dbManager.closeConnection();

        return categories;
    }
}
