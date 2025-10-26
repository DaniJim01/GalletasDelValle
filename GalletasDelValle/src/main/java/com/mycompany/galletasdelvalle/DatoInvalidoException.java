
package com.mycompany.galletasdelvalle;

public class DatoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L; //Establece una version unica de serializacion para evitar problemas de compatibilidad

    public DatoInvalidoException(String mensaje) { //Metodo caundo hay un dato invalido
        super(mensaje); //Envia el mensaje en caso de error de forma prioritaria (Super)
    }

}
