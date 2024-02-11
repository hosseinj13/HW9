package service.subCategory;

import base.service.BaseService;
import model.Factor;
import model.SubCategory;

import java.sql.SQLException;

public interface SubCategoryService extends BaseService<Integer, SubCategory> {
    SubCategory [] showAllSubCategory() throws SQLException;
    int editCategoryFk(int id,String name) throws SQLException;
    int deleteFromInnerTable(int id) throws SQLException;
    SubCategory [] showOneCategorySubCategories(int id) throws SQLException;

}
