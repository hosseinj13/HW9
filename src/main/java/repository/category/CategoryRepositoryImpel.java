package repository.category;

import base.repository.BaseRepositoryImpel;
import model.Category;
import model.SubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRepositoryImpel extends BaseRepositoryImpel<Integer, Category> implements CategoryRepository {
    public CategoryRepositoryImpel(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return " maktab.hw9.category ";
    }

    @Override
    public String getColumnsName() {
        return "(category_name)";
    }

    @Override
    public String getEditNameColumn() {
        return null;
    }

    @Override
    public String getIdColumnName() {
        return null;
    }

    @Override
    public String getColumnModelName() {
        return "category_name";
    }

    @Override
    public String getIdFkColumnName() {
        return null;
    }

    @Override
    public String getCountQuestionMarkParams() {
        return "(?)";
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, Category entity, boolean isCountOnly) throws SQLException {
        preparedStatement.setString(1, entity.getCategoryName());
    }

    @Override
    public Category mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("category_id"));
        category.setCategoryName(resultSet.getString("category_name"));
        return category;
    }

    @Override
    public String getUpdateQueryParams() {
        return "category_name";
    }

    @Override
    public Category[] showAllCategories() throws SQLException {
        Category[] categories = new Category[numOfArray()];
        int index = 0;
        String sql = " SELECT * FROM " + getTableName();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = mapResultSetToEntity(resultSet);
                categories[index] = category;
                index++;
            }
        }
        return categories;
    }

    @Override
    public int deleteFromInnerTable(int id) {
        String sql = "DELETE FROM maktab.hw9.subcategory WHERE category_id_fk = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
