package es.netmind.mypersonalbankapi.controladores;

//import es.netmind.mypersonalbankapi.config.SpringConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfig.class})
//@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientesControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Autowired
    //private ClientesController clienteControl;
    private IClientesController clienteControl;

    @Test
    void testBeans() throws Exception {
        //Si queremos imprimir por pantalla información, debemos comentar el setUpStreams que camptura la salida
        assertThat(clienteControl, notNullValue());
        //assertEquals(clienteControl.numeroClientes(), 26);
    }

    @BeforeEach
    public void setUpStreams() {
        //clienteControl.connectClientController();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    @Order(1)
    void dadoUsuarioConsultaDetalle_cuandoHayClientes_entoncesObtieneDatosCliente() throws Exception {
        //given
        //int long1 = ClientesController.numeroClientes();
        int long1 = clienteControl.numeroClientes();
        //when
        clienteControl.mostrarDetalle(1);
        //then
        //assertEquals(3, long1);
        //assertThat(outContent.toString(), containsString("Personal{dni='12345678J'} Cliente{id=1, nombre='Juan Juanez', email='jj@j.com', direccion='Calle JJ 1', alta=2023-10-23, activo=true, moroso=false, cuentas=[Cuenta{id=1, fechaCreacion=2023-10-23, saldo=100.0, transacciones=null, interes=1.1, comision=0.2}, Cuenta{id=4, fechaCreacion=2023-10-23, saldo=300.0, transacciones=null, interes=1.1, comision=0.2}], prestamos=[Prestamo{id=1, fechaConcesion=2023-10-23, monto=1000.0, saldo=1000.0, pagos=null, moras=null, interes=4, interesMora=2, moroso=false, liquidado=false}]}"));
        assertThat(outContent.toString(), containsString("Personal{dni='12345678Z'} Cliente{id=1, nombre='Juan Juanez', email='jj@j.com', direccion='C/Huelva 13, Barcelona', alta=2023-10-18, activo=true, moroso=false, cuentas=null, prestamos=[Prestamo{id=1, fechaConcesion=2023-11-07, monto=1000.0, saldo=1000.0, pagos=null, moras=null, interes=4, interesMora=2, moroso=false, liquidado=false}]}"));
    }

    @Test
    @Order(2)
    void dadoUsuarioConsultaDetalle_cuandoClienteNoExiste_entoncesError() {
        //given
        int long1 = 0;
        try {
            long1 = clienteControl.numeroClientes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //when
        clienteControl.mostrarDetalle(500);
        //then
        //assertEquals(3, long1);
        assertThat(outContent.toString(), containsString("Cliente NO encontrado"));
        //assertThat(outContent.toString(), containsString("Oops ha habido un problema"));
    }

    @Test
    @Order(3)
    void dadoUsuarioQuiereModificarCliente_cuandoDatosNOK_entoncesModificacionNOK() throws Exception {
        String[] datos = {
                "Francisco Lopez",
                "emaile|gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "true",
                "false",
                "12345678Z"
        };
        clienteControl.actualizar(1, datos);
        //clienteControl.commitClientController();

        clienteControl.mostrarLista();
        assertFalse(outContent.toString().contains("nombre='Francisco Lopez'"));
    }

    @Test
    @Order(4)
    @Transactional
    void dadoUsuarioQuiereModificarCliente_cuandoDatosOK_entoncesModificacionOK() {
        String[] datos = {
                "Carlos Lopez",
                "emaile@gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "true",
                "false",
                "12345678Z"
        };
        clienteControl.actualizar(1, datos);
        //clienteControl.rollbackClientController();

        assertThat(outContent.toString(), containsString("Carlos Lopez"));
    }

    @Test
    @Order(5)
    void dadoUsuarioQuiereConsultar_cuandoHayClientes_entoncesObtieneListaClientes() throws Exception {
        //given
        int long1 = clienteControl.numeroClientes();
        //when
        clienteControl.mostrarLista();
        //then
        //assertEquals(3, long1);
        assertThat(outContent.toString(), containsString("(3) Servicios Informatico SL 3"));
    }

    @Test
    @Order(6)
    @Transactional
    void dadoUsuarioQuiereConsultar_cuandoNoHayClientes_entoncesObtieneListaVacia() throws Exception {
        //No borramos los 3 primeros - error por integridad referencial
        //Como no existen hasta el 100, al fallar el borrado la transacción se marca para rollback
        for (int i = 4; i < 100; i++) {
            clienteControl.eliminar(i);
        }
        int long1 = clienteControl.numeroClientes();
        //when
        clienteControl.mostrarLista();
        //clienteControl.rollbackClientController();
        //then
        assertEquals(3, long1);
        //Ponemos 3 en lugar de 0 porque los 3 usuarios iniciales de la base de datos tienen foreign key.
    }

    @Test
    @Order(7)
    @Transactional
    void dadoUsuarioQuiereAltaCliente_cuandoDatosOK_entoncesAltaOK() {
        String[] datos = {
                "personal",
                "Carlos Sanchez",
                "emaile@gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "12345678Z"
        };
        clienteControl.add(datos);
        String respuesta = outContent.toString();

        assertTrue(respuesta.contains("Cliente añadido"));
    }

    @Test
    @Order(8)
    void dadoUsuarioQuiereAltaCliente_cuandoDatosNOK_entoncesAltaNOK() throws Exception {
        String[] datos = {
                "empresa",
                "Servicios Informatico SL",
                "sis.com",
                "Calle SI 3",
                "2023-10-23",
                "J12345678"
        };
        clienteControl.add(datos);

        clienteControl.mostrarLista();
        System.out.println(outContent);
        //Test NOK
        //assertThat(outContent.toString(), containsString("Oops ha habido un problema, inténtelo más tarde 😞!"));

        //Test OK (Cliente no válido, falta @ en email)
        assertThat(outContent.toString(), containsString("Cliente NO válido"));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
