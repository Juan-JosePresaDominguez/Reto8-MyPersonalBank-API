package es.netmind.mypersonalbankapi.utils;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.ClientesFactory;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

import java.time.LocalDate;
import java.util.HashMap;

public class ClientesUtils {
    public static Cliente extractClientFromArgsForCreate(String[] args) throws Exception {
        int argsLength = args.length;

        Cliente cl = null;
        String tipoCliente = args[0].toLowerCase();
        HashMap<String, Object> params = new HashMap<>();
        if (tipoCliente.equals("personal")) {
            // cl = new Personal(null, args[1], args[2], args[3], LocalDate.parse(args[4]), true, false, args[5]);
            params.put("id", null);
            params.put("nombre", args[1]);
            params.put("email", args[2]);
            params.put("direccion", args[3]);
            params.put("alta", args[4]);
            params.put("activo", true);
            params.put("moroso", false);
            params.put("dni", args[5]);
            cl = ClientesFactory.create("personal", params);
        } else if (tipoCliente.equals("empresa")) {
            // cl = new Empresa(null, args[1], args[2], args[3], LocalDate.parse(args[4]), true, false, args[5], argsLength > 8 ? args[8].split(",") : null);
            params.put("id", null);
            params.put("nombre", args[1]);
            params.put("email", args[2]);
            params.put("direccion", args[3]);
            params.put("alta", args[4]);
            params.put("activo", true);
            params.put("moroso", false);
            params.put("cif", args[5]);
            params.put("unidadesNegocio", argsLength > 8 ? args[8].split(",") : null);
            cl = ClientesFactory.create("empresa", params);
        }
        if (cl.validar()) return cl;
        else throw new Exception();
        //return cl;
    }

    public static Cliente updateClientFromArgs(Cliente clienteBD, String[] args) throws Exception {
        // {nombre} {email} {direccion} {fechaAlta (yyyy-mm-dd)} {activo} {moroso} {dni/nif} {unidadesNegocio (opcional)}

        int argsLength = args.length;

        System.out.println("updateClientFromArgs");
        String className = clienteBD.getClass().getName();

        System.out.println("argsLength: " + argsLength);
        System.out.println("className: " + className);

        System.out.println("Traza 1");
        if (className.contains("modelos.clientes.Personal")) {
            System.out.println("Traza 3");
            Personal per = (Personal) clienteBD;
            Personal cliente = new Personal(per.getId(), per.getNombre(), per.getEmail(), per.getDireccion(), per.getAlta(), per.isActivo(), per.isMoroso(), per.getDni());
            if (args[0] != null) cliente.setNombre(args[0]);
            if (args[1] != null) cliente.setEmail(args[1]);
            if (args[2] != null) cliente.setDireccion(args[2]);
            if (args[3] != null) cliente.setAlta(LocalDate.parse(args[3]));
            if (args[4] != null) cliente.setActivo(Boolean.valueOf(args[4]));
            if (args[5] != null) cliente.setMoroso(Boolean.valueOf(args[5]));
            if (args[6] != null) cliente.setDni(args[6]);
            if (cliente.validar()) {
                if (args[0] != null) per.setNombre(args[0]);
                if (args[1] != null) per.setEmail(args[1]);
                if (args[2] != null) per.setDireccion(args[2]);
                if (args[3] != null) per.setAlta(LocalDate.parse(args[3]));
                if (args[4] != null) per.setActivo(Boolean.valueOf(args[4]));
                if (args[5] != null) per.setMoroso(Boolean.valueOf(args[5]));
                if (args[6] != null) per.setDni(args[6]);
                return per;
            }
            else throw new Exception();
            //return per;
        } else {
            System.out.println("Traza 4");
            Empresa emp = (Empresa) clienteBD;
            Empresa cliente = new Empresa(emp.getId(), emp.getNombre(), emp.getEmail(), emp.getDireccion(), emp.getAlta(), emp.isActivo(), emp.isMoroso(), emp.getCif(), emp.getUnidadesNegocio());
            if (args[0] != null) cliente.setNombre(args[0]);
            if (args[1] != null) cliente.setEmail(args[1]);
            if (args[2] != null) cliente.setDireccion(args[2]);
            if (args[3] != null) cliente.setAlta(LocalDate.parse(args[3]));
            if (args[4] != null) cliente.setActivo(Boolean.valueOf(args[4]));
            if (args[5] != null) cliente.setMoroso(Boolean.valueOf(args[5]));
            if (args[6] != null) cliente.setCif(args[6]);
            if (argsLength > 7) cliente.setUnidadesNegocio(args[7].split(","));
            if (cliente.validar()) {
                if (args[0] != null) emp.setNombre(args[0]);
                if (args[1] != null) emp.setEmail(args[1]);
                if (args[2] != null) emp.setDireccion(args[2]);
                if (args[3] != null) emp.setAlta(LocalDate.parse(args[3]));
                if (args[4] != null) emp.setActivo(Boolean.valueOf(args[4]));
                if (args[5] != null) emp.setMoroso(Boolean.valueOf(args[5]));
                if (args[6] != null) emp.setCif(args[6]);
                if (argsLength > 7) emp.setUnidadesNegocio(args[7].split(","));
                return emp;
            }
            else throw new Exception();
            //return emp;
        }


    }
}
