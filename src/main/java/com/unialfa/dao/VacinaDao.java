package com.unialfa.dao;

import com.unialfa.model.Vacina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacinaDao {
    private Connection connection;

    public VacinaDao() throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackathon?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }



    public void inserir (Vacina vacina) throws Exception{
        String sql = "INSERT INTO vacina (nome, dataInicioCampanha) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, vacina.getNome());
        ps.setDate(2, Date.valueOf(vacina.getData()));

        ps.execute();
    }

    public void atualizar(Vacina vacina) throws SQLException {
        String sql = "UPDATE vacina SET nome = ?, dataInicioCampanha = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, vacina.getNome());
        ps.setDate(2, Date.valueOf(vacina.getData()));
        ps.setLong(3, vacina.getId());
        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM vacina WHERE vacina.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public List<Vacina> listarTodos() throws SQLException {
        List<Vacina> vacina = new ArrayList<Vacina>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM vacina").executeQuery();
        while (rs.next()) {
            vacina.add(new Vacina(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getDate("dataInicioCampanha").toLocalDate()
            ));
        }
        rs.close();
        return vacina;
    }
}
