package com.unialfa.dao;

import com.unialfa.model.Agente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDao {
    private Connection connection;

    public AgenteDao() throws SQLException {
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

    public void inserir(Agente medico) throws SQLException {
        String sql = "INSERT INTO medico (nome) VALUES (?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, medico.getNome());

        ps.execute();
    }

    public void atualizar(Agente medico) throws SQLException {
        String sql = "UPDATE medico SET nome = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, medico.getNome());
        ps.setLong(2, medico.getId());
        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM medico WHERE medico.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public List<Agente> listarTodos() throws SQLException {
        List<Agente> agentes = new ArrayList<Agente>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM medico").executeQuery();
        while (rs.next()) {
            agentes.add(new Agente(
                    rs.getLong("id"),
                    rs.getString("nome")
            ));
        }
        rs.close();
        return agentes;
    }
}

