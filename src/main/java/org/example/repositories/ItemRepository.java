package org.example.repositories;

import org.example.connection.DatabaseConnection;
import org.example.entities.Usuario;
import org.example.entities.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemRepository extends _BaseRepositoryImpl<Item>{
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    DatabaseConnection connection = new DatabaseConnection();

    public static final String TB_NAME = "CH_PRODUTO";

    public static final Map<String, String> TB_COLUMNS = Map.of(
            "COD_PRODUTO", "COD_PRODUTO",
            "NOME", "NOME",
            "DESCRICAO", "DESCRICAO",
            "PRECO", "PRECO",
            "ESTOQUE", "ESTOQUE",
            "CH_CLIENTE_COD_CLIENTE", "CH_CLIENTE_COD_CLIENTE"

    );


    public ItemRepository() {
        Initialize();
    }

    private void Initialize() {
        String sql = "CREATE TABLE %s (" +
                "%s NUMBER generated as identity constraint %s_PK PRIMARY KEY, " +
                        "%s VARCHAR2(100) NOT NULL, " +
                        "%s VARCHAR2(250) NOT NULL, " +
                        "%s NUMBER NOT NULL, " +
                        "%s NUMBER NOT NULL, ".formatted(TB_NAME,
                                TB_COLUMNS.get("COD_PRODUTO"),
                                TB_NAME,
                                TB_COLUMNS.get("NOME"),
                                TB_COLUMNS.get("DESCRICAO"),
                                TB_COLUMNS.get("PRECO"),
                                TB_COLUMNS.get("ESTOQUE")
                        );

        try (var conn = connection.getConnection()) {
            var metaData = conn.getMetaData();
            var rs = metaData.getTables(null, null, TB_NAME.toUpperCase(), new String[]{"TABLE"});
            if (!rs.next()) {
                try (var stmt = conn.prepareStatement(sql)) {
                    stmt.execute();
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Create(Item item, int id) {
        Usuario usuario = usuarioRepository.ReadById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Cliente com ID " + id + " não existe.");
        }

        String sql = "INSERT INTO " + TB_NAME + " (" +
                TB_COLUMNS.get("NOME") + ", " +
                TB_COLUMNS.get("DESCRICAO") + ", " +
                TB_COLUMNS.get("PRECO") + ", " +
                TB_COLUMNS.get("ESTOQUE") + ", " +
                TB_COLUMNS.get("CH_CLIENTE_COD_CLIENTE") + ") " +
                "VALUES (?, ?, ?, ?, ?)";

        try (var conn = connection.getConnection()) {
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, item.getNome());
                stmt.setString(2, item.getDescricao());
                stmt.setDouble(3, item.getPreco());
                stmt.setInt(4, item.getEstoque());
                stmt.setInt(5, id);
                var result = stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setId(ReadAll().getLast().getId());
    }

    @Override
    public List<Item> ReadAll() {
        List<Item> items = new ArrayList<>();
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY "+ TB_COLUMNS.get("COD_PRODUTO")+" ASC");
            var rs = stmt.executeQuery();
            while (rs.next()){
                Item item = new Item(
                        rs.getInt(TB_COLUMNS.get("COD_PRODUTO")),
                        usuarioRepository.ReadById(rs.getInt(TB_COLUMNS.get("CH_CLIENTE_COD_CLIENTE"))),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getString(TB_COLUMNS.get("DESCRICAO")),
                        rs.getDouble(TB_COLUMNS.get("PRECO")),
                        rs.getInt(TB_COLUMNS.get("ESTOQUE"))
                );
                items.add(item);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public boolean UpdateById(Item item, int id) {
        String sql = "UPDATE " + TB_NAME + " SET " +
                TB_COLUMNS.get("NOME") + " = ?, " +
                TB_COLUMNS.get("DESCRICAO") + " = ?, " +
                TB_COLUMNS.get("PRECO") + " = ?, " +
                TB_COLUMNS.get("ESTOQUE") + " = ? " +
                "WHERE " + TB_COLUMNS.get("COD_PRODUTO") + " = ?";

        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescricao());
            stmt.setDouble(3, item.getPreco());
            stmt.setInt(4, item.getEstoque());
            stmt.setInt(5, id);
            var result = stmt.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
        return false;
        }
    }


    @Override
    public Item ReadById(int id) {
        Item item = null;
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE " + TB_COLUMNS.get("COD_PRODUTO") + " = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()){
                item = new Item(
                        rs.getInt(TB_COLUMNS.get("COD_PRODUTO")),
                        usuarioRepository.ReadById(rs.getInt(TB_COLUMNS.get("CH_CLIENTE_COD_CLIENTE"))),
                        rs.getString(TB_COLUMNS.get("NOME")),
                        rs.getString(TB_COLUMNS.get("DESCRICAO")),
                        rs.getDouble(TB_COLUMNS.get("PRECO")),
                        rs.getInt(TB_COLUMNS.get("ESTOQUE"))
                );
            }
        } catch (SQLException e){
                e.printStackTrace();
        }

        return item;
    }
    @Override
    public boolean DeleteById(int id) {
        try(var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE " + TB_COLUMNS.get("COD_PRODUTO") + " = ?");
            stmt.setInt(1, id);
            var result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }




}
