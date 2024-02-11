package repository.subCategory;

import base.repository.BaseRepositoryImpel;
import model.*;
import service.category.CategoryService;
import utility.ApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubCategoryRepositoryImpel extends BaseRepositoryImpel<Integer, SubCategory> implements SubCategoryRepository {
    public SubCategoryRepositoryImpel(Connection connection) {
        super(connection);
    }

    CategoryService categoryService = ApplicationContext.getCategoryServiceImpel();

    @Override
    public String getTableName() {
        return "maktab.hw9.subcategory";
    }

    @Override
    public String getColumnsName() {
        return "(subcategory_name, category_id_fk)";
    }

    @Override
    public String getEditNameColumn() {
        return "subcategory_name";
    }

    @Override
    public String getIdColumnName() {
        return null;
    }

    @Override
    public String getColumnModelName() {
        return "subcategory_id";
    }

    @Override
    public String getIdFkColumnName() {
        return "category_id_fk";
    }

    @Override
    public String getCountQuestionMarkParams() {
        return "(?, ?)";
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, SubCategory subCategory, boolean isCountOnly) throws SQLException {
        preparedStatement.setString(1, subCategory.getSubCategoryName());
        Category category = subCategory.getCategory();
        preparedStatement.setInt(2, category.getId());
    }

    @Override
    public SubCategory mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("subcategory_id");
        String name = resultSet.getString("subcategory_name");
        int fk = resultSet.getInt("category_id_fk");
        Category category = categoryService.findById(fk);
        return new SubCategory(id, name, category);
    }

    @Override
    public String getUpdateQueryParams() {
        return "subcategory_name";
    }

    @Override
    public SubCategory[] showAllSubCategories() throws SQLException {
        SubCategory[] subCategories = new SubCategory[numOfArray()];
        int i = 0;
        String sql = "SELECT * FROM " + getTableName();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SubCategory subCategory = mapResultSetToEntity(resultSet);
                subCategories[i] = subCategory;
                i++;
            }
        }
        return subCategories;
    }

    @Override
    public int editCategoryFk(int id, String name) throws SQLException {
        String sql = " UPDATE " + getTableName() + " SET category_id = ? WHERE subcategory_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int deleteFromInnerTable(int id) throws SQLException {
        String sql = " DELETE FROM " + getTableName() + " WHERE " + getColumnsName() + " = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public SubCategory[] showOneCategoryOfSubCategory(int id) throws SQLException {
        SubCategory[] subCategories = new SubCategory[numOfOneArray(id)];

        int i = 0;

        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdFkColumnName() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SubCategory subCategory = mapResultSetToEntity(resultSet);
                subCategories[i] = subCategory;
                i++;
            }
        }
        return subCategories;
    }
}
