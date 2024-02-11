package repository.subCategory;

import base.repository.BaseRepository;
import model.SubCategory;
import java.sql.SQLException;

public interface SubCategoryRepository extends BaseRepository<Integer, SubCategory> {
    SubCategory [] showAllSubCategories() throws SQLException;
    int editCategoryFk(int id,String name) throws SQLException;
    int deleteFromInnerTable(int id) throws SQLException;
    SubCategory [] showOneCategoryOfSubCategory(int id) throws SQLException;
}
