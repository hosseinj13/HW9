package base.service;

import java.sql.SQLException;

public interface BaseService<ID, TYPE> {
    int save(TYPE entity) throws SQLException;
    TYPE findById(ID id) throws SQLException;
    TYPE findByName(String name) throws SQLException;
    int editName(String oldName, String newName) throws SQLException;
    void update(TYPE entity) throws SQLException;
    int delete(String name) throws SQLException;

}
