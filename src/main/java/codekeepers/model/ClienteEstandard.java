package codekeepers.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESTANDAR")
public class ClienteEstandard extends Cliente {

    public ClienteEstandard() {}
    public ClienteEstandard(int id, String nombre, String nif, String domicilio, String email) {
        super(id, nombre, nif, domicilio, email);
    }

    @Override
    public String tipoCliente() {
        return "Estandar";
    }

    @Override
    public float cuotaAnual() {
        // Implementación específica para calcular la cuota anual de ClienteEstandard
        return 0.0f;
    }

    @Override
    public float descuentoEnvio() {
        // Implementación específica para calcular el descuento de gastos de envío para ClienteEstandard
        return 0.0f;
    }

    @Override
    public String toString() {
        return "Tipo de Cliente: " + tipoCliente() + "\n" +
                super.toString() +
                "\nCuota anual: " + cuotaAnual() + "€\n" +
                "Descuento de envio: " + descuentoEnvio() + "%\n"
                ;
    }
}
