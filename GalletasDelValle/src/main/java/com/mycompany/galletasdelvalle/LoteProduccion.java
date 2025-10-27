/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

import java.io.Serializable;
import java.time.LocalDate;

// Clase que representa un lote de producción
// Implementa Serializable (para guardar datos), Comparable (para ordenar) y ControlCalidad
public class LoteProduccion implements Serializable, Comparable<LoteProduccion>, ControlCalidad {
    private static final long serialVersionUID = 1L;
 // Atributos del lote
    private String idLote;
    private Galleta tipoGalleta;
    private LocalDate fechaFabricacion;
    private int unidadesProducidas;
    private double tiempoFabricacion;
    private double costoTotal;
    private double puntajeCalidad;

  // Constructor que recibe los datos del lote
    public LoteProduccion(String idLote, Galleta tipoGalleta, LocalDate fechaFabricacion,
                          int unidadesProducidas, double tiempoFabricacion) {
        setIdLote(idLote);
        setTipoGalleta(tipoGalleta);
        setFechaFabricacion(fechaFabricacion);
        setUnidadesProducidas(unidadesProducidas);
        setTiempoFabricacion(tiempoFabricacion);
        calcularCosto();
    }

    private void calcularCosto() { // Calcula el costo total del lote usando el método de la galleta
        this.costoTotal = tipoGalleta.calcularCostoProduccion(unidadesProducidas, tiempoFabricacion);
    }

    public String getIdLote() { return idLote; }// Getter del ID del lote

    public void setIdLote(String idLote) {    // Setter del ID del lote con validación
        if (idLote == null || idLote.isEmpty())
            throw new DatoInvalidoException("ID inválido");
        this.idLote = idLote;
    }

    public Galleta getTipoGalleta() { return tipoGalleta; }   // Getter del tipo de galleta

    public void setTipoGalleta(Galleta tipoGalleta) {// Setter del tipo de galleta
        if (tipoGalleta == null)
            throw new DatoInvalidoException("Tipo de galleta inválido");
        this.tipoGalleta = tipoGalleta;
    }

    public LocalDate getFechaFabricacion() { return fechaFabricacion; } // Getter y Setter de la fecha de fabricación

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        if (fechaFabricacion == null)
            throw new DatoInvalidoException("Fecha inválida");
        this.fechaFabricacion = fechaFabricacion;
    }

    public int getUnidadesProducidas() { return unidadesProducidas; }

    public void setUnidadesProducidas(int unidadesProducidas) { // Getter y Setter de unidades producidas
        if (unidadesProducidas <= 0)
            throw new DatoInvalidoException("Unidades deben ser mayores a 0");
        this.unidadesProducidas = unidadesProducidas;
        calcularCosto();
    }

    public double getTiempoFabricacion() { return tiempoFabricacion; }    // Getter y Setter del tiempo de fabricación

    public void setTiempoFabricacion(double tiempoFabricacion) {
        if (tiempoFabricacion < 0)
            throw new DatoInvalidoException("Tiempo inválido");
        this.tiempoFabricacion = tiempoFabricacion;
        calcularCosto();
    }

    public double getCostoTotal() { return costoTotal; }    // Getter del costo total
 // Método compareTo para ordenar por fecha
    @Override
    public int compareTo(LoteProduccion o) {
        return this.fechaFabricacion.compareTo(o.fechaFabricacion);
    }
    
 // Métodos de la interfaz ControlCalidad
    @Override
public void evaluarCalidad() {
    if (unidadesProducidas < 100) {
        puntajeCalidad = 70;
    } else if (unidadesProducidas <= 500) {
        puntajeCalidad = 85;
    } else {
        puntajeCalidad = 95;
    }

    // Penaliza si tarda mucho
    if (tiempoFabricacion > 8) {
        puntajeCalidad -= 10;
    }

    if (puntajeCalidad < 0) puntajeCalidad = 0;
}

@Override
public double obtenerPuntajeCalidad() {
    return puntajeCalidad;
}
}