package repository.admin;

import base.repository.BaseRepositoryImpel;
import model.Admin;
import model.SubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepositoryImpel extends BaseRepositoryImpel<Integer, Admin> implements AdminRepository {
    public AdminRepositoryImpel(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return " maktab.hw9.admins ";
    }

    @Override
    public String getColumnsName() {
        return null;
    }

    @Override
    public String getEditNameColumn() {
        return null;
    }

    @Override
    public String getIdColumnName() {
        return null;
    }

    @Override
    public String getColumnModelName() {
        return null;
    }

    @Override
    public String getIdFkColumnName() {
        return null;
    }

    @Override
    public String getCountQuestionMarkParams() {
        return null;
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, Admin admin, boolean isCountOnly) throws SQLException {

    }

    @Override
    public Admin mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getInt("admin_id"));
        admin.setName(resultSet.getString("admin_name"));
        admin.setUsername(resultSet.getString("admin_username"));
        admin.setEmail(resultSet.getString("admin_email"));
        admin.setPassword(resultSet.getString("admin_password"));
        return admin;
    }

    @Override
    public String getUpdateQueryParams() {
        return null;
    }

    @Override
    public Admin login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE admin_username = ? AND admin_password = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return mapResultSetToEntity(resultSet);
        }
        return null;
    }
}
