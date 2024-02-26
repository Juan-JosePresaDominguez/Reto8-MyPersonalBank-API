package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.prestamos.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPrestamosRepoData extends JpaRepository<Prestamo, Integer> {

    public Prestamo findPrestamoById(Integer id) throws Exception;

    @Query("SELECT p FROM Prestamo p")
    public List<Prestamo> getAll() throws Exception;
}
