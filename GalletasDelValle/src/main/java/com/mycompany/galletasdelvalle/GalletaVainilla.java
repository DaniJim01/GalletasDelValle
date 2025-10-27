/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.galletasdelvalle;

public class GalletaVainilla extends Galleta {
    private static final long serialVersionUID = 1L; // Identificador único para permitir la serialización del objeto (guardar/cargar desde archivo)
    private double recargoVainilla; // Atributo adicional propio de esta subclase: recargo específico por sabor vainilla

    public GalletaVainilla(double costoBase, double recargoVainilla) { // Constructor
        super("Vainilla", costoBase); // Llama al constructor de la clase padre Galleta, indicando que el tipo es "Vainilla"
        this.recargoVainilla = recargoVainilla; // Asigna el recargo de vainilla al atributo local
    }

    @Override
    public double calcularCostoProduccion(int unidades, double horas) {
        double manoObra = 4.5; // Costo fijo por hora de trabajo
        return (getCostoBase() + recargoVainilla) * unidades + manoObra * horas; // Fórmula: (costo base + recargo vainilla) * unidades + costo mano de obra * horas
    }
}