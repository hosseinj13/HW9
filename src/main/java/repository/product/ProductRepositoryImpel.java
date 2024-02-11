package repository.product;

import base.repository.BaseRepositoryImpel;
import model.Product;
import model.SubCategory;
import service.subCategory.SubCategoryService;
import utility.ApplicationContext;

import java.sql.*;

public class ProductRepositoryImpel extends BaseRepositoryImpel<Integer, Product> implements ProductRepository {
    public ProductRepositoryImpel(Connection connection) {
        super(connection);
    }

    SubCategoryService subCategoryService = ApplicationContext.getSubCategoryServiceImpel();

    @Override
    public String getTableName() {
        return " maktab.hw9.product ";
    }

    @Override
    public String getColumnsName() {
        return "(product_name, product_price, product_number, subCategory_id)";
    }

    @Override
    public String getEditNameColumn() {
        return null;
    }

    @Override
    public String getIdColumnName() {
        return "product_id";
    }

    @Override
    public String getColumnModelName() {
        return "";
    }

    @Override
    public String getIdFkColumnName() {
        return "subCategory_id";
    }

    @Override
    public String getCountQuestionMarkParams() {
        return "(?, ?, ?, ?)";
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, Product product, boolean isCountOnly) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setDouble(2, product.getProductPrice());
        preparedStatement.setInt(3, product.getProductNumber());
        SubCategory subCategory = product.getSubCategory();
        preparedStatement.setInt(4, subCategory.getId());
    }

    @Override
    public Product mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setProductPrice(resultSet.getDouble("product_price"));
        product.setProductNumber(resultSet.getInt("product_number"));
        product.setSubCategory(subCategoryService.findById(resultSet.getInt("subcategory_id")));
        return product;
    }

    @Override
    public String getUpdateQueryParams() {
        return "product_name";
    }

    @Override
    public Product[] showAllProducts() throws SQLException {
        Product[] products = new Product[numOfArray()];
        int i = 0;

        String sql = "SELECT * FROM " + getTableName();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = mapResultSetToEntity(resultSet);
                products[i] = product;
                i++;
            }
        }
        return products;
    }

    @Override
    public int editProductPrice(String name, float newPrice) throws SQLException {
        String sql = "UPDATE maktab.hw9.product SET product_price = ? WHERE product_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setString(2, name);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int editProductNumber(String name, int newNumber) throws SQLException {
        String sql = "UPDATE maktab.hw9.product SET product_number = ? WHERE product_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setString(2, name);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int editProductSubCategory(int id, String name) throws SQLException {
        String sql = "UPDATE maktab.hw9.product SET subcategory_id = ? WHERE product_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            return preparedStatement.executeUpdate();
        }
    }


    @Override
    public Product[] showOneSubCategoryProducts(int id) throws SQLException {
        Product [] products = new Product[numOfOneArray(id)];
        int i = 0;
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdFkColumnName() + " = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product  = mapResultSetToEntity(resultSet);
                products[i] = product;
                i ++ ;
            }
        }
        return products;
    }

    @Override
    public int deleteFromInnerTable(int id) throws SQLException {
        String sql = "DELETE FROM maktab.hw9.factor_product WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }
}
