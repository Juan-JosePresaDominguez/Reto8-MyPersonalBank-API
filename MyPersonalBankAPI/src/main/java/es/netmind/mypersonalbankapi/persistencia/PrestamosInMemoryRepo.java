package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.exceptions.PrestamoException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.prestamos.Prestamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PrestamosInMemoryRepo implements IPrestamosRepo {
    private static PrestamosInMemoryRepo instance;
    private static List<Prestamo> prestamos;
    private static IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();

    static {
        prestamos = new ArrayList<>();
        try {
            prestamos.add(new Prestamo(1, LocalDate.now(), 1000.0, 1000.0, 4, 2, false, false, 2));
            prestamos.add(new Prestamo(2, LocalDate.now(), 100000.0, 100000.0, 4, 2, false, false, 25));

            /* Asociamos prestamos */
            List<Cliente> clientes = clientesRepo.getAll();
            clientes.get(0).asociarPrestamo(prestamos.get(0));
            clientes.get(2).asociarPrestamo(prestamos.get(1));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear prestamos: " + e.getMessage());
        }
    }

    private PrestamosInMemoryRepo() {
    }

    public static PrestamosInMemoryRepo getInstance() {
        if (instance == null) instance = new PrestamosInMemoryRepo();
        return instance;
    }

    @Override
    public List<Prestamo> getAll() {
        return prestamos;
    }

    @Override
    public Prestamo getLoanById(Integer id) throws Exception {
        if (prestamos != null) {
            for (Prestamo p : prestamos) {
                if (p.getId().equals(id)) return p;
            }

            throw new PrestamoException("Prestamo no encontrado", ErrorCode.NONEXISTINGLOAN);
        } else throw new PrestamoException("Prestamo no encontrado", ErrorCode.NONEXISTINGLOAN);
    }

    @Override
    public Prestamo addLoan(Prestamo prestamo) throws Exception {
        if (prestamo.validar()) {
            prestamo.setId(prestamos.size() + 1);
            prestamos.add(prestamo);
            return prestamo;
        } else throw new PrestamoException("Prestamo inválido", ErrorCode.INVALIDLOAN);
    }

    @Override
    public boolean deleteLoan(Prestamo prestamo) throws Exception {
        if (prestamo.getId() > 0) {
            for (Prestamo p : prestamos) {
                if (p.getId().equals(prestamo.getId())) prestamos.remove(p);
                return true;
            }
            throw new PrestamoException("Prestamo no encontrado", ErrorCode.NONEXISTINGLOAN);
        } else throw new PrestamoException("Prestamo inválido", ErrorCode.INVALIDLOAN);
    }

    @Override
    public Prestamo updateLoan(Prestamo prestamo) throws Exception {
        if (prestamo.validar()) {
            for (Prestamo c : prestamos) {
                if (c.getId().equals(prestamo.getId())) {
                    c = prestamo;
                    return prestamo;
                }
            }
            throw new PrestamoException("Préstamo no encontrado", ErrorCode.NONEXISTINGLOAN);
        } else throw new PrestamoException("Préstamo inválido", ErrorCode.INVALIDLOAN);
    }

    @Override
    public List<Prestamo> getLoansByClient(Integer uid) throws Exception {
        Cliente elCliente = clientesRepo.getClientById(uid);

        if (elCliente != null)
            return elCliente.getPrestamos();
        else throw new PrestamoException("Cliente NO encontrado para prestamos", ErrorCode.NONEXISTINGCLIENT);
    }

    @Override
    public Prestamo getLoansByClientAndId(Integer uid, Integer aid) throws Exception {
        Cliente elCliente = clientesRepo.getClientById(uid);
        boolean encontrado = false;

        List<Prestamo> prestamos = getLoansByClient(uid);

        if (prestamos != null) {
            for (Prestamo cu : prestamos) {
                if (cu.getId().equals(aid)) return cu;
            }
            throw new PrestamoException("Prestamo NO encontrado para cliente", ErrorCode.NONEXISTINGLOAN);
        } else throw new PrestamoException("Prestamos NO encontrados para cliente", ErrorCode.NONEXISTINGLOAN);

    }


}
