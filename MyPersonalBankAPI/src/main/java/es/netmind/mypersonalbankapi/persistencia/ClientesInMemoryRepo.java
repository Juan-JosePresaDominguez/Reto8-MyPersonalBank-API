package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientesInMemoryRepo implements IClientesRepo {
    private static ClientesInMemoryRepo instance;
    private final static List<Cliente> clientes;

    static {
        clientes = new ArrayList<>();
        try {
            clientes.add(new Personal(1, "Juan Juanez", "jj@j.com", "Calle JJ 1", LocalDate.now(), true, false, "12345678J"));
            clientes.add(new Personal(2, "Luisa Perez", "lp@l.com", "Calle LP 2", LocalDate.now(), true, false, "12345678L"));
            clientes.add(new Empresa(3, "Servicios Informatico SL", "si@s.com", "Calle SI 3", LocalDate.now(), true, false, "J12345678", new String[]{"Dev", "Marketing"}));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear clientes: " + e.getMessage());
        }
    }

    public ClientesInMemoryRepo() {
    }

    public static ClientesInMemoryRepo getInstance() {
        if (instance == null) instance = new ClientesInMemoryRepo();
        return instance;
    }

    @Override
    public List<Cliente> getAll() {
        return clientes;
    }

    @Override
    public Cliente getClientById(Integer id) throws Exception {
        if (clientes != null) {
            for (Cliente c : clientes) {
                if (c.getId() == id) return c;
            }

            throw new ClienteException("Cliente no existe", ErrorCode.NONEXISTINGCLIENT);
        } else throw new ClienteException("Cliente no existe", ErrorCode.NONEXISTINGCLIENT);
    }

    @Override
    public Cliente addClient(Cliente cliente) throws Exception {
        if (cliente.validar()) {
            cliente.setId(clientes.size() + 1);
            clientes.add(cliente);
            return cliente;
        }
        throw new ClienteException("Cliente no válido", ErrorCode.INVALIDCLIENT);
    }

    @Override
    public boolean deleteClient(Cliente cliente) throws Exception {
        if (cliente.getId() > 0) {
            for (Cliente c : clientes) {
                if (c.getId().equals(cliente.getId())) clientes.remove(c);
                return true;
            }
            throw new ClienteException("Cliente no existe", ErrorCode.NONEXISTINGCLIENT);
        } else throw new ClienteException("Cliente no válido", ErrorCode.INVALIDCLIENT);
    }

    @Override
    public Cliente updateClient(Cliente cliente) throws Exception {
        if (cliente.validar()) {
            for (Cliente c : clientes) {
                if (c.getId().equals(cliente.getId())) {
                    c = cliente;
                    return cliente;
                }
            }
            throw new ClienteException("Cliente no existe", ErrorCode.NONEXISTINGCLIENT);
        } else throw new ClienteException("Cliente no válido", ErrorCode.INVALIDCLIENT);
    }

    @Override
    public void commitClientRepo() throws Exception {

    }

    @Override
    public void rollbackClientRepo() throws Exception {

    }

    @Override
    public void connectClientRepo() throws Exception {

    }
}
