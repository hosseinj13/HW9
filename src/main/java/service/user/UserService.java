package service.user;

import base.service.BaseService;
import model.User;

import java.sql.SQLException;

public interface UserService extends BaseService<Integer, User> {
    User logIn(String username,String password) throws SQLException;
    int saveUserInnerTable(int id) throws SQLException;

}
