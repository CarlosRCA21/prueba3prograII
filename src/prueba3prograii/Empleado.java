package prueba3prograii;


public abstract class Empleado implements Cloneable {

    
    /* comparacion con el salario
       menos de 1000 es Básico
       desde 1000 y menos de 1800 es Estandar
       1800 o mas es Superior
    */
    
    public static final double LIMITE_BASICO = 1000;
    public static final double LIMITE_ESTANDAR = 1800;

    private String codigo = "";
    private String nombre = "";
    private double salarioBase = 0;

    public Empleado(String codigo, String nombre, double salarioBase) {
        
        setCodigo(codigo);
        setNombre(nombre);
        setSalarioBase(salarioBase);
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo.");
        }
        this.salarioBase = salarioBase;
    }


    
    public abstract double calcularSalario();

    
    public abstract String obtenerTipo();

    
    public String obtenerCategoria() {
        double salario = calcularSalario();

        if (salario < LIMITE_BASICO) {
            return "Básico";
        } else if (salario < LIMITE_ESTANDAR) {
            return "Estándar";
        } else {
            return "Superior";
        }
    }

    
    public String mostrarInformacion() {
        return "Código: " + codigo
                + "\nNombre: " + nombre
                + "\nTipo: " + obtenerTipo()
                + "\nSalario base: " + formatear(salarioBase)
                + "\nSalario calculado: " + formatear(calcularSalario())
                + "\nCategoría: " + obtenerCategoria();
    }

    public static String formatear(double monto) {
        return String.format("%.2f", monto);
    }

   
    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("No se pudo clonar el empleado.");
        }
    }
}
