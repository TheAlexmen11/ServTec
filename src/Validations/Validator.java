package Validations;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Validator {

    public static boolean validarCampos(LinkedList<JTextField> campos, LinkedList<JLabel> etiquetas) {
        String[] mensajes = {
            "Nombre:", "Dni:", "Teléfono:", "Correo:",
            "Dirección:"
        };

        boolean hayError = false;

        for (int i = 0; i < campos.size(); i++) {
            if (campos.get(i).getText().trim().isEmpty()) {
                etiquetas.get(i).setText(mensajes[i]);
                etiquetas.get(i).setForeground(Color.RED);
                campos.get(i).requestFocus();
                hayError = true;
                break;
            } else {
                etiquetas.get(i).setForeground(Color.BLACK);
            }
        }

        // Validar email
        int correoIndex = 3;
        if (!hayError && !esEmailValido(campos.get(correoIndex).getText())) {
            etiquetas.get(correoIndex).setText("Correo inválido:");
            etiquetas.get(correoIndex).setForeground(Color.RED);
            campos.get(correoIndex).requestFocus();
            hayError = true;
        }

        return !hayError;
    }

    public static boolean esEmailValido(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    }

    public static boolean validarCamposUsuario(HashMap<JLabel, JTextField> campos) {

        //boolean hayError = false;
        boolean[] hayError = {false};

        // Validar que los campos no estén vacíos
        for (JLabel label : campos.keySet()) {
            if (campos.get(label).getText().trim().isEmpty()) {
                label.setForeground(Color.RED);
                campos.get(label).requestFocus();
                hayError[0] = true;
                break;
            } else {
                label.setForeground(Color.BLACK);
            }
        }

        // Validar formato de correo si el campo existe
        for (JLabel label : campos.keySet()) {
            if (label.getText().toLowerCase().contains("correo")) {
                JTextField field = campos.get(label);
                if (!esEmailValido(field.getText())) {
                    label.setForeground(Color.RED);
                    field.requestFocus();
                    hayError[0] = true;
                    break;
                }
            }
        }

        // Validar que DNI sea numérico si el campo existe
        campos.entrySet().stream()
                .filter(entry -> entry.getKey().getText().toLowerCase().contains("dni"))
                .forEach(entry -> {
                    try {
                        Integer.parseInt(entry.getValue().getText());
                    } catch (NumberFormatException e) {
                        entry.getKey().setForeground(Color.RED);
                        entry.getValue().requestFocus();
                        hayError[0] = true;
                    }
                });

        return !hayError[0];

    }
}
