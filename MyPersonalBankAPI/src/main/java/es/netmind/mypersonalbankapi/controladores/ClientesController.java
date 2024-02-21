package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.prestamos.Prestamo;
import es.netmind.mypersonalbankapi.persistencia.*;
import es.netmind.mypersonalbankapi.utils.ClientesUtils;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Component
@ToString
//public class ClientesController {
public class ClientesController implements IClientesController {

    //private IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();
    //private IClientesRepo clientesRepo = ClientesDBRepo.getInstance();   // RETO 4 - SGBD
    @Autowired // Para el bean clientesRepo no hace falta porque ya está definido en RepoConfig.java ?
    //private IClientesRepo clientesRepo;         // RETO 5 - SPRING
    private IClientesRepoData clientesRepo;       // RETO 7 - SPRING-DATA
    private ICuentasRepo cuentasRepo = CuentasInMemoryRepo.getInstance();
    private IPrestamosRepo prestamosRepo = PrestamosInMemoryRepo.getInstance();

    public void mostrarLista() throws Exception {
        System.out.println("\nLista de clientes:");
        System.out.println("───────────────────────────────────");
        List<Cliente> clientes = clientesRepo.getAll();
        for (Cliente cl : clientes) {

            try {
                cl.validar();
                System.out.println("(" + cl.getId() + ") " + cl.getNombre() + " " + cl.getId());
            } catch (ClienteException e) {
                System.out.println("El cliente solicitado tiene datos erroneos 😞! Ponte en contacto con el admin. \nCode: " + e.getCode());
            } catch (Exception e) {
                System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            }

        }
    }

    public int numeroClientes() throws Exception {
        return clientesRepo.getAll().size();
    }

    public void mostrarDetalle(Integer uid) {
        System.out.println("\nDetalle de cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Cliente cl = clientesRepo.findById(uid).orElseThrow(()->new ClienteNotFoundException());
            System.out.println(cl);
        } catch (ClienteNotFoundException e) {
            System.out.println("Cliente NO encontrado 😞!");
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(String[] args) {
        System.out.println("\nAñadiendo cliente");
        System.out.println("───────────────────────────────────");
        try {
            Cliente cl = ClientesUtils.extractClientFromArgsForCreate(args);
            clientesRepo.save(cl);
            System.out.println("Cliente añadido: " + cl + " 🙂");
            mostrarLista();
        } catch (ClienteException e) {
            System.out.println("Cliente NO válido 😞! \nCode: " + e.getCode());
        } catch (DateTimeException e) {
            System.out.println("⚠ LAS FECHAS DEBEN TENER EL FORMATO yyyy-mm-dd, por ejemplo 2023-12-01 ⚠");
        } catch (Exception e) {
            //System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            System.out.println("Cliente NO válido 😞!");
            e.printStackTrace();
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminar(Integer uid) {
        System.out.println("\nBorrando cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            clientesRepo.deleteById(uid);
            System.out.println("Cliente borrado 🙂!!");
            //this.mostrarLista();
        } catch (Exception e) {
            System.out.println("Cliente NO encontrado\n");
        }
        //} catch (ClienteException e) {
        //    System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        //} catch (Exception e) {
        //    System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
        //}

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void actualizar(Integer uid, String[] args) {
        System.out.println("\nActualizando cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Cliente cl = clientesRepo.findClienteById(uid);
            System.out.println("cl.getClass():" + cl.getClass() + " " + cl);
            ClientesUtils.updateClientFromArgs(cl, args);
            clientesRepo.save(cl);
            System.out.println("Cliente actualizado 🙂!!");
            System.out.println(cl);
            //this.mostrarLista();
        } catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (DateTimeException e) {
            System.out.println("⚠ LAS FECHAS DEBEN TENER EL FORMATO yyyy-mm-dd, por ejemplo 2023-12-01 ⚠");
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            e.printStackTrace();
        }
    }

    public Cliente updateCliente(Integer id, Cliente cliente) throws ClienteNotFoundException {
        Cliente newCliente = clientesRepo.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        newCliente.setNombre(cliente.getNombre());
        newCliente.setEmail(cliente.getEmail());
        return clientesRepo.save(newCliente);
    }

    public void evaluarPrestamo(Integer uid, Double cantidad) {
        System.out.println("\nEvaluando préstamos de " + cantidad + " EUR para el  cliente: " + uid);
        System.out.println("───────────────────────────────────");

        try {
            Cliente cliente = clientesRepo.findClienteById(uid);
            System.out.println("Saldo total del cliente: " + cliente.obtenerSaldoTotal());
            int numPrestamos = cliente.getPrestamos() != null ? cliente.getPrestamos().size() : 0;
            System.out.println("Número total de préstamos del cliente: " + numPrestamos);

            Prestamo prestamoSolictado = new Prestamo(null, LocalDate.now(), cantidad, cantidad, 10, 5, false, false, 5);

            boolean aceptable = cliente.evaluarSolicitudPrestamo(prestamoSolictado);
            if (aceptable) System.out.println("SÍ se puede conceder 🙂!!");
            else System.out.println("NO puede conceder 😞!! Saldo insuficiente.");

        } catch (ClienteException e) {
            System.out.println("Cliente NO encontrado 😞! \nCode: " + e.getCode());
        } catch (Exception e) {
            System.out.println("Oops ha habido un problema, inténtelo más tarde 😞!");
            e.printStackTrace();
        }


    }

    public void setClientesRepo(IClientesRepoData clientesRepo) {
        this.clientesRepo = clientesRepo;
    }
}
