package org.example.repositories;

import org.example.connection.DatabaseConnection;
import org.example.entities.Endereco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnderecoRepository extends _BaseRepositoryImpl<Endereco>{

    DatabaseConnection connection = new DatabaseConnection();
    public static final String TB_NAME = "CH_ENDERECO";

    public static final Map<String, String> TB_COLUMNS_ENDERECO = Map.of(
            "COD_ENDERECO", "COD_ENDERECO",
            "NUMERO", "NUMERO",
            "COMPLEMENTO", "COMPLEMENTO",
            "CEP", "CEP",
            "CH_CLIENTE_COD_CLIENTE", "CH_CLIENTE_COD_CLIENTE",
            "CH_BAIRRO_COD_BAIRRO", "CH_BAIRRO_COD_BAIRRO"
    );

    public EnderecoRepository() {
        Initialize();
    }

    private void Initialize() {
        String sql = "CREATE TABLE %s (" +
                "%s NUMBER generated as idendereco constraint %s_PK PRIMARY KEY, " +
                "%s VARCHAR2(50) NOT NULL, " +
                "%s VARCHAR2(10) NOT NULL, " +
                "%s VARCHAR2(50) NOT NULL, " +
                "%s VARCHAR2(8) NOT NULL, " +
                "%s NUMBER NOT NULL, " +
                "%s NUMBER NOT NULL)"
                        .formatted(TB_NAME,
                                TB_COLUMNS_ENDERECO.get("COD_ENDERECO"),
                                TB_NAME,
                                TB_COLUMNS_ENDERECO.get("NUMERO"),
                                TB_COLUMNS_ENDERECO.get("COMPLEMENTO"),
                                TB_COLUMNS_ENDERECO.get("CEP"),
                                TB_COLUMNS_ENDERECO.get("CH_CLIENTE_COD_CLIENTE"),
                                TB_COLUMNS_ENDERECO.get("CH_CIDADE_COD_BAIRRO")
                        );
        try (var conn = connection.getConnection()) {
            var metaData = conn.getMetaData();
            var rs = metaData.getTables(null, null, TB_NAME.toUpperCase(), new String[]{"TABLE"});
            if (!rs.next()) {
                try (var stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Endereco Create(Endereco endereco) {
        String sql = "INSERT INTO "+ TB_NAME + " (" +
                TB_COLUMNS_ENDERECO.get("NUMERO") + ", " +
                TB_COLUMNS_ENDERECO.get("COMPLEMENTO") + ", " +
                TB_COLUMNS_ENDERECO.get("CEP") + ", " +
                TB_COLUMNS_ENDERECO.get("CH_CLIENTE_COD_CLIENTE") + ", " +
                TB_COLUMNS_ENDERECO.get("CH_BAIRRO_COD_BAIRRO") + ") " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.valueOf(endereco.getNumero()));
                stmt.setString(2, endereco.getComplemento());
                stmt.setInt(3, Integer.valueOf(endereco.getCep()));
                stmt.setInt(4, endereco.getCliente().getId());
                var result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return endereco;
    }

    @Override
    public List<Endereco> ReadAll() {
        List<Endereco> enderecos = new ArrayList<>();
        var sql = "SELECT e.COD_ENDERECO, e.NUMERO, e.COMPLEMENTO, e.CEP, " +
                "p.nome AS pais " +
                "FROM ch_endereco e " +
                "JOIN ch_pais p ON est.ch_pais_cod_pais = p.cod_pais";
        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)) {
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    var endereco = new Endereco();
                    endereco.setId(rs.getInt("COD_ENDERECO"));
                    endereco.setNumero(rs.getString("NUMERO"));
                    endereco.setComplemento(rs.getString("COMPLEMENTO"));
                    endereco.setCep(rs.getString("CEP"));
                    endereco.setPais(rs.getString("pais"));

                    enderecos.add(endereco);
            }
            return enderecos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean UpdateById(Endereco endereco, int id) {
        String sql = "UPDATE " + TB_NAME + " SET " +
                TB_COLUMNS_ENDERECO.get("NUMERO") + " = ?, " +
                TB_COLUMNS_ENDERECO.get("COMPLEMENTO") + " = ?, " +
                TB_COLUMNS_ENDERECO.get("CEP") + " = ?, " +
                TB_COLUMNS_ENDERECO.get("CH_CLIENTE_COD_CLIENTE") + " = ?, " +
                TB_COLUMNS_ENDERECO.get("CH_BAIRRO_COD_BAIRRO") + " = ? " +
                "WHERE " + TB_COLUMNS_ENDERECO.get("COD_ENDERECO") + " = ?";
        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,  Integer.valueOf(endereco.getNumero()));
            stmt.setString(2, endereco.getComplemento());
            stmt.setInt(3, Integer.valueOf(endereco.getCep()));
            stmt.setInt(4, endereco.getCliente().getId());
            stmt.setInt(5, id);
            var result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean DeleteById(int id) {
        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE " + TB_COLUMNS_ENDERECO.get("COD_ENDERECO") + " = ?")) {
            stmt.setInt(1, id);
            var result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
}


    @Override
    public Endereco ReadById(int id) {
        Endereco endereco = null;
        var sql = "SELECT e.*, p.nome AS pais " +
                "FROM ch_endereco e " +
                "JOIN ch_pais p ON est.ch_pais_cod_pais = p.cod_pais " +
                "WHERE e.COD_ENDERECO = ?";
        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                endereco = new Endereco();
                endereco.setId(rs.getInt("COD_ENDERECO"));
                endereco.setNumero(rs.getString("NUMERO"));
                endereco.setComplemento(rs.getString("COMPLEMENTO"));
                endereco.setCep(rs.getString("CEP"));
                endereco.setPais(rs.getString("pais"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endereco;
    }


}
