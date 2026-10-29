package prueba3prograii;

import java.util.ArrayList;


public class GestorEmpleados {

    
    private ArrayList<Empleado> empleados = new ArrayList<>();

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    
    public Empleado buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < empleados.size(); i++) {
            Empleado actual = empleados.get(i);
            if (actual.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return actual;
            }
        }
        return null;
    }

    
    public void registrar(Empleado empleado) {
        if (buscarPorCodigo(empleado.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado con el código " + empleado.getCodigo() + ".");
        }
        empleados.add(empleado);
    }

    
    public Empleado empleadoMayorSalario() {
        Empleado mayor = null;

        for (int i = 0; i < empleados.size(); i++) {
            Empleado actual = empleados.get(i);
            if (mayor == null || actual.calcularSalario() > mayor.calcularSalario()) {
                mayor = actual;
            }
        }
        return mayor;
    }

    
    public double salarioPromedio() {
        if (empleados.size() == 0) {
            return 0;
        }

        double suma = 0;
        for (int i = 0; i < empleados.size(); i++) {
            suma = suma + empleados.get(i).calcularSalario();
        }
        return suma / empleados.size();
    }

    
    public Empleado clonar(Empleado original) {
        Empleado copia = original.clone();
        copia.setNombre("Clon de " + original.getNombre());
        return copia;
    }

    
    public void registrarClon(Empleado copia, String nuevoCodigo) {
        copia.setCodigo(nuevoCodigo);
        registrar(copia);
    }

    
    public String compararEmpleados(Empleado a, Empleado b) {
        boolean mismaReferencia = (a == b);
        boolean mismoTipo = a.obtenerTipo().equals(b.obtenerTipo());
        boolean mismoBase = (a.getSalarioBase() == b.getSalarioBase());
        boolean mismoSalario = (a.calcularSalario() == b.calcularSalario());
        boolean mismaCategoria = a.obtenerCategoria().equals(b.obtenerCategoria());

        String resultado = "Empleado A: " + a.getCodigo() + " - " + a.getNombre()
                + "\nEmpleado B: " + b.getCodigo() + " - " + b.getNombre()
                + "\n"
                + "\n¿Son el mismo objeto (A == B)? " + siNo(mismaReferencia)
                + "\n¿Mismo tipo? " + siNo(mismoTipo)
                + "\n¿Mismo salario base? " + siNo(mismoBase)
                + "\n¿Mismo salario calculado? " + siNo(mismoSalario)
                + "\n¿Misma categoría? " + siNo(mismaCategoria)
                + "\n";

        if (mismoTipo && mismoBase && mismoSalario && mismaCategoria) {
            resultado = resultado + "\nLos datos son equivalentes.";
        } else {
            resultado = resultado + "\nLos datos son distintos.";
        }

        if (!mismaReferencia) {
            resultado = resultado + "\nSon dos objetos diferentes en memoria.";
        }
        return resultado;
    }

    private String siNo(boolean valor) {
        if (valor) {
            return "Sí";
        }
        return "No";
    }
}
