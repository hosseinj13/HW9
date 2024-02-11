package repository.user;

import base.repository.BaseRepositoryImpel;
import model.SubCategory;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpel extends BaseRepositoryImpel<Integer, User>
        implements UserRepository {

    public UserRepositoryImpel(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return " maktab.hw9.users ";
    }

    @Override
    public String getColumnsName() {
        return "(name, username, user_email, user_password)";
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
        return "username";
    }

    @Override
    public String getIdFkColumnName() {
        return null;
    }

    @Override
    public String getCountQuestionMarkParams() {
        return "(?, ?, ?, ?)";
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, User user, boolean isCountOnly) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
    }

    @Override
    public User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("user_email"));
        user.setPassword(resultSet.getString("user_password"));
        return user;
    }

    @Override
    public String getUpdateQueryParams() {
        return "name = ?, username = ?, user_email = ?, user_password = ?";
    }


    @Override
    public User logIn(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE username = ? AND user_password = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return mapResultSetToEntity(resultSet);
        }
        return null;
    }

    @Override
    public int saveUserInnerTable(int id) throws SQLException {
        String sql = "INSERT INTO maktab.hw9.factor(user_id) VALUES (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate();
        }
    }
}






