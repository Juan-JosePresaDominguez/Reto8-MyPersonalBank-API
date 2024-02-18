package es.netmind.mypersonalbankapi.modelos.clientes;

import java.time.LocalDate;
import java.util.HashMap;

public class ClientesFactory {
    public static Cliente create(String type, HashMap params) throws Exception {
        switch (type) {
            case "personal": {
                String prop1 = (String) params.get("aProperty");
                Integer prop2 = (Integer) params.get("anotherProperty");

                Integer id = (Integer) params.get("id");
                String nombre = (String) params.get("nombre");
                String email = (String) params.get("email");
                String direccion = (String) params.get("direccion");
                LocalDate alta = LocalDate.parse((String) params.get("alta"));
                boolean activo = (Boolean) params.get("activo");
                boolean moroso = (Boolean) params.get("moroso");
                String dni = (String) params.get("dni");

                return new Personal(id, nombre, email, direccion, alta, activo, moroso, dni);
            }
            case "empresa": {
                Integer id = (Integer) params.get("id");
                String nombre = (String) params.get("nombre");
                String email = (String) params.get("email");
                String direccion = (String) params.get("direccion");
                LocalDate alta = LocalDate.parse((String) params.get("alta"));
                boolean activo = (Boolean) params.get("activo");
                boolean moroso = (Boolean) params.get("moroso");
                String cif = (String) params.get("cif");
                String[] unidadesNegocio = (String[]) params.get("unidadesNegocio");
                return new Empresa(id, nombre, email, direccion, alta, activo, moroso, cif, unidadesNegocio);
            }
            default:
                return null;
        }
    }
}
