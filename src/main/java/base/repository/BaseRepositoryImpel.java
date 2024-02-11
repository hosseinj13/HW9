package base.repository;

import base.model.BaseEntity;
import model.SubCategory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseRepositoryImpel<ID extends Serializable, TYPE extends BaseEntity<ID>>
        implements BaseRepository<ID, TYPE> {
    protected final Connection connection;

    public BaseRepositoryImpel(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(TYPE entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " " + getColumnsName() + " VALUES " + getCountQuestionMarkParams();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillParamForStatement(preparedStatement, entity, false);
            return preparedStatement.executeUpdate();
        }
    }


    @Override
    public TYPE findById(ID id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, (Integer) id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
            return null;
        }
    }

    @Override
    public TYPE findByName(String name) throws SQLException {
        String sql = "select * from " + getTableName() + " where " + getColumnModelName() + " =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return mapResultSetToEntity(resultSet);
        }
        return null;
    }

    @Override
    public int editName(String oldName, String newName) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET " + getUpdateQueryParams() + "= ?" + " WHERE " + getEditNameColumn() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, oldName);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(TYPE entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET " + getUpdateQueryParams() + " WHERE id = " + entity.getId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillParamForStatement(preparedStatement, entity, true);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public int delete(String name) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getEditNameColumn() + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            return  preparedStatement.executeUpdate();
        }
    }

    @Override
    public int numOfArray() throws SQLException {
        int numOfArray = 0;
        String sql = "SELECT * FROM " + getTableName();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numOfArray++;
            }
        }
        return numOfArray;
    }

    @Override
    public int numOfOneArray(int id) throws SQLException {
        int numOfArrayOne = 0;

        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdFkColumnName() + "= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numOfArrayOne++;
            }
        }
        return numOfArrayOne;
    }

    public abstract String getTableName();

    public abstract String getColumnsName();
    public abstract String getEditNameColumn();

    public abstract String getIdColumnName();

    public abstract String getColumnModelName();

    public abstract String getIdFkColumnName();

    public abstract String getCountQuestionMarkParams();

    public abstract void fillParamForStatement(PreparedStatement preparedStatement, TYPE entity, boolean isCountOnly) throws SQLException;

    public abstract TYPE mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    public abstract String getUpdateQueryParams();

}
