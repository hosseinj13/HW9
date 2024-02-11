package service.admin;

import base.service.BaseServiceImpel;
import model.Admin;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryImpel;

import java.sql.SQLException;

public class AdminServiceImpel extends BaseServiceImpel<Integer, Admin, AdminRepository> implements AdminService {
    public AdminServiceImpel(AdminRepository repository) {
        super(repository);
    }

    @Override
    public Admin login(String username, String password) throws SQLException {
        return repository.login(username, password);
    }
}
