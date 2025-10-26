package com.mycompany.galletasdelvalle;

import java.io.Serializable; //Paquete para  la serializacion

public abstract class Galleta implements Serializable { //Implementa la serializacion y definicion de la clase coom abstracta o clase padre
    private static final long serialVersionUID = 1L; //Se establece la version de la serializacion para evitar problemas de compatibilidad
    private String nombre; //Definicion atributos
    private double costoBase;

    public Galleta(String nombre, double costoBase) { //Constructotr
        this.nombre = nombre;
        this.costoBase = costoBase;
    }

    public String getNombre() { return nombre; } //Getters y setters

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) //Validacion si no se ha ingresado nada en el apartado de nombre
            throw new IllegalArgumentException("El nombre no puede estar vacío"); //Crea un nuevo error y llama al metodo que va a mostrar el mensaje de que esta vacio
        this.nombre = nombre; //Setter
    }

    public double getCostoBase() { return costoBase; } //Getters y setters

    public void setCostoBase(double costoBase) {
        if (costoBase < 0) //Verifica que el costo base sea valido
            throw new IllegalArgumentException("El costo base no puede ser negativo"); //Llama al metodo de errores y crea un nuevo error junto con imprimir el mensaje
        this.costoBase = costoBase; //Setter
    }

    public abstract double calcularCostoProduccion(int unidades, double horas); //Metodo abstracto para calcular costo de produccion

}
