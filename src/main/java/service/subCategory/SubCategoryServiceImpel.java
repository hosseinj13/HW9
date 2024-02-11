package service.subCategory;

import base.service.BaseServiceImpel;
import model.Factor;
import model.SubCategory;
import repository.subCategory.SubCategoryRepository;

import java.sql.SQLException;


public class SubCategoryServiceImpel extends BaseServiceImpel<Integer, SubCategory, SubCategoryRepository> implements SubCategoryService {

    public SubCategoryServiceImpel(SubCategoryRepository repository) {
        super(repository);
    }


    @Override
    public SubCategory[] showAllSubCategory() throws SQLException {
        return repository.showAllSubCategories();
    }

    @Override
    public int editCategoryFk(int id, String name) throws SQLException {
        return repository.editCategoryFk(id, name);
    }

    @Override
    public int deleteFromInnerTable(int id) throws SQLException {
        return repository.deleteFromInnerTable(id);
    }

    @Override
    public SubCategory[] showOneCategorySubCategories(int id) throws SQLException {
        return repository.showOneCategoryOfSubCategory(id);
    }
}