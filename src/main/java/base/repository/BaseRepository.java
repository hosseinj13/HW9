package base.repository;

import base.model.BaseEntity;
import java.io.Serializable;
import java.sql.SQLException;

public interface BaseRepository<ID extends Serializable, TYPE extends BaseEntity<ID>> {
    int save(TYPE entity) throws SQLException;
    TYPE findById(ID id) throws SQLException;
    TYPE findByName(String name) throws SQLException;
    int editName(String oldName , String newName) throws SQLException;
    void update(TYPE entity) throws SQLException;
    int delete(String name) throws SQLException;
    int numOfArray() throws SQLException;
    int numOfOneArray(int id) throws SQLException;

}
