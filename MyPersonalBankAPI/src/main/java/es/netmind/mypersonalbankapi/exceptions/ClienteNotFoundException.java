package es.netmind.mypersonalbankapi.exceptions;

public class ClienteNotFoundException extends Exception {
    public ClienteNotFoundException() { super("Cliente no encontrado"); }

    public ClienteNotFoundException(Integer clienteId) {
        super("Cliente con id: " + clienteId + " no encontrado");
    }

    public ClienteNotFoundException(String message) {
        super(message);
    }
}
