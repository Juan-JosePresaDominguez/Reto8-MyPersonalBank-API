package es.netmind.mypersonalbankapi.modelos.clientes;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Personal extends Cliente {
    private String dni;

    public Personal(Integer id, String nombre, String email, String direccion, LocalDate alta, boolean activo, boolean moroso, String dni) throws Exception{
        super(id, nombre, email, direccion, alta, activo, moroso);
        setDni(dni);
    }

    private boolean validarDNI(String dni) throws Exception{
        if (dni != null && dni.length() == 9) {
            String intPartDNI = dni.trim().replaceAll(" ", "").substring(0, 7);
            char ltrDNI = dni.charAt(8);
            int valNumDni = Integer.parseInt(intPartDNI);
            return valNumDni > 0 || !Character.isLetter(ltrDNI);
        } else return false;
    }

        @Override
    public boolean validar() throws Exception{
        return validarComun() && validarDNI(this.dni);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws Exception{
        if(validarDNI(dni)) this.dni = dni;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "dni='" + dni + '\'' +
                "} " + super.toString();
    }
}
