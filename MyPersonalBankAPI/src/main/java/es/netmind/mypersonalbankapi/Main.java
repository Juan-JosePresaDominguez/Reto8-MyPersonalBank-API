package es.netmind.mypersonalbankapi;

import es.netmind.mypersonalbankapi.controladores.CuentasController;
import es.netmind.mypersonalbankapi.controladores.IClientesController;
import es.netmind.mypersonalbankapi.controladores.PrestamosController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Main {
    @Autowired
    private IClientesController clienteControl;

    public void main(String[] args) {
        System.out.println("╔════════════════════════╗");
        System.out.println("║  MY PERSONAL BANK API  ║");
        System.out.println("╚════════════════════════╝");

        if (args != null && args.length >= 2) {
            try {
                procesarArgumentos(args);
            } catch (NumberFormatException e) {
                System.out.println("⚠ LOS IDENTIFICADORES DE CLIENTE {uid}, CUENTA {aid} Y PRÉSTAMOS {lid} DEBEN SER NÚMEROS ⚠");
                mostrarInstrucciones();
            } catch (Exception e) {
                System.out.println("⚠ HA HABIDO UN ERROR... CONTACTE CON EL ADMINISTRADOR ⚠");
                e.printStackTrace();
            }

        } else mostrarInstrucciones();

    }

    private void procesarArgumentos(String[] args) throws Exception {
        int argsLength = args.length;
        String arg0 = args[0].toLowerCase();
        String arg1 = args[1].toLowerCase();
        String arg2 = argsLength > 2 ? args[2].toLowerCase() : null;

        if (!arg0.equals("clients")) {
            mostrarInstrucciones();
            return;
        } else if (arg2 != null && arg2.equals("accounts")) {
            procesarArgumentosCuentas(args);
        } else if (arg2 != null && arg2.equals("loans")) {
            procesarArgumentosPrestamos(args);
        } else if (arg2 != null && arg2.equals("loan-evaluation")) {
            procesarArgumentosEvaluacionPrestamo(args);
        } else {
            procesarArgumentosClientes(args);
        }

    }

    private void procesarArgumentosClientes(String[] args) throws Exception {
        int argsLength = args.length;
        String arg1 = args[1].toLowerCase();

        if (arg1.equals("list")) clienteControl.mostrarLista();
        else if (arg1.equals("add")) clienteControl.add(Arrays.copyOfRange(args, 2, argsLength));
        else if (arg1.equals("remove")) clienteControl.eliminar(Integer.valueOf(args[2]));
        else if (arg1.equals("update"))
            clienteControl.actualizar(Integer.valueOf(args[2]), Arrays.copyOfRange(args, 3, argsLength));
        else if (argsLength == 2) clienteControl.mostrarDetalle(Integer.valueOf(arg1));
    }

    private static void procesarArgumentosCuentas(String[] args) {
        int argsLength = args.length;
        int uid = Integer.valueOf(args[1]);
        if (argsLength == 3) CuentasController.mostrarLista(uid);
        else if (argsLength == 4) CuentasController.mostrarDetalle(uid, Integer.valueOf(args[3]));
        else if (args[3].toLowerCase().equals("add"))
            CuentasController.add(uid, Arrays.copyOfRange(args, 4, argsLength));
        else if (args[3].toLowerCase().equals("update"))
            CuentasController.actualizar(uid, Integer.valueOf(args[4]), Arrays.copyOfRange(args, 5, argsLength));
        else if (args[3].toLowerCase().equals("remove"))
            CuentasController.eliminar(uid, Integer.valueOf(args[4]));
        else mostrarInstrucciones();
    }

    private static void procesarArgumentosPrestamos(String[] args) {
        int argsLength = args.length;
        int uid = Integer.valueOf(args[1]);
        if (argsLength == 3) PrestamosController.mostrarLista(uid);
        else if (argsLength == 4) PrestamosController.mostrarDetalle(uid, Integer.valueOf(args[3]));
        else if (args[3].toLowerCase().equals("add"))
            PrestamosController.add(uid, Arrays.copyOfRange(args, 4, argsLength));
        else if (args[3].toLowerCase().equals("update"))
            PrestamosController.actualizar(uid, Integer.valueOf(args[4]), Arrays.copyOfRange(args, 5, argsLength));
        else if (args[3].toLowerCase().equals("remove"))
            PrestamosController.eliminar(uid, Integer.valueOf(args[4]));
        else mostrarInstrucciones();
    }

    private void procesarArgumentosEvaluacionPrestamo(String[] args) {
        int argsLength = args.length;
        int uid = Integer.valueOf(args[1]);
        Double cantidad = argsLength >= 4 ? Double.valueOf(args[3]) : 0;
        if (cantidad > 0) {
            clienteControl.evaluarPrestamo(uid, cantidad);
        } else mostrarInstrucciones();
    }

    private static void mostrarInstrucciones() {
        System.out.println("⚠ LAS OPCIONES DEL PROGRAMA SON LAS SIGUIENTES: ⚠");

        System.out.println("\nCLIENTES:");
        System.out.println("clients list");
        System.out.println("clients {uid}");
        System.out.println("clients add {personal/empresa} {nombre} {email} {direccion} {fechaAlta (yyyy-mm-dd)} {dni/nif} {unidadesNegocio (opcional)}");
        System.out.println("clients remove {uid}");
        System.out.println("clients update {uid} {nombre} {email} {direccion} {fechaAlta (yyyy-mm-dd)} {activo} {moroso} {dni/nif} {unidadesNegocio (opcional)}");

        System.out.println("\nCUENTAS:");
        System.out.println("clients {uid} accounts");
        System.out.println("clients {uid} accounts {aid}");
        System.out.println("clients {uid} accounts add {ahorro/corriente} {fechaCreacion} {saldo} {interes} {comision}");
        System.out.println("clients {uid} accounts remove {aid}");
        System.out.println("clients {uid} accounts update {aid} {fechaCreacion} {saldo} {interes} {comision}");

        System.out.println("\nPRÉSTAMOS:");
        System.out.println("clients {uid} loans");
        System.out.println("clients {uid} loans {lid}");
        System.out.println("clients {uid} loans add {fechaConcesion (yyyy-mm-dd)} {monto} {interes} {interesMora}");
        System.out.println("clients {uid} loans remove {lid}");
        System.out.println("clients {uid} loans update {lid} {fechaConcesion (yyyy-mm-dd)} {monto} {interes} {interesMora}");

        System.out.println("\nEVALUAR PRÉSTAMO:");
        System.out.println("clients {uid} loan-evaluation {cantidad > 0}");
    }
}