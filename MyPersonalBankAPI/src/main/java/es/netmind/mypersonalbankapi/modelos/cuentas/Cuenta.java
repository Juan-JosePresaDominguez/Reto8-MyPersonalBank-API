package es.netmind.mypersonalbankapi.modelos.cuentas;

import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public abstract class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate fechaCreacion;
    private Double saldo;
    @Transient
    private List<Transaccion> transacciones;
    private Double interes;
    private Double comision;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude   //Evitar bucles infinitos
    private Cliente myCliente;

    /* CONSTRUCTOR */
    public Cuenta(Integer id, LocalDate fechaCreacion, Double saldo, Double interes, Double comision) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        this.interes = interes;
        this.comision = comision;
    }

    /* LOGICA IMPORTANTE */
    public void aniadirTransaccion(Transaccion tr) {
        if (this.transacciones == null) this.transacciones = new ArrayList<>();
        if (tr.validar()) {
            this.transacciones.add(tr);
            actualizarSaldo(tr.getImporte());
        }
    }

    protected void actualizarSaldo(Double monto) {
        this.saldo += monto;
    }

    protected boolean validarComun(){
        if (this.fechaCreacion.isAfter(LocalDate.now())) return false;
        else if (this.interes < 0) return false;
        else if (this.comision < 0) return false;
        else return true;
    }
    public abstract boolean validar();

    /* GETTERS AND SETTERS */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }


    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", saldo=" + saldo +
                ", transacciones=" + transacciones +
                ", interes=" + interes +
                ", comision=" + comision +
                '}';
    }
}
