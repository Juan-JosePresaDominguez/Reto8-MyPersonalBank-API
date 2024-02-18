package es.netmind.mypersonalbankapi.modelos.cuentas;

import java.time.LocalDate;

public class Cheque {
    private Integer id;
    private Double importe;
    private LocalDate fecha;
    private String beneficiario;

    public Cheque(Integer id, Double importe, LocalDate fecha, String beneficiario) {
        this.id = id;
        this.importe = importe;
        this.fecha = fecha;
        this.beneficiario = beneficiario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "id=" + id +
                ", importe=" + importe +
                ", fecha=" + fecha +
                ", beneficiario='" + beneficiario + '\'' +
                '}';
    }
}
