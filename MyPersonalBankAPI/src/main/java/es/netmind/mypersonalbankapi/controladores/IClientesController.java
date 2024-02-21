package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepo;

public interface IClientesController {
    void mostrarLista() throws Exception;
    int numeroClientes() throws Exception;
    void mostrarDetalle(Integer uid);
    void add(String[] args);
    void eliminar(Integer uid);
    void actualizar(Integer uid, String[] args);
    void evaluarPrestamo(Integer uid, Double cantidad);

    Cliente updateCliente(Integer id, Cliente cliente) throws ClienteNotFoundException;
}
