package service.product;

import base.service.BaseServiceImpel;
import model.Product;
import repository.product.ProductRepository;
import repository.product.ProductRepositoryImpel;

import java.sql.SQLException;

public class ProductServiceImpel extends BaseServiceImpel<Integer, Product, ProductRepository> implements ProductService {
    public ProductServiceImpel(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Product[] showAllProducts() throws SQLException {
        return repository.showAllProducts();
    }

    @Override
    public int editProductPrice(String name, float newPrice) throws SQLException {
        return repository.editProductPrice(name, newPrice);
    }

    @Override
    public int editProductNumber(String name, int newNumber) throws SQLException {
        return repository.editProductNumber(name, newNumber);
    }

    @Override
    public int editProductSubCategory(int id, String name) throws SQLException {
        return repository.editProductSubCategory(id, name);
    }

    @Override
    public Product[] showOneSubCategoryProducts(int id) throws SQLException {
        return repository.showOneSubCategoryProducts(id);
    }

    @Override
    public int deleteFromInnerTable(int id) throws SQLException {
        return repository.deleteFromInnerTable(id);
    }
}
