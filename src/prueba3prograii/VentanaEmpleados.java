package prueba3prograii;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class VentanaEmpleados extends JFrame implements ActionListener {

  
    private GestorEmpleados gestor = new GestorEmpleados();


    private Empleado copiaPendiente = null;

 
    private JTextField campoCodigo = new JTextField(15);
    private JTextField campoNombre = new JTextField(15);
    private JComboBox<String> cajaTipo = new JComboBox<>(new String[] {"Administrativo", "Por horas"});
    private JTextField campoSalarioBase = new JTextField(15);
    private JLabel etiquetaDato1 = new JLabel("Bonificación:");
    private JTextField campoDato1 = new JTextField(15);
    private JLabel etiquetaDato2 = new JLabel("Descuento (0-100 %):");
    private JTextField campoDato2 = new JTextField(15);

    
    private JButton botonRegistrar = crearBoton("Registrar");
    private JButton botonCalcular = crearBoton("Calcular salario");
    private JButton botonBuscar = crearBoton("Buscar");
    private JButton botonClonar = crearBoton("Clonar empleado");
    private JButton botonRegistrarClon = crearBoton("Registrar clon");
    private JButton botonComparar = crearBoton("Comparar objetos");
    private JButton botonMaximo = crearBoton("Salario máximo");
    private JButton botonPromedio = crearBoton("Promedio");
    private JButton botonLimpiar = crearBoton("Limpiar");

    
    private DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[] {"Código", "Nombre", "Tipo", "Salario calculado", "Categoría"}, 0);
    private JTable tabla = new JTable(modeloTabla);

    public VentanaEmpleados() {
        super("Sistema de Gestión de Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        cajaTipo.addActionListener(this);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(crearPanelFormulario(), BorderLayout.NORTH);
        panelSuperior.add(crearPanelBotones(), BorderLayout.CENTER);

        JScrollPane panelTabla = new JScrollPane(tabla);
        panelTabla.setBorder(BorderFactory.createTitledBorder("Listado de empleados"));

        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);

        actualizarTabla();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.addActionListener(this);
        return boton;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));

        panel.add(new JLabel("Código:"));
        panel.add(campoCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Tipo:"));
        panel.add(cajaTipo);
        panel.add(new JLabel("Salario base:"));
        panel.add(campoSalarioBase);
        panel.add(etiquetaDato1);
        panel.add(campoDato1);
        panel.add(etiquetaDato2);
        panel.add(campoDato2);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(3, 3, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        panel.add(botonRegistrar);
        panel.add(botonCalcular);
        panel.add(botonBuscar);
        panel.add(botonClonar);
        panel.add(botonRegistrarClon);
        panel.add(botonComparar);
        panel.add(botonMaximo);
        panel.add(botonPromedio);
        panel.add(botonLimpiar);
        return panel;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        try {
            if (origen == cajaTipo) {
                cambiarEtiquetas();
            } else if (origen == botonRegistrar) {
                registrarEmpleado();
            } else if (origen == botonCalcular) {
                calcularSalario();
            } else if (origen == botonBuscar) {
                buscarEmpleado();
            } else if (origen == botonClonar) {
                clonarEmpleado();
            } else if (origen == botonRegistrarClon) {
                registrarClon();
            } else if (origen == botonComparar) {
                compararObjetos();
            } else if (origen == botonMaximo) {
                mostrarSalarioMaximo();
            } else if (origen == botonPromedio) {
                mostrarPromedio();
            } else if (origen == botonLimpiar) {
                limpiarFormulario();
            }
        } catch (Exception ex) {
            mostrarError("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }



    private void registrarEmpleado() {
        String codigo = campoCodigo.getText().trim();
        String nombre = campoNombre.getText().trim();
        String textoBase = campoSalarioBase.getText().trim();
        String texto1 = campoDato1.getText().trim();
        String texto2 = campoDato2.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || textoBase.isEmpty() || texto1.isEmpty() || texto2.isEmpty()) {
            mostrarError("Llene todos los campos. No se aceptan campos vacíos.");
            return;
        }

        String tipo = (String) cajaTipo.getSelectedItem();
        Empleado nuevo = null;

        try {
            double salarioBase = Double.parseDouble(textoBase);

            if (tipo.equals("Administrativo")) {
                double bonificacion = Double.parseDouble(texto1);
                double descuento = Double.parseDouble(texto2);
                nuevo = new EmpleadoAdministrativo(codigo, nombre, salarioBase, bonificacion, descuento);
            } else {
                int horas = Integer.parseInt(texto1);
                double tarifa = Double.parseDouble(texto2);
                nuevo = new EmpleadoPorHoras(codigo, nombre, salarioBase, horas, tarifa);
            }

            gestor.registrar(nuevo);
        } catch (NumberFormatException ex) {
            mostrarError("Revise los campos numéricos: solo números, con punto para los decimales y enteros para las horas.");
            return;
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
            return;
        }

        actualizarTabla();
        limpiarFormulario();
        mostrarMensaje("El empleado " + nuevo.getCodigo() + " se registró correctamente.");
    }

    private void calcularSalario() {
        Empleado seleccionado = obtenerSeleccionado();

        if (seleccionado == null) {
            mostrarError("Seleccione un empleado de la tabla para calcular su salario.");
            return;
        }

        mostrarMensaje("Salario calculado de " + seleccionado.getNombre() + ": "
                + Empleado.formatear(seleccionado.calcularSalario())
                + "\nCategoría: " + seleccionado.obtenerCategoria());
    }

    private void buscarEmpleado() {
        String codigo = JOptionPane.showInputDialog(this, "Escriba el código del empleado:");

        if (codigo == null) {
            return;   
        }

        codigo = codigo.trim();
        if (codigo.isEmpty()) {
            mostrarError("Escriba un código para buscar.");
            return;
        }

        Empleado encontrado = gestor.buscarPorCodigo(codigo);

        if (encontrado == null) {
            mostrarError("No existe ningún empleado con el código " + codigo + ".");
        } else {
            int fila = gestor.getEmpleados().indexOf(encontrado);
            tabla.setRowSelectionInterval(fila, fila);
            mostrarMensaje(encontrado.mostrarInformacion());
        }
    }

    private void clonarEmpleado() {
        Empleado seleccionado = obtenerSeleccionado();

        if (seleccionado == null) {
            mostrarError("Seleccione un empleado de la tabla antes de clonar.");
            return;
        }

        copiaPendiente = gestor.clonar(seleccionado);
        mostrarMensaje("Se creó una copia (todavía NO está registrada):\n\n"
                + copiaPendiente.mostrarInformacion()
                + "\n\nPresione \"Registrar clon\" para guardarla con un código nuevo.");
    }

    private void registrarClon() {
        if (copiaPendiente == null) {
            mostrarError("Primero seleccione un empleado y presione \"Clonar empleado\".");
            return;
        }

        String nuevoCodigo = JOptionPane.showInputDialog(this, "Escriba el código nuevo para la copia:");

        if (nuevoCodigo == null) {
            return;   
        }

        try {
            gestor.registrarClon(copiaPendiente, nuevoCodigo);
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
            return;
        }

        String codigoRegistrado = copiaPendiente.getCodigo();
        copiaPendiente = null;
        actualizarTabla();
        mostrarMensaje("La copia se registró con el código " + codigoRegistrado + ".");
    }

    private void compararObjetos() {
        int[] filas = tabla.getSelectedRows();

        if (filas.length != 2) {
            mostrarError("Seleccione dos empleados de la tabla (con Ctrl para elegir el segundo).");
            return;
        }

        Empleado a = gestor.getEmpleados().get(filas[0]);
        Empleado b = gestor.getEmpleados().get(filas[1]);
        mostrarMensaje(gestor.compararEmpleados(a, b));
    }

    private void mostrarSalarioMaximo() {
        Empleado mayor = gestor.empleadoMayorSalario();

        if (mayor == null) {
            mostrarError("No hay empleados registrados.");
        } else {
            mostrarMensaje("Empleado con el mayor salario:\n\n" + mayor.mostrarInformacion());
        }
    }

    private void mostrarPromedio() {
        if (gestor.getEmpleados().size() == 0) {
            mostrarError("No hay empleados registrados.");
        } else {
            mostrarMensaje("Salario promedio de " + gestor.getEmpleados().size() + " empleado(s): "
                    + Empleado.formatear(gestor.salarioPromedio()));
        }
    }

    // Vacía el formulario (no borra los empleados guardados)
    private void limpiarFormulario() {
        campoCodigo.setText("");
        campoNombre.setText("");
        campoSalarioBase.setText("");
        campoDato1.setText("");
        campoDato2.setText("");
        cajaTipo.setSelectedIndex(0);
        cambiarEtiquetas();
    }

    private void cambiarEtiquetas() {
        String tipo = (String) cajaTipo.getSelectedItem();

        if (tipo.equals("Administrativo")) {
            etiquetaDato1.setText("Bonificación:");
            etiquetaDato2.setText("Descuento (0-100 %):");
        } else {
            etiquetaDato1.setText("Horas trabajadas:");
            etiquetaDato2.setText("Tarifa por hora:");
        }
    }

   
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (int i = 0; i < gestor.getEmpleados().size(); i++) {
            Empleado empleado = gestor.getEmpleados().get(i);
            String[] fila = {
                empleado.getCodigo(),
                empleado.getNombre(),
                empleado.obtenerTipo(),
                Empleado.formatear(empleado.calcularSalario()),
                empleado.obtenerCategoria()
            };
            modeloTabla.addRow(fila);
        }
    }

   
    private Empleado obtenerSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            return null;
        }
        return gestor.getEmpleados().get(fila);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
