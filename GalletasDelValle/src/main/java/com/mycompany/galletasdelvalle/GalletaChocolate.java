

package com.mycompany.galletasdelvalle;

public class GalletaChocolate extends Galleta {
    private static final long serialVersionUID = 1L;
    private double recargoChocolate;

    public GalletaChocolate(double costoBase, double recargoChocolate) {
        super("Chocolate", costoBase);
        this.recargoChocolate = recargoChocolate;
    }

    @Override
    public double calcularCostoProduccion(int unidades, double horas) {
        double manoObra = 5.0;
        return (getCostoBase() + recargoChocolate) * unidades + manoObra * horas;
    }
}