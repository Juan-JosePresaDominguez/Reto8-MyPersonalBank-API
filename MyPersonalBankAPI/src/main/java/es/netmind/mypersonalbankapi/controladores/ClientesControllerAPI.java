package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.persistencia.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**************************************************************************************************/
/* DOCUMENTACIÓN SWAGGER: http://localhost:9980/swagger-ui/index.html                             */
/* ~~~~~~~~~~~~~~~~~~~~~                                                                          */
/* HISTORIA DE USUARIO 1                                                                          */
/* Como usuario del sistema, quiero poder ver nuestra lista de clientes para tener una visión     */
/* general de los mismos.                                                                         */
/*                                                                                                */
/* HISTORIA DE USUARIO 2                                                                          */
/* Como usuario del sistema, quiero poder ver el detalle de un cliente para entender su perfil.   */
/*                                                                                                */
/* HISTORIA DE USUARIO 3                                                                          */
/* Como usuario del sistema, quiero poder registrar nuevos clientes para poder incrementar        */
/* nuestra base de datos.                                                                         */
/*                                                                                                */
/* HISTORIA DE USUARIO 4                                                                          */
/* Como usuario del sistema, quiero poder modificar los datos de un cliente para mantenerlos      */
/* actualizados.                                                                                  */
/* Borrar cliente por Id                                                                          */
/**************************************************************************************************/
@RestController
@RequestMapping("/clientes")
@Validated
@Tag(name = "MyPersonalBank API", description = "MyPersonalBankAPI - ClientesControllerAPI")
public class ClientesControllerAPI {

    private static final Logger logger = LoggerFactory.getLogger(ClientesControllerAPI.class);

    @Autowired
    private IClientesRepoData clientesRepo;         // RETO 8 - API REST
    private ICuentasRepo cuentasRepo = CuentasInMemoryRepo.getInstance();
    private IPrestamosRepo prestamosRepo = PrestamosInMemoryRepo.getInstance();

    @Autowired
    private IClientesController clientesController; // RETO 8 - API REST

    // Método GET (Obtener Clientes 'getClientes')
    @Operation(summary = "Get all clients", description = "Returns all clients from the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The client was not found")
    })
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Cliente>> getClientes() throws ClienteNotFoundException {
        List<Cliente> products = clientesRepo.findAll();
        if (products != null && products.size() > 0)
            return new ResponseEntity<>(clientesRepo.findAll(), HttpStatus.OK); // HTTP 200
            //else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            throw new ClienteNotFoundException("La lista de clientes está vacía"); // HTTP 404 + Excepción: La lista de clientes está vacía
    }

    // Método GET (Obtener Cliente por ID 'getCliente')
    @Operation(summary = "Get a client by id", description = "Returns a client as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not Found - The client was not found")
    })
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Cliente> getCliente(
            @Parameter(name = "id", description = "Cliente id", example = "1", required = true)
            @PathVariable @Min(1) Integer id
    ) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientesRepo.findClienteById(id));
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método POST (Crear Cliente 'save')       * NO FUNCIONA ?
    @Operation(summary = "Create a new client", description = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Cliente> save(@RequestBody @Valid Cliente newCliente) {
        logger.info("newCliente:" + newCliente);
        return new ResponseEntity<>(clientesRepo.save(newCliente), HttpStatus.CREATED);
    }

    // Método PUT (Actualizar Cuenta por ID y Cliente 'updateCliente'       * NO FUNCIONA ?
    @Operation(summary = "Update a client by id", description = "Update a client selected by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(
            @PathVariable @Min(1) Integer id,
            @RequestBody Cliente cliente
    ) throws ClienteNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientesController.updateCliente(id, cliente));
    }

    // Método DELETE (Borrar Cuenta por ID 'delete')
    @Operation(summary = "Delete a client by id", description = "Removes a client as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found - The client was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteId(
            @Parameter(name = "id", description = "Cliente id", example = "1", required = true)
            @PathVariable @Min(1) Integer id
    ) {
        //this.clientesRepo.deleteById(id); // HTTP 204 No Content
        this.clientesController.eliminar(id); // HTTP 204 No Content
        return ResponseEntity.noContent().build();
    }

    // Método DELETE (Borrar Todas las Cuentas 'deleteAll')
    @Operation(summary = "Delete all clients", description = "Removes all clients from the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content")
    })
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        clientesRepo.deleteAll(); // HTTP 204 No Content
        return ResponseEntity.noContent().build();
    }

}
