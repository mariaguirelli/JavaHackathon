package com.unialfa.dao;

import com.unialfa.model.Vacina;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
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



    public void inserir (Vacina vacinas) throws Exception{
        String sql = "INSERT INTO vacinas (nome, dataInicioCampanha) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, vacinas.getNome());
        ps.setDate(2, Date.valueOf(vacinas.getData()));

        ps.execute();
    }

    public void atualizar(Vacina vacinas) throws SQLException {
        String sql = "UPDATE vacinas SET nome = ?, dataInicioCampanha = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, vacinas.getNome());
        ps.setDate(2, Date.valueOf(vacinas.getData()));
        ps.setLong(3, vacinas.getId());
        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM vacinas WHERE vacinas.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public List<Vacina> listarTodos() throws SQLException {
        List<Vacina> vacinas = new ArrayList<Vacina>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM vacinas").executeQuery();
        while (rs.next()) {
            vacinas.add(new Vacina(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getDate("dataInicioCampanha").toLocalDate()
            ));
        }
        rs.close();
        return vacinas;
    }
}
