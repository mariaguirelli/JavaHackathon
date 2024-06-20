package com.unialfa.view;

import com.unialfa.model.Agente;
import com.unialfa.service.AgenteService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AgenteForm extends JFrame {
    private AgenteService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeAgente;
    private JTextField campoNomeAgente;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JTable tabelaAgente;

    public AgenteForm() {
        service = new AgenteService();

        setTitle("Agente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelId = new JLabel("ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(15);
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        labelNomeAgente = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeAgente, constraints);

        campoNomeAgente = new JTextField(15);
        campoNomeAgente.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeAgente, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 1;
        constraints.gridy = 6;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 0;
        constraints.gridy = 6;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarDiretor());
        constraints.gridx = 2;
        constraints.gridy = 6;
        painelEntrada.add(botaoDeletar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        tabelaAgente = new JTable();
        tabelaAgente.setModel(carregarDadosAgentes());
        tabelaAgente.getSelectionModel().addListSelectionListener(e -> selecionarAgente(e));
        tabelaAgente.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabelaAgente);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        setLocationRelativeTo(null);


    }

    private void selecionarAgente(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaAgente.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Long) tabelaAgente.getValueAt(selectedRow, 0);
                campoId.setText(id.toString());
                var nome = (String) tabelaAgente.getValueAt(selectedRow, 1);
                campoNomeAgente.setText(nome);
            }

        }
    }

    private void limparCampos() {
        campoNomeAgente.setText("");
        campoId.setText("");
    }

    private Agente construirAgente() throws Exception {
        return campoId.getText().isEmpty()
                ? new Agente(campoNomeAgente.getText())
                : new Agente(Long.parseLong(campoId.getText()), campoNomeAgente.getText());
    }

    private DefaultTableModel carregarDadosAgentes() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nome");

        service.listarAgentes().forEach(agente ->
                model.addRow(new Object[]{
                        agente.getId(), agente.getNome()
                })
        );
        return model;
    }

    private void executarAcaoDoBotao() {
        try {
            service.salvar(construirAgente());
            limparCampos();
            tabelaAgente.setModel(carregarDadosAgentes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletarDiretor() {
        service.deletar((int) Long.parseLong(campoId.getText()));
        limparCampos();
        tabelaAgente.setModel(carregarDadosAgentes());
    }
}
