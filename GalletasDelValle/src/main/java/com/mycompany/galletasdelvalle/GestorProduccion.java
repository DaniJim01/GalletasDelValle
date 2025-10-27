/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

import java.io.*;
import java.util.*;

public class GestorProduccion {
    private List<LoteProduccion> lotes = new ArrayList<>();// Colección dinámica donde se almacenan todos los lotes
    private final File archivo = new File("lotes.dat"); // Archivo binario donde se serializa la lista de lotes

    public void agregarLote(LoteProduccion lote) {  // Agrega un nuevo lote a la lista
        lotes.add(lote);
    }

    public List<LoteProduccion> listarLotes() { // Devuelve una copia de la lista de lotes
        return new ArrayList<>(lotes);
    }

    public void ordenarPorFecha() { Collections.sort(lotes); }// Ordena los lotes por fecha

    public void ordenarPorCostoDesc() { // Ordena los lotes por costo total (de mayor a menor)
        lotes.sort(Comparator.comparingDouble(LoteProduccion::getCostoTotal).reversed());
    }

    public void ordenarPorUnidadesDesc() { // Ordena los lotes por unidades producidas (de mayor a menor)
        lotes.sort(Comparator.comparingInt(LoteProduccion::getUnidadesProducidas).reversed());
    }

    public void guardar() throws IOException {    // Guarda los lotes en el archivo (serialización)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lotes);
        }
    }

    @SuppressWarnings("unchecked")    // Carga los lotes desde el archivo (si existe)
    public void cargar() throws IOException, ClassNotFoundException {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) { // El contenido del archivo debe ser exactamente una List<LoteProduccion> serializada.
            lotes = (List<LoteProduccion>) ois.readObject();
        }
    }
}