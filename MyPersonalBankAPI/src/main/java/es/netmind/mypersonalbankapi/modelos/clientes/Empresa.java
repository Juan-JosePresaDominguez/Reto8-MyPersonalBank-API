package es.netmind.mypersonalbankapi.modelos.clientes;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Empresa extends Cliente {

    @Pattern(regexp = "[A-Z]{1}[0-9]{8}")   // X12345678
    private String cif;

    private String[] unidadesNegocio;

    public Empresa(Integer id, String nombre, String email, String direccion, LocalDate alta, boolean activo, boolean moroso, String cif, String[] unidadesNegocio) throws Exception{
        super(id, nombre, email, direccion, alta, activo, moroso);
        setCif(cif);
        this.unidadesNegocio = unidadesNegocio;
    }

    private boolean validarCIF(String cif) throws Exception{
        if (cif != null && cif.length() == 9) {
            String intPartCIF = cif.trim().replaceAll(" ", "").substring(1, 9);
            char ltrCIF = cif.charAt(0);
            int valNumCif = Integer.parseInt(intPartCIF);
            return valNumCif > 0 || !Character.isLetter(ltrCIF);
        } else return false;
    }

    @Override
    public boolean validar() throws Exception{
        return this.validarComun() && validarCIF(this.cif);
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) throws Exception{
        if(validarCIF(cif)) this.cif = cif;
    }

//    public String[] getUnidadesNegocio() {
//        return unidadesNegocio;
//    }

    public void setUnidadesNegocio(String[] unidadesNegocio) {
        this.unidadesNegocio = unidadesNegocio;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "cif='" + cif + '\'' +
                ", unidadesNegocio=" + Arrays.toString(unidadesNegocio) +
                "} " + super.toString();
    }
}
