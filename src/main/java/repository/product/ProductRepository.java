package repository.product;

import base.repository.BaseRepository;
import model.Product;

import java.sql.SQLException;

public interface ProductRepository extends BaseRepository<Integer, Product> {

    Product [] showAllProducts() throws SQLException;
    int editProductPrice(String name, float newPrice) throws SQLException;
    int editProductNumber(String name, int newNumber) throws SQLException;
    int editProductSubCategory(int id, String name) throws SQLException;
    Product[] showOneSubCategoryProducts(int id) throws SQLException;
    int deleteFromInnerTable(int id) throws SQLException;
}
