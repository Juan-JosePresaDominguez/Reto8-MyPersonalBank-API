package es.netmind.mypersonalbankapi.modelos.prestamos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(name = "Préstamo", description = "Modelo préstamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @Schema(name = "Préstamo ID", example = "1", required = false)
    private Integer id;

    @Column(name = "fecha_concesion")
    @Schema(name = "Fecha de concesión", example = "2024-02-26", required = true)
    private LocalDate fechaConcesion;

    @Schema(name = "Monto", type = "Double", required = true)
    private Double monto; /* Valor futuro: Suma del capital más el interés. */

    @Schema(name = "Saldo", type = "Double", required = true)
    private Double saldo;

    @Schema(name = "Mensualidad", type = "Double", required = true)
    private Double mensualidad;

    @Min(1)
    @Max(50)
    @Schema(name = "Años préstamo", type = "Integer", required = true)
    private Integer anios;

    @Transient
    @JsonIgnore
    @Schema(name = "Préstamo pagos", type = "List<Pago>", required = false)
    private List<Pago> pagos;

    @Transient
    @JsonIgnore
    @Schema(name = "Préstamo moras", type = "List<Mora>", required = false)
    private List<Mora> moras;

    @Min(0)
    @Schema(name = "Interés", type = "Integer", required = true)
    private Integer interes;

    @Column(name = "interes_mora")
    @Min(0)
    @Schema(name = "Interés mora", type = "Integer", required = true)
    private Integer interesMora;

    @Schema(name = "Préstamo moroso", example = "true", required = true)
    private boolean moroso;

    @Schema(name = "Préstamo liquidado", example = "false", required = true)
    private boolean liquidado;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude   //Evitar bucles infinitos
    private Cliente myCliente;

    /* CONSTRUCTOR */
    public Prestamo(Integer id, LocalDate fechaConcesion, Double monto, Double saldo, Integer interes, Integer interesMora, boolean moroso, boolean liquidado, Integer anios) {
        this.id = id;
        this.fechaConcesion = fechaConcesion;
        this.monto = monto;
        this.saldo = saldo;
        this.interes = interes;
        this.interesMora = interesMora;
        this.moroso = moroso;
        this.liquidado = liquidado;
        this.anios = anios;
    }

    /* LOGICA IMPORTANTE */
    public boolean validar() {
        if (this.fechaConcesion.isAfter(LocalDate.now())) return false;
        else if (this.monto <= 0) return false;
        else if (this.saldo < 0) return false;
        else if (this.interesMora < 0) return false;
        else if (anios < 1) return false;
        else return true;
    }

    private void actualizarSaldo(Double monto) {
        this.saldo += monto;
    }

    public void calcularMensualidad(Double mensualidad) {
        this.mensualidad = this.monto * interes / (anios * 12);
    }

    public void pagarMensualidad() {
        if (!this.liquidado) {
            this.saldo -= this.mensualidad;
            if (this.saldo <= 0) this.liquidado = true;
        }
    }

    public void aplicarMora() {
        if (!this.liquidado) {
            this.saldo += this.saldo * interesMora;
            this.moroso = true;
        }
    }

    /* GETTERS */
    public Integer getId() {
        return id;
    }

    public LocalDate getFechaConcesion() {
        return fechaConcesion;
    }

    public Double getMonto() {
        return monto;
    }

    public Double getSaldo() {
        return saldo;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public List<Mora> getMoras() {
        return moras;
    }

    public Integer getInteres() {
        return interes;
    }

    public Integer getInteresMora() {
        return interesMora;
    }

    public boolean isMoroso() {
        return moroso;
    }

    public boolean isLiquidado() {
        return liquidado;
    }

    public Double getMensualidad() {
        return mensualidad;
    }

    public Integer getAnios() {
        return anios;
    }

    /* SETTERS */
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFechaConcesion(LocalDate fechaConcesion) {
        this.fechaConcesion = fechaConcesion;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setInteres(Integer interes) {
        this.interes = interes;
    }

    public void setInteresMora(Integer interesMora) {
        this.interesMora = interesMora;
    }

    public void setAnios(Integer anios) {
        this.anios = anios;
    }

    /* TOSTRING */
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", fechaConcesion=" + fechaConcesion +
                ", monto=" + monto +
                ", saldo=" + saldo +
                ", pagos=" + pagos +
                ", moras=" + moras +
                ", interes=" + interes +
                ", interesMora=" + interesMora +
                ", moroso=" + moroso +
                ", liquidado=" + liquidado +
                '}';
    }
}
