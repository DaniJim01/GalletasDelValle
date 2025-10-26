

package com.mycompany.galletasdelvalle;

public class GalletaChocolate extends Galleta { //Extiende la clase padre de galleta
    private static final long serialVersionUID = 1L; //Define la version de serializacion
    private double recargoChocolate; //Atributo de cargos extra

    public GalletaChocolate(double costoBase, double recargoChocolate) { //Metodo de galleta chocolate
        super("Chocolate", costoBase); //Muestra el costo base de el chocolate
        this.recargoChocolate = recargoChocolate; //Setter
    }

    @Override
    public double calcularCostoProduccion(int unidades, double horas) { //Override con el metodo abstracto con los atributos que debemos de utilizar
        double manoObra = 5.0; //Atributo de lo que cuesta la mano de obra
        return (getCostoBase() + recargoChocolate) * unidades + manoObra * horas; //El metodo devuelve el resultado de la formula para conseguir el costo de produccion
    }

}
