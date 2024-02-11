package service.category;

import base.service.BaseServiceImpel;
import model.Category;
import repository.category.CategoryRepository;
import repository.category.CategoryRepositoryImpel;

import java.sql.SQLException;

public class CategoryServiceImpel extends BaseServiceImpel<Integer, Category, CategoryRepository> implements CategoryService {

    public CategoryServiceImpel(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Category[] showAllCategories() throws SQLException {
        return repository.showAllCategories();
    }

    @Override
    public int deleteFromInnerTable(int id) throws SQLException {
        return repository.deleteFromInnerTable(id);
    }
}