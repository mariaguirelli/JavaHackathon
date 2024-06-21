package com.unialfa.view;

import com.unialfa.model.Vacina;
import com.unialfa.service.VacinaService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class VacinaForm {
    private VacinaService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeVacina;
    private JTextField campoNomeVacina;
    private JLabel labelDataInicioCampanha;
    private JTextField campoDataInicioCampanha;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JTable tabelaVacina;

    public VacinaForm() {
        service = new VacinaService();

        setTitle("Vacina");
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
        campoDataInicioCampanha
        campoId.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        labelNomeVacina = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeVacina, constraints);

        campoNomeVacina = new JTextField(15);
        campoNomeVacina.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeVacina, constraints);

        labelDataInicioCampanha = new JLabel("Data Início da Campanha:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDataInicioCampanha, constraints);

        campoDataInicioCampanha = new JTextField(15);
        campoDataInicioCampanha.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoDataInicioCampanha, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarVacina());
        constraints.gridx = 2;
        constraints.gridy = 3;
        painelEntrada.add(botaoDeletar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        tabelaVacina = new JTable();
        tabelaVacina.setModel(carregarDadosVacinas());
        tabelaVacina.getSelectionModel().addListSelectionListener(e -> selecionarVacina(e));
        tabelaVacina.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabelaVacina);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void selecionarVacina(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaVacina.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Long) tabelaVacina.getValueAt(selectedRow, 0);
                campoId.setText(id.toString());
                var nome = (String) tabelaVacina.getValueAt(selectedRow, 1);
                campoNomeVacina.setText(nome);
                var dataInicioCampanha = (LocalDate) tabelaVacina.getValueAt(selectedRow, 3);
                campoNomeVacina.setText(nome);
            }
        }
    }

    private void limparCampos() {
        campoNomeVacina.setText("");
        campoDataInicioCampanha.setText("");
        campoId.setText("");
    }

    private Vacina construirVacina() throws Exception {
        return campoId.getText().isEmpty()
                ? new Vacina(campoNomeVacina.getText(), campoDataInicioCampanha.getText())
                : new Vacina(Long.parseLong(campoId.getText()), campoNomeVacina.getText(), campoDataInicioCampanha.getText());
    }

    private DefaultTableModel carregarDadosVacinas() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Data Campanha");

        service.listarVacinas().forEach(vacina ->
                model.addRow(new Object[]{
                        vacina.getId(), vacina.getNome(), vacina.getData()
                })
        );
        return model;
    }

    private void executarAcaoDoBotao() {
        try {
            service.salvar(construirVacina());
            limparCampos();
            tabelaVacina.setModel(carregarDadosVacinas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletarVacina() {
        service.deletar((int) Long.parseLong(campoId.getText()));
        limparCampos();
        tabelaVacina.setModel(carregarDadosVacinas());
    }

    private Date converterData (String data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(data);
    }
}
