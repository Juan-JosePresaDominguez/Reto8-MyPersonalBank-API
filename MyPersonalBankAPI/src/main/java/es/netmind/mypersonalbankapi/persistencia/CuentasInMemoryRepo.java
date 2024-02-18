package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.CuentaException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.cuentas.Ahorro;
import es.netmind.mypersonalbankapi.modelos.cuentas.Corriente;
import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CuentasInMemoryRepo implements ICuentasRepo {
    private static CuentasInMemoryRepo instance;
    private static List<Cuenta> cuentas;
    private static IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();

    static {
        cuentas = new ArrayList<>();
        try {
            cuentas.add(new Ahorro(1, LocalDate.now(), 100.0, 1.1, 0.2));
            cuentas.add(new Corriente(1, LocalDate.now(), 200.0, 0.5, 0.2));
            cuentas.add(new Ahorro(3, LocalDate.now(), 300.0, 1.1, 0.2));
            cuentas.add(new Ahorro(4, LocalDate.now(), 300.0, 1.1, 0.2));

            /* Asociamos cuentas */
            List<Cliente> clientes = clientesRepo.getAll();
            clientes.get(0).asociarCuenta(cuentas.get(0));
            clientes.get(0).asociarCuenta(cuentas.get(3));
            clientes.get(1).asociarCuenta(cuentas.get(2));
            clientes.get(2).asociarCuenta(cuentas.get(1));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear cuentas: " + e.getMessage());
        }
    }

    private CuentasInMemoryRepo() {
    }

    public static CuentasInMemoryRepo getInstance() {
        if (instance == null) instance = new CuentasInMemoryRepo();
        return instance;
    }

    @Override
    public List<Cuenta> getAll() {
        return cuentas;
    }

    @Override
    public Cuenta getAccountById(Integer id) throws Exception {
        if (cuentas != null) {
            for (Cuenta c : cuentas) {
                if (c.getId().equals(id)) return c;
            }

            throw new CuentaException("Cuenta no encontrada", ErrorCode.NONEXISTINGACCOUNT);
        }
        throw new CuentaException("Cuenta no encontrada", ErrorCode.NONEXISTINGACCOUNT);
    }

    @Override
    public Cuenta addAccount(Cuenta cuenta) throws Exception {
        if (cuenta.validar()) {
            cuenta.setId(cuentas.size() + 1);
            cuentas.add(cuenta);
            return cuenta;
        } else throw new CuentaException("Cuenta inválida", ErrorCode.INVALIDACCOUNT);
    }

    @Override
    public boolean deleteAccount(Cuenta cuenta) throws Exception {
        if (cuenta.getId() > 0) {
            for (Cuenta c : cuentas) {
                if (c.getId().equals(cuenta.getId())) {
                    cuentas.remove(c);
                    return true;
                }
            }
            throw new CuentaException("Cuenta no encontrada", ErrorCode.NONEXISTINGACCOUNT);
        } else throw new CuentaException("Cuenta inválida", ErrorCode.INVALIDACCOUNT);
    }

    @Override
    public Cuenta updateAccount(Cuenta cuenta) throws Exception {
        if (cuenta.validar()) {
            for (Cuenta c : cuentas) {
                if (c.getId().equals(cuenta.getId())) {
                    c = cuenta;
                    return cuenta;
                }
            }
            throw new CuentaException("Cliente no encontrada", ErrorCode.NONEXISTINGCLIENT);
        } else throw new CuentaException("Cliente inválida", ErrorCode.INVALIDACCOUNT);
    }

    @Override
    public List<Cuenta> getAccountsByClient(Integer uid) throws Exception {
        Cliente elCliente = clientesRepo.getClientById(uid);

        if (elCliente != null)
            return elCliente.getCuentas();
        else throw new CuentaException("Cliente NO encontrado para cuentas", ErrorCode.NONEXISTINGCLIENT);
    }

    @Override
    public Cuenta getAccountsByClientAndId(Integer uid, Integer aid) throws Exception {
        Cliente elCliente = clientesRepo.getClientById(uid);
        boolean encontrado = false;

        List<Cuenta> cuentas = getAccountsByClient(uid);

        if (cuentas != null) {
            for (Cuenta cu : cuentas) {
                if (cu.getId().equals(aid)) return cu;
            }
            throw new CuentaException("Cuenta NO encontrada para cliente", ErrorCode.NONEXISTINGACCOUNT);
        } else throw new CuentaException("Cuentas NO encontradas para cliente", ErrorCode.NONEXISTINGACCOUNT);

    }


}
