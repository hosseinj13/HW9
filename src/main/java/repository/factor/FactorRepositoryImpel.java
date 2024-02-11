package repository.factor;

import base.repository.BaseRepositoryImpel;
import model.Factor;
import model.SubCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactorRepositoryImpel extends BaseRepositoryImpel<Integer, Factor> implements FactorRepository {

    public FactorRepositoryImpel(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return " maktab.hw9.factor_product";
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
        return "factor_id";
    }

    @Override
    public String getCountQuestionMarkParams() {
        return null;
    }

    @Override
    public void fillParamForStatement(PreparedStatement preparedStatement, Factor entity, boolean isCountOnly) throws SQLException {

    }


    @Override
    public Factor mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Factor factor = new Factor();
        factor.setId(resultSet.getInt("factor_id"));


        return factor;
    }

    @Override
    public String getUpdateQueryParams() {
        return "user_id = ?, item_id = ?";
    }


    @Override
    public int[] numOfFactor(int id) throws SQLException {
        int[] numOfFactor = new int[99999];
        int i = 0;

        String sql = "SELECT * FROM maktab.hw9.factor WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int num = resultSet.getInt("factor_id");
                numOfFactor[i] = num;
                i++;
            }
        }
        return numOfFactor;
    }

    @Override
    public int saveFactorInnerTable(int factorId, int productId) throws SQLException {
        String sql = "INSERT INTO maktab.hw9.factor_product(factor_id, product_id) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, factorId);
            preparedStatement.setInt(2, factorId);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int[] productsOfOneFactor(int id) throws SQLException {
        int [] productsId = new int[numOfOneArray(id)];
        int  i = 0;

        String sql = "SELECT * FROM maktab.hw9.factor_product WHERE factor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int temp = resultSet.getInt("product_id");
                productsId[i] = temp;
                i++;
            }
        }
        return productsId;
    }
}
