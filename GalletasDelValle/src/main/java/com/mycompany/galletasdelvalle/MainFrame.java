/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.galletasdelvalle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
// Ventana principal del programa (interfaz gráfica)
public class MainFrame extends JFrame {
    private GestorProduccion gestor = new GestorProduccion();    // Objeto gestor que maneja la lógica de producción
    private JTextField txtId, txtFecha, txtUnidades, txtHoras, txtCosto, txtRecargo; // Campos de texto para ingresar datos
    private JComboBox<String> cbTipo;// ComboBox para seleccionar el tipo de galleta
    private DefaultTableModel modeloTabla; // Modelo para la tabla donde se muestran los lotes

    public MainFrame() {  // Constructor de la ventana
        super("Galletas del Valle S.A.");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
         // Panel principal del formulario
        JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));
        p.setBorder(BorderFactory.createTitledBorder("Registrar Lote"));
// Campos de texto
        txtId = new JTextField();
        txtFecha = new JTextField("YYYY-MM-DD");
        txtUnidades = new JTextField();
        txtHoras = new JTextField();
        txtCosto = new JTextField();
        txtRecargo = new JTextField();
         // ComboBox con tipos de galletas
        cbTipo = new JComboBox<>(new String[]{"Chocolate", "Vainilla"});

         // Etiquetas y campos en el formulario
        p.add(new JLabel("ID Lote:")); p.add(txtId);
        p.add(new JLabel("Tipo de galleta:")); p.add(cbTipo);
        p.add(new JLabel("Fecha:")); p.add(txtFecha);
        p.add(new JLabel("Unidades:")); p.add(txtUnidades);
        p.add(new JLabel("Horas fabricación:")); p.add(txtHoras);
        p.add(new JLabel("Costo base:")); p.add(txtCosto);
        p.add(new JLabel("Recargo:")); p.add(txtRecargo);

        // Botones principales
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");
        JButton btnOrdenFecha = new JButton("Ordenar Fecha");
        JButton btnOrdenCosto = new JButton("Ordenar Costo");
        JButton btnOrdenUnidades = new JButton("Ordenar Unidades");

        // Panel que contiene los botones
        JPanel botones = new JPanel();
        botones.add(btnRegistrar);
        botones.add(btnGuardar);
        botones.add(btnCargar);
        botones.add(btnOrdenFecha);
        botones.add(btnOrdenCosto);
        botones.add(btnOrdenUnidades);

        // Tabla donde se muestran los lotes registrados
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Tipo", "Fecha", "Unidades", "Horas", "Costo Total","Calidad"}, 0);
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

         // Agrega todo al JFrame
        add(p, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
// Eventos de los botones
        btnRegistrar.addActionListener(e -> registrar());// Llama al método registrar al hacer clic
        btnGuardar.addActionListener(e -> {
            try { gestor.guardar();// Guarda los datos en archivo
            JOptionPane.showMessageDialog(this, "Datos guardados."); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        btnCargar.addActionListener(e -> {
            try { 
                gestor.cargar(); // Carga los datos desde el archivo
                refrescarTabla();  // Actualiza la tabla
                JOptionPane.showMessageDialog(this, "Datos cargados."); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });
        
        // Botones para ordenar la tabla
        btnOrdenFecha.addActionListener(e -> { gestor.ordenarPorFecha(); refrescarTabla(); });
        btnOrdenCosto.addActionListener(e -> { gestor.ordenarPorCostoDesc(); refrescarTabla(); });
        btnOrdenUnidades.addActionListener(e -> { gestor.ordenarPorUnidadesDesc(); refrescarTabla(); });
    }
// Método que registra un nuevo lote
    private void registrar() {
        try {
            // Toma los datos ingresados por el usuario
            String id = txtId.getText().trim();
            String tipo = (String) cbTipo.getSelectedItem();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            int unidades = Integer.parseInt(txtUnidades.getText().trim());
            double horas = Double.parseDouble(txtHoras.getText().trim());
            double costo = Double.parseDouble(txtCosto.getText().trim());
            double recargo = Double.parseDouble(txtRecargo.getText().trim());
 // Crea el tipo de galleta según la selección
            Galleta g = tipo.equals("Chocolate")
                    ? new GalletaChocolate(costo, recargo)
                    : new GalletaVainilla(costo, recargo);
// Crea el lote de producción y lo evalúa
            LoteProduccion lote = new LoteProduccion(id, g, fecha, unidades, horas);
            lote.evaluarCalidad();
            // Agrega el lote al gestor y actualiza la tabla
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
// Actualiza los datos mostrados en la tabla
    private void refrescarTabla() {
        modeloTabla.setRowCount(0);// Limpia las filas actuales
        for (LoteProduccion l : gestor.listarLotes()) {// Agrega cada lote como una nueva fila en la tabl
        modeloTabla.addRow(new Object[]{
        l.getIdLote(),
        l.getTipoGalleta().getNombre(),
        l.getFechaFabricacion(),
        l.getUnidadesProducidas(),
        l.getTiempoFabricacion(),
        String.format("%.2f", l.getCostoTotal()),// muestra con dos decimales
        String.format("%.0f", l.obtenerPuntajeCalidad())  // <-- muestra el valor
    });
}
    }
// Limpia todos los campos de texto del formulario
    private void limpiar() {
        txtId.setText(""); txtFecha.setText("YYYY-MM-DD");
        txtUnidades.setText(""); txtHoras.setText("");
        txtCosto.setText(""); txtRecargo.setText("");
    }
// Método principal para ejecutar el programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}