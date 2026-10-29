package prueba3prograii;

public class EmpleadoAdministrativo extends Empleado {

    private double bonificacion = 0;
    private double descuento = 0;   

    public EmpleadoAdministrativo(String codigo, String nombre, double salarioBase,
                                  double bonificacion, double descuento) {
        super(codigo, nombre, salarioBase);
        setBonificacion(bonificacion);
        setDescuento(descuento);
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        if (bonificacion < 0) {
            throw new IllegalArgumentException("La bonificación no puede ser negativa.");
        }
        this.bonificacion = bonificacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento tiene que estar entre 0 y 100.");
        }
        this.descuento = descuento;
    }


    @Override
    public double calcularSalario() {
        return (getSalarioBase() + bonificacion) * (1 - descuento / 100);
    }

    @Override
    public String obtenerTipo() {
        return "Administrativo";
    }

    
    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion()
                + "\nBonificación: " + formatear(bonificacion)
                + "\nDescuento: " + formatear(descuento) + " %";
    }
}
