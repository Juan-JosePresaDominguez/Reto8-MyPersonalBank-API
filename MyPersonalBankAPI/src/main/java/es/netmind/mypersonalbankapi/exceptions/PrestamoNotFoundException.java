package es.netmind.mypersonalbankapi.exceptions;

public class PrestamoNotFoundException extends Exception {
    public PrestamoNotFoundException() { super("Cliente no encontrado"); }

    public PrestamoNotFoundException(Integer clienteId) {
        super("Cliente con id: " + clienteId + " no encontrado");
    }

    public PrestamoNotFoundException(String message) {
        super(message);
    }
}
