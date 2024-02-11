package service.user;

import base.service.BaseServiceImpel;
import model.User;
import repository.user.UserRepository;

import java.sql.SQLException;


public class UserServiceImpel extends BaseServiceImpel<Integer, User, UserRepository> implements UserService {
    public UserServiceImpel(UserRepository repository) {
        super(repository);
    }

    @Override
    public User logIn(String username, String password) throws SQLException {
        return repository.logIn(username, password);
    }

    @Override
    public int saveUserInnerTable(int id) throws SQLException {
        return repository.saveUserInnerTable(id);
    }
}


