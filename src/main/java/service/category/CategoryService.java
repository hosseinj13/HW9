package service.category;

import base.service.BaseService;
import model.Category;

import java.sql.SQLException;

public interface CategoryService extends BaseService<Integer, Category> {

    Category [] showAllCategories() throws SQLException;
    int deleteFromInnerTable(int id) throws SQLException;
}
