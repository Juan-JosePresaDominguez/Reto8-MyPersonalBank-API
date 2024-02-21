package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

public interface IClientesController {
    void mostrarLista() throws Exception;
    int numeroClientes() throws Exception;
    void mostrarDetalle(Integer uid);
    void add(String[] args);
    void eliminar(Integer uid);
    void actualizar(Integer uid, String[] args);
    void evaluarPrestamo(Integer uid, Double cantidad);

    Personal updatePersonal(Integer id, Personal personal) throws ClienteNotFoundException;
    Empresa updateEmpresa(Integer id, Empresa empresa) throws ClienteNotFoundException;
}
