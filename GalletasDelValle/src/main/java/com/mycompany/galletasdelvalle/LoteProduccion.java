/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

import java.io.Serializable;
import java.time.LocalDate;

public class LoteProduccion implements Serializable, Comparable<LoteProduccion>, ControlCalidad {
    private static final long serialVersionUID = 1L;

    private String idLote;
    private Galleta tipoGalleta;
    private LocalDate fechaFabricacion;
    private int unidadesProducidas;
    private double tiempoFabricacion;
    private double costoTotal;
    private double puntajeCalidad;


    public LoteProduccion(String idLote, Galleta tipoGalleta, LocalDate fechaFabricacion,
                          int unidadesProducidas, double tiempoFabricacion) {
        setIdLote(idLote);
        setTipoGalleta(tipoGalleta);
        setFechaFabricacion(fechaFabricacion);
        setUnidadesProducidas(unidadesProducidas);
        setTiempoFabricacion(tiempoFabricacion);
        calcularCosto();
    }

    private void calcularCosto() {
        this.costoTotal = tipoGalleta.calcularCostoProduccion(unidadesProducidas, tiempoFabricacion);
    }

    public String getIdLote() { return idLote; }

    public void setIdLote(String idLote) {
        if (idLote == null || idLote.isEmpty())
            throw new DatoInvalidoException("ID inválido");
        this.idLote = idLote;
    }

    public Galleta getTipoGalleta() { return tipoGalleta; }

    public void setTipoGalleta(Galleta tipoGalleta) {
        if (tipoGalleta == null)
            throw new DatoInvalidoException("Tipo de galleta inválido");
        this.tipoGalleta = tipoGalleta;
    }

    public LocalDate getFechaFabricacion() { return fechaFabricacion; }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        if (fechaFabricacion == null)
            throw new DatoInvalidoException("Fecha inválida");
        this.fechaFabricacion = fechaFabricacion;
    }

    public int getUnidadesProducidas() { return unidadesProducidas; }

    public void setUnidadesProducidas(int unidadesProducidas) {
        if (unidadesProducidas <= 0)
            throw new DatoInvalidoException("Unidades deben ser mayores a 0");
        this.unidadesProducidas = unidadesProducidas;
        calcularCosto();
    }

    public double getTiempoFabricacion() { return tiempoFabricacion; }

    public void setTiempoFabricacion(double tiempoFabricacion) {
        if (tiempoFabricacion < 0)
            throw new DatoInvalidoException("Tiempo inválido");
        this.tiempoFabricacion = tiempoFabricacion;
        calcularCosto();
    }

    public double getCostoTotal() { return costoTotal; }

    @Override
    public int compareTo(LoteProduccion o) {
        return this.fechaFabricacion.compareTo(o.fechaFabricacion);
    }
    
    
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