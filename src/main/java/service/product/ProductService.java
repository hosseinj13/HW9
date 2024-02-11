package service.product;

import base.service.BaseService;
import model.Product;

import java.sql.SQLException;

public interface ProductService extends BaseService<Integer, Product> {
    Product[] showAllProducts() throws SQLException;
    int editProductPrice(String name, float newPrice) throws SQLException;
    int editProductNumber(String name, int newNumber) throws SQLException;
    int editProductSubCategory(int id, String name) throws SQLException;
    Product[] showOneSubCategoryProducts(int id) throws SQLException;
    int deleteFromInnerTable(int id) throws SQLException;


}
