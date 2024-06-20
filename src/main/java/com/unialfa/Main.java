package com.unialfa;

import com.unialfa.view.AgenteForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var form = new AgenteForm();
            form.setVisible(true);
        });
    }
}