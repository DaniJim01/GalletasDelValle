/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.galletasdelvalle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MainFrame extends JFrame {
    private GestorProduccion gestor = new GestorProduccion();
    private JTextField txtId, txtFecha, txtUnidades, txtHoras, txtCosto, txtRecargo;
    private JComboBox<String> cbTipo;
    private DefaultTableModel modeloTabla;

    public MainFrame() {
        super("Galletas del Valle S.A.");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Registrar Lote"));

        txtId = new JTextField();
        txtFecha = new JTextField("YYYY-MM-DD");
        txtUnidades = new JTextField();
        txtHoras = new JTextField();
        txtCosto = new JTextField();
        txtRecargo = new JTextField();
        cbTipo = new JComboBox<>(new String[]{"Chocolate", "Vainilla"});

        p.add(new JLabel("ID Lote:")); p.add(txtId);
        p.add(new JLabel("Tipo de galleta:")); p.add(cbTipo);
        p.add(new JLabel("Fecha:")); p.add(txtFecha);
        p.add(new JLabel("Unidades:")); p.add(txtUnidades);
        p.add(new JLabel("Horas fabricación:")); p.add(txtHoras);
        p.add(new JLabel("Costo base:")); p.add(txtCosto);
        p.add(new JLabel("Recargo:")); p.add(txtRecargo);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnOrdenFecha = new JButton("Ordenar Fecha");
        JButton btnOrdenCosto = new JButton("Ordenar Costo");
        JButton btnOrdenUnidades = new JButton("Ordenar Unidades");

        JPanel botones = new JPanel();
        botones.add(btnRegistrar);
        botones.add(btnGuardar);
        botones.add(btnCargar);
        botones.add(btnOrdenFecha);
        botones.add(btnOrdenCosto);
        botones.add(btnOrdenUnidades);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Tipo", "Fecha", "Unidades", "Horas", "Costo Total","Calidad"}, 0);
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        add(p, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrar());
        btnGuardar.addActionListener(e -> {
            try { gestor.guardar(); JOptionPane.showMessageDialog(this, "Datos guardados."); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        btnCargar.addActionListener(e -> {
            try { gestor.cargar(); refrescarTabla(); JOptionPane.showMessageDialog(this, "Datos cargados."); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        btnOrdenFecha.addActionListener(e -> { gestor.ordenarPorFecha(); refrescarTabla(); });
        btnOrdenCosto.addActionListener(e -> { gestor.ordenarPorCostoDesc(); refrescarTabla(); });
        btnOrdenUnidades.addActionListener(e -> { gestor.ordenarPorUnidadesDesc(); refrescarTabla(); });
    }

    private void registrar() {
        try {
            String id = txtId.getText().trim();
            String tipo = (String) cbTipo.getSelectedItem();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            int unidades = Integer.parseInt(txtUnidades.getText().trim());
            double horas = Double.parseDouble(txtHoras.getText().trim());
            double costo = Double.parseDouble(txtCosto.getText().trim());
            double recargo = Double.parseDouble(txtRecargo.getText().trim());

            Galleta g = tipo.equals("Chocolate")
                    ? new GalletaChocolate(costo, recargo)
                    : new GalletaVainilla(costo, recargo);

            LoteProduccion lote = new LoteProduccion(id, g, fecha, unidades, horas);
            lote.evaluarCalidad();
            gestor.agregarLote(lote);
            refrescarTabla();
            limpiar();
        } catch (DateTimeParseException dt) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido (YYYY-MM-DD).");
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (LoteProduccion l : gestor.listarLotes()) {
        modeloTabla.addRow(new Object[]{
        l.getIdLote(),
        l.getTipoGalleta().getNombre(),
        l.getFechaFabricacion(),
        l.getUnidadesProducidas(),
        l.getTiempoFabricacion(),
        String.format("%.2f", l.getCostoTotal()),
        String.format("%.0f", l.obtenerPuntajeCalidad())  // <-- muestra el valor
    });
}
    }

    private void limpiar() {
        txtId.setText(""); txtFecha.setText("YYYY-MM-DD");
        txtUnidades.setText(""); txtHoras.setText("");
        txtCosto.setText(""); txtRecargo.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}