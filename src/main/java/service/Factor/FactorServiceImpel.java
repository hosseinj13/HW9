package service.Factor;

import base.service.BaseServiceImpel;
import model.Factor;
import repository.factor.FactorRepository;
import java.sql.SQLException;

public class FactorServiceImpel extends BaseServiceImpel<Integer, Factor, FactorRepository> implements FactorService {
    public FactorServiceImpel(FactorRepository repository) {
        super(repository);
    }

    @Override
    public int save(Factor entity) throws SQLException {
      return  super.save(entity);
    }

    @Override
    public int delete(String name) throws SQLException {
        return super.delete(name);
    }

    @Override
    public int[] numOfFactor(int id) throws SQLException {
        return repository.numOfFactor(id);
    }

    @Override
    public int saveFactorInnerTable(int idF, int idP) throws SQLException {
        return repository.saveFactorInnerTable(idF, idP);
    }

    @Override
    public int[] productsOfOneFactor(int id) throws SQLException {
        return repository.productsOfOneFactor(id);
    }
}
