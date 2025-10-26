/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

import java.io.*;
import java.util.*;

public class GestorProduccion {
    private List<LoteProduccion> lotes = new ArrayList<>();
    private final File archivo = new File("lotes.dat");

    public void agregarLote(LoteProduccion lote) {
        lotes.add(lote);
    }

    public List<LoteProduccion> listarLotes() {
        return new ArrayList<>(lotes);
    }

    public void ordenarPorFecha() { Collections.sort(lotes); }

    public void ordenarPorCostoDesc() {
        lotes.sort(Comparator.comparingDouble(LoteProduccion::getCostoTotal).reversed());
    }

    public void ordenarPorUnidadesDesc() {
        lotes.sort(Comparator.comparingInt(LoteProduccion::getUnidadesProducidas).reversed());
    }

    public void guardar() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lotes);
        }
    }

    @SuppressWarnings("unchecked")
    public void cargar() throws IOException, ClassNotFoundException {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            lotes = (List<LoteProduccion>) ois.readObject();
        }
    }
}