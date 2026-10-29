package prueba3prograii;

public class EmpleadoPorHoras extends Empleado {

    private int horasTrabajadas = 0;
    private double tarifaPorHora = 0;

    public EmpleadoPorHoras(String codigo, String nombre, double salarioBase,
                            int horasTrabajadas, double tarifaPorHora) {
        super(codigo, nombre, salarioBase);
        setHorasTrabajadas(horasTrabajadas);
        setTarifaPorHora(tarifaPorHora);
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        if (horasTrabajadas < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        }
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        if (tarifaPorHora < 0) {
            throw new IllegalArgumentException("La tarifa por hora no puede ser negativa.");
        }
        this.tarifaPorHora = tarifaPorHora;
    }

  
    @Override
    public double calcularSalario() {
        return getSalarioBase() + (horasTrabajadas * tarifaPorHora);
    }

    @Override
    public String obtenerTipo() {
        return "Por horas";
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion()
                + "\nHoras trabajadas: " + horasTrabajadas
                + "\nTarifa por hora: " + formatear(tarifaPorHora);
    }
}
