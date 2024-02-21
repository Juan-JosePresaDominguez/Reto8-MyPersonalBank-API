package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClientesRepoData extends JpaRepository<Cliente, Integer> {

    public Cliente findClienteById(Integer id) throws Exception;

    //MÉTODOS EQUIVALENTES (no haría falta definirlos)
    public List<Cliente> findAll();

    @Query("SELECT c FROM Cliente c")
    public List<Cliente> getAll() throws Exception;

    // COMENTARIOS RICARDO:
    /*- El método findAll no hace falta declararlo en la interface que extiende JPSRepository, ya que ya existe en la propia interface.
    - El método findClienteById, se puede reemplazar por el findById, que ya existe. En general, como estamos en JPSRepository de Cliente, no hace falta añadir Cliente en el nombre de los métodos.
    - El método getAll está bien como experimento/prueba.*/
}
