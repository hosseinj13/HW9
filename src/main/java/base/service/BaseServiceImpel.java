package base.service;

import base.model.BaseEntity;
import base.repository.BaseRepository;

import java.io.Serializable;
import java.sql.SQLException;

public class BaseServiceImpel<ID extends Serializable, TYPE extends BaseEntity<ID>, R extends BaseRepository<ID, TYPE>>
        implements BaseService<ID, TYPE>{

    protected final R repository;

    public BaseServiceImpel(R repository) {
        this.repository = repository;
    }

    @Override
    public int save(TYPE entity) throws SQLException {
       return repository.save(entity);
    }

    @Override
    public TYPE findById(ID id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public TYPE findByName(String name) throws SQLException {
        return repository.findByName(name);
    }

    @Override
    public int editName(String oldName, String newName) throws SQLException {
        return 0;
    }

    @Override
    public void update(TYPE entity) throws SQLException {
        repository.update(entity);
    }

    @Override
    public int delete(String name) throws SQLException {
       return repository.delete(name);
    }
}
