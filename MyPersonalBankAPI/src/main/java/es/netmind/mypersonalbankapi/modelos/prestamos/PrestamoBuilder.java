package es.netmind.mypersonalbankapi.modelos.prestamos;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.exceptions.ErrorCode;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;

import java.time.LocalDate;

public class PrestamoBuilder {
    private Integer id;
    private LocalDate fechaConcesion;
    private Double monto;
    private Double saldo;
    private Integer anios;
    private Integer interes;
    private Integer interesMora;
    private boolean moroso;
    private boolean liquidado;

    public PrestamoBuilder() {
    }

    public PrestamoBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public PrestamoBuilder setFechaConcesion(LocalDate fechaConcesion) {
        this.fechaConcesion = fechaConcesion;
        return this;
    }

    public PrestamoBuilder setMonto(Double monto) {
        this.monto = monto;
        return this;
    }

    public PrestamoBuilder setSaldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    public PrestamoBuilder setAnios(Integer anios) {
        this.anios = anios;
        return this;
    }

    public PrestamoBuilder setInteres(Integer interes) {
        this.interes = interes;
        return this;
    }

    public PrestamoBuilder setInteresMora(Integer interesMora) {
        this.interesMora = interesMora;
        return this;
    }

    public PrestamoBuilder setMoroso(boolean moroso) {
        this.moroso = moroso;
        return this;
    }

    public PrestamoBuilder setLiquidado(boolean liquidado) {
        this.liquidado = liquidado;
        return this;
    }

    public Prestamo build() throws Exception {
        return new Prestamo(id, fechaConcesion, monto, saldo, interes, interesMora, moroso, liquidado, anios);
    }
}
