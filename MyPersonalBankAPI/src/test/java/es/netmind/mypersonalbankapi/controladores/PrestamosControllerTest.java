package es.netmind.mypersonalbankapi.controladores;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class PrestamosControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void dadosPrestamosEnRepo_cuandoPidoPrestamosClienteExistente_entoncesOK() {
        int clienteId = 1;
        PrestamosController.mostrarLista(clienteId);
        System.out.println(outContent);
        assertThat(outContent.toString(), containsString("Lista de prestamos del cliente"));
        assertThat(outContent.toString(), containsString("id=1"));
        assertThat(outContent.toString(), containsString("monto=1000.0"));
    }

    @Test
    void dadosPrestamosEnRepo_cuandoPidoPrestamosClienteSinPrestamos_entoncesOK() {
        int clienteId = 2;
        PrestamosController.mostrarLista(clienteId);
        System.out.println(outContent);
        assertThat(outContent.toString(), containsString("El cliente no tiene prestamos!"));
    }

    @Test
    void dadosPrestamosEnRepo_cuandoPidoPrestamosClienteNoExistente_entoncesNOK() {
        int clienteId = 5;
        PrestamosController.mostrarLista(clienteId);
        System.out.println(outContent);
        assertThat(outContent.toString(), containsString("Cliente NO encontrado"));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}