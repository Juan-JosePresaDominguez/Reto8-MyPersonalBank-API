package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static es.netmind.mypersonalbankapi.exceptions.ErrorCode.INVALIDCLIENT;
import static es.netmind.mypersonalbankapi.exceptions.ErrorCode.NONEXISTINGCLIENT;

@Getter
@Setter
@Repository
public class ClientesRepoJPA implements IClientesRepo {

    @PersistenceContext // Accede al emf; emf.createEntityManager();
    EntityManager em;

    private static ClientesRepoJPA instance;

    private static String db_url = null;
    //private String db_url = null;

    private Connection conn = null;

    //La URL se debe definir en RepoConfig.java, sino se produce el error "java.sql.SQLException: The url cannot be null"
//    public ClientesDBRepo() throws Exception {
//        PropertyValues props = new PropertyValues();
//        db_url = props.getPropValues().getProperty("db_url");
//    }

    public static ClientesRepoJPA getInstance() {
        if (instance == null) {
            try {
                instance = new ClientesRepoJPA();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    @Override
    public List<Cliente> getAll() throws Exception {
        try {
            return em.createQuery("SELECT s FROM Cliente s", Cliente.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override       //Devuelve el cliente indicado por parámetro
    public Cliente getClientById(Integer id) throws Exception {
        try {
            if (em.find(Cliente.class, id) == null) {
                throw new ClienteException(NONEXISTINGCLIENT);
            } else {
                return em.find(Cliente.class, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override       //INSERT
    @Transactional
    public Cliente addClient(Cliente cliente) throws Exception {
        if (cliente.validar()) {
            try {
                em.persist(cliente);
                return cliente;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new ClienteException(INVALIDCLIENT);
        }
    }

    @Override
    @Transactional
    public boolean deleteClient(Cliente cliente) throws Exception {
        try {
            em.remove(cliente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public Cliente updateClient(Cliente cliente) throws Exception {
        if (cliente.validar()) {
            try {
                Cliente clActual = em.find(Cliente.class, cliente.getId());
                clActual.setNombre(cliente.getNombre());
                clActual.setEmail(cliente.getEmail());
                clActual.setDireccion(cliente.getDireccion());
                clActual.setActivo(cliente.isActivo());
                clActual.setMoroso(cliente.isMoroso());
                if (clActual instanceof Personal) {
                    ((Personal) clActual).setDni(((Personal) cliente).getDni());
                } else if (clActual instanceof Empresa) {
                    ((Empresa) clActual).setCif(((Empresa) cliente).getCif());
                    ((Empresa) clActual).setUnidadesNegocio(((Empresa) cliente).getUnidadesNegocio());
                }
                return clActual;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new ClienteException(INVALIDCLIENT);
        }
    }

    public void connectClientRepo() throws Exception {
        try {
            conn = DriverManager.getConnection(db_url);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public void commitClientRepo() throws Exception {
        try //(                Connection conn = DriverManager.getConnection(db_url);)
        {
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public void rollbackClientRepo() throws Exception {
        try //(                Connection conn = DriverManager.getConnection(db_url);)
        {
            conn.rollback();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
