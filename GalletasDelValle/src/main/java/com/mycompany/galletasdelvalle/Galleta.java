/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

import java.io.Serializable;

public abstract class Galleta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private double costoBase;

    public Galleta(String nombre, double costoBase) {
        this.nombre = nombre;
        this.costoBase = costoBase;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        this.nombre = nombre;
    }

    public double getCostoBase() { return costoBase; }

    public void setCostoBase(double costoBase) {
        if (costoBase < 0)
            throw new IllegalArgumentException("El costo base no puede ser negativo");
        this.costoBase = costoBase;
    }

    public abstract double calcularCostoProduccion(int unidades, double horas);
}