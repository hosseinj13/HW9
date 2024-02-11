package repository.factor;

import base.repository.BaseRepository;
import model.Factor;

import java.sql.SQLException;

public interface FactorRepository extends BaseRepository<Integer, Factor> {
    int [] numOfFactor(int id) throws SQLException, SQLException;
    int saveFactorInnerTable(int idF,int idP) throws SQLException;
    int [] productsOfOneFactor(int id) throws SQLException;

}
