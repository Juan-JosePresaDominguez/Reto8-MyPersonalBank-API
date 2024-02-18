package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

import java.util.List;

public interface IClientesRepo {
    public List<Cliente> getAll() throws Exception;

    public Cliente getClientById(Integer id) throws Exception;

    public Cliente addClient(Cliente cliente) throws Exception;

    public boolean deleteClient(Cliente cliente) throws Exception;

    public Cliente updateClient(Cliente cliente) throws Exception;

    void commitClientRepo() throws Exception;

    void rollbackClientRepo() throws Exception;

    void connectClientRepo() throws Exception;

}
