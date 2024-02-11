package repository.user;

import base.repository.BaseRepository;
import model.User;
import java.sql.SQLException;

public interface UserRepository extends BaseRepository<Integer, User> {

    User logIn(String username, String password) throws SQLException;
    int saveUserInnerTable(int id) throws SQLException;

}
