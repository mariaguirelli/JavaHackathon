package com.unialfa.view;

import com.unialfa.model.Vacina;
import com.unialfa.service.VacinaService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VacinaForm extends JFrame{
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
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public VacinaForm() {
        service = new VacinaService();

        setTitle("Vacina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

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

        labelNomeVacina = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeVacina, constraints);

        campoNomeVacina = new JTextField(15);
        campoNomeVacina.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeVacina, constraints);

        labelDataInicioCampanha = new JLabel("Data Início da Campanha:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDataInicioCampanha, constraints);

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataInicioCampanha = new JFormattedTextField(dateMask);
            campoDataInicioCampanha.setColumns(15);
            constraints.gridx = 1;
            constraints.gridy = 2;
            painelEntrada.add(campoDataInicioCampanha, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
                String dataInicioCampanhaStr = (String) tabelaVacina.getValueAt(selectedRow, 2);
                LocalDate dataInicioCampanha = LocalDate.parse(dataInicioCampanhaStr, dateFormatter);
                campoDataInicioCampanha.setText(dataInicioCampanhaStr);
            }
        }
    }

    private void limparCampos() {
        campoNomeVacina.setText("");
        campoDataInicioCampanha.setText("");
        campoId.setText("");
    }

    private Vacina construirVacina() throws Exception {
        LocalDate dataInicioCampanha = converterData(campoDataInicioCampanha.getText());
        return campoId.getText().isEmpty()
                ? new Vacina(dataInicioCampanha, campoNomeVacina.getText())
                : new Vacina(Long.parseLong(campoId.getText()), campoNomeVacina.getText(), dataInicioCampanha);
    }

    private DefaultTableModel carregarDadosVacinas() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Data Campanha");

        service.listarVacinas().forEach(vacina -> {
            String dataFormatada = vacina.getData().format(dateFormatter);
            model.addRow(new Object[]{
                    vacina.getId(), vacina.getNome(), dataFormatada
            });
        });
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

    private LocalDate converterData(String data) throws DateTimeParseException {
        return LocalDate.parse(data, dateFormatter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VacinaForm form = new VacinaForm();
            form.setVisible(true);
        });
    }
}
