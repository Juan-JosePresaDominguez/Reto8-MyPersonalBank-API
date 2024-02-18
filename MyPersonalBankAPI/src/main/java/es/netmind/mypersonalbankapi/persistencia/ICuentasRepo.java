package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;

import java.util.List;

public interface ICuentasRepo {
    public List<Cuenta> getAll();

    public Cuenta getAccountById(Integer id) throws Exception;

    public Cuenta addAccount(Cuenta cuenta) throws Exception;

    public boolean deleteAccount(Cuenta cuenta) throws Exception;

    public Cuenta updateAccount(Cuenta cuenta) throws Exception;

    public List<Cuenta> getAccountsByClient(Integer uid) throws Exception;
    public Cuenta getAccountsByClientAndId(Integer uid, Integer aid) throws Exception;

}
