/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

public class GalletaVainilla extends Galleta {
    private static final long serialVersionUID = 1L;
    private double recargoVainilla;

    public GalletaVainilla(double costoBase, double recargoVainilla) {
        super("Vainilla", costoBase);
        this.recargoVainilla = recargoVainilla;
    }

    @Override
    public double calcularCostoProduccion(int unidades, double horas) {
        double manoObra = 4.5;
        return (getCostoBase() + recargoVainilla) * unidades + manoObra * horas;
    }
}