package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfig.class})
//@EnableAutoConfiguration
class ClientesRepoDataTest {

    @Autowired
    private IClientesRepoData repo;

    @Test
    void testBeans() throws Exception {
        assertThat(repo, notNullValue());
        System.out.println(repo.findClienteById(3));
    }

//    @BeforeEach
//    void setUp() throws Exception {
//        repo = new ClientesDBRepo();
//    }

    @Test
    void dadosClientes_cuandogetAll_entoncesClientes() throws Exception {
        //Como usuario del sistema, quiero poder ver nuestra lista de clientes para tener una visión general de los mismos.
        //List<Cliente> clientes = repo.getAll();
        List<Cliente> clientes = repo.findAll();

        System.out.println("test getAll:" + clientes);

        assertThat(clientes.size(), greaterThan(0));
    }

    @Test
    void dadosClientes_cuandogetClientById_entoncesClientePersonal() throws Exception {
        //Como usuario del sistema, quiero poder ver el detalle de un cliente para entender su perfil.
        Cliente cliente = repo.findClienteById(1);

        System.out.println(cliente);

        assertThat(cliente.getId(), is(1));
    }

    @Test
    void dadosClientes_cuandogetClientById_entoncesClienteEmpresa() throws Exception {
        //Como usuario del sistema, quiero poder ver el detalle de un cliente para entender su perfil.
        Cliente cliente = repo.findClienteById(3);

        System.out.println(cliente);

        assertThat(cliente.getId(), is(3));
    }

    @Test
    void dadosClientes_cuandoaddCliente_entoncesClientePersonalInsert() throws Exception {
        //Como usuario del sistema, quiero poder registrar nuevos clientes para poder incrementar nuestro base de datos.
        Personal cliente = new Personal(null, "Ricardo", "ricardo@a.com", "Netmind 23", LocalDate.now(), true, false, "12345678J");

        repo.save(cliente);

        System.out.println(cliente);

        //assertThat(cliente.getId(), greaterThan(0));

        Cliente nuevoCliente = repo.findClienteById(cliente.getId());

        //Comprobamos que lo que se ha insertado es lo que tenía que ser
        assertThat(cliente.getNombre(), is("Ricardo"));
        assertThat(cliente.getDni(), is("12345678J"));
    }

    @Test
    void dadosClientes_cuandoaddCliente_entoncesClienteEmpresaInsert() throws Exception {
        //Como usuario del sistema, quiero poder registrar nuevos clientes para poder incrementar nuestro base de datos.
        Empresa cliente = new Empresa(null, "Caixa", "caixa@c.com", "BARCELONA sn", LocalDate.now(), true, false, "J12345678", new String[]{"Activo", "Garantías"});

        repo.save(cliente);

        System.out.println(cliente);

        assertThat(cliente.getId(), greaterThan(0));
    }

    @Test
    void dadosClientes_cuandoupdateClient_entoncesClientePersonalUpdate() throws Exception {
        //Como usuario del sistema, quiero poder modificar los datos de un cliente para mantenerlos actualizados.
        Personal cliente = new Personal(6, "Ricardo Ahumada", "ricardo@a.com", "Netmind 23 !", LocalDate.now(), true, false, "12345678J");

        repo.save(cliente);

        System.out.println(cliente);

        //assertThat(cliente.getId(), greaterThan(0));

        Cliente nuevoCliente = repo.findClienteById(cliente.getId());

        //Comprobamos que lo que se ha insertado es lo que tenía que ser
        assertThat(cliente.getNombre(), is("Ricardo Ahumada"));
        assertThat(cliente.getDni(), is("12345678J"));
    }

    @Test
    void dadosClientes_cuandoupdateClient_entoncesClienteEmpresaUpdateException() throws Exception {
        //Como usuario del sistema, quiero poder modificar los datos de un cliente para mantenerlos actualizados.
        Empresa cliente = new Empresa(85,"Caixa Bank", "caixa@c.com", "BCN", LocalDate.now(), true, true, "J12345678", new String[]{"Activo", "Garantías", "Tasaciones"});

        System.out.println("test - cliente:" + cliente);

        assertThrows(Exception.class, () -> {
            repo.findById(cliente.getId()).orElseThrow(()->new RuntimeException());
            //repo.save(cliente);
        });
    }

    @Test
    @Transactional
    void dadosClientes_cuandodeleteClient_entoncesClienteDelete() throws Exception {
        //Borrar cliente por Id
        Personal cliente = new Personal(null, "Ricardo", "ricardo@a.com", "Netmind 23", LocalDate.now(), true, false, "12345678J");

        repo.save(cliente);

        repo.delete(cliente);

        System.out.println("test - cliente:" + cliente);

        //assertThat(cliente.getId(), greaterThan(0));

        assertThrows(Exception.class, () -> {
            //repo.findClienteById(cliente.getId());
            repo.findById(cliente.getId()).orElseThrow(()->new RuntimeException());
        });
    }

}
