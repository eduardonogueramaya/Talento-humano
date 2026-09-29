import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

    public class VentanaTalentoHumano extends JFrame {

        private JTextField txtCedula;
        private JTextField txtNombre;
        private JTextField txtSalario;
        private JComboBox<String> comboTipo;
        private JTextField txtBonificacion;

        private JTable tabla;
        private DefaultTableModel modelo;

        private ArrayList<EMPLEADO> empleados = new ArrayList<>();

        private JLabel lblEmpleados;
        private JLabel lblTotalNomina;

        public VentanaTalentoHumano() {

            setTitle("Sistema de Talento Humano");
            setSize(750, 520);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            crearInterfaz();

            agregarEmpleadoInicial(
                    "1004",
                    "Pedro Cano",
                    3200000,
                    "Administrativo",
                    300000
            );

            agregarEmpleadoInicial(
                    "1003",
                    "Marta Rios",
                    1750000,
                    "Operativo",
                    0
            );

            agregarEmpleadoInicial(
                    "1002",
                    "Luis Gomez",
                    2500000,
                    "Administrativo",
                    300000
            );

            agregarEmpleadoInicial(
                    "1001",
                    "Ana Torres",
                    1800000,
                    "Operativo",
                    0
            );

            actualizarTabla();
        }

        private void crearInterfaz() {

            setLayout(new BorderLayout(10, 10));

            JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));

            formulario.add(new JLabel("Cédula:"));
            txtCedula = new JTextField();
            formulario.add(txtCedula);

            formulario.add(new JLabel("Nombre completo:"));
            txtNombre = new JTextField();
            formulario.add(txtNombre);

            formulario.add(new JLabel("Salario base:"));
            txtSalario = new JTextField();
            formulario.add(txtSalario);

            formulario.add(new JLabel("Tipo de empleado:"));

            comboTipo = new JComboBox<>();
            comboTipo.addItem("Operativo");
            comboTipo.addItem("Administrativo");

            formulario.add(comboTipo);

            formulario.add(new JLabel("Bonificación (solo administrativos):"));
            txtBonificacion = new JTextField();
            formulario.add(txtBonificacion);

            add(formulario, BorderLayout.NORTH);

            JPanel panelBotones = new JPanel();

            JButton btnAgregar = new JButton("Agregar");
            JButton btnBuscar = new JButton("Buscar");
            JButton btnActualizar = new JButton("Actualizar");
            JButton btnEliminar = new JButton("Eliminar");
            JButton btnLimpiar = new JButton("Limpiar");
            JButton btnHistorial = new JButton("Historial");

            panelBotones.add(btnAgregar);
            panelBotones.add(btnBuscar);
            panelBotones.add(btnActualizar);
            panelBotones.add(btnEliminar);
            panelBotones.add(btnLimpiar);
            panelBotones.add(btnHistorial);

            add(panelBotones, BorderLayout.CENTER);

            String[] columnas = {
                    "Cédula",
                    "Nombre",
                    "Tipo",
                    "Salario base",
                    "Salario total"
            };

            modelo = new DefaultTableModel(columnas, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tabla = new JTable(modelo);

            tabla.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION
            );

            JScrollPane scroll = new JScrollPane(tabla);

            JPanel panelTabla = new JPanel(new BorderLayout());

            panelTabla.add(
                    new JLabel("Empleados registrados"),
                    BorderLayout.NORTH
            );

            panelTabla.add(scroll, BorderLayout.CENTER);

            add(panelTabla, BorderLayout.SOUTH);

            JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));

            lblEmpleados = new JLabel("Empleados: 0");
            lblTotalNomina = new JLabel("Total nómina: $0");

            panelInferior.add(lblEmpleados);
            panelInferior.add(new JLabel("     |     "));
            panelInferior.add(lblTotalNomina);

            add(panelInferior, BorderLayout.SOUTH);


            btnAgregar.addActionListener(e -> agregarEmpleado());

            btnBuscar.addActionListener(e -> buscarEmpleado());

            btnActualizar.addActionListener(e -> actualizarEmpleado());

            btnEliminar.addActionListener(e -> eliminarEmpleado());

            btnLimpiar.addActionListener(e -> limpiarCampos());

            btnHistorial.addActionListener(e ->
                    JOptionPane.showMessageDialog(
                            this,
                            "Historial de empleados disponible."
                    )
            );

            tabla.getSelectionModel().addListSelectionListener(e -> {

                if (!e.getValueIsAdjusting()) {

                    int fila = tabla.getSelectedRow();

                    if (fila >= 0) {
                        cargarEmpleado(fila);
                    }
                }
            });
        }

        private void agregarEmpleado() {

            try {

                String cedula = txtCedula.getText();
                String nombre = txtNombre.getText();

                double salario =
                        Double.parseDouble(txtSalario.getText());

                String tipo =
                        comboTipo.getSelectedItem().toString();

                double bonificacion = 0;

                if (tipo.equals("Administrativo")) {

                    if (!txtBonificacion.getText().isEmpty()) {

                        bonificacion =
                                Double.parseDouble(
                                        txtBonificacion.getText()
                                );
                    }
                }

                if (cedula.isEmpty() || nombre.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Completa todos los campos."
                    );

                    return;
                }

                EMPLEADO empleado = new EMPLEADO(
                        cedula,
                        nombre,
                        salario,
                        tipo,
                        bonificacion
                );

                empleados.add(empleado);

                actualizarTabla();
                limpiarCampos();

                JOptionPane.showMessageDialog(
                        this,
                        "Empleado agregado correctamente."
                );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "El salario debe ser un número."
                );
            }
        }

        private void buscarEmpleado() {

            String cedula = txtCedula.getText();

            for (EMPLEADO empleado : empleados) {

                if (empleado.getCedula().equals(cedula)) {

                    txtNombre.setText(empleado.getNombre());

                    txtSalario.setText(
                            String.valueOf(
                                    empleado.getSalarioBase()
                            )
                    );

                    comboTipo.setSelectedItem(
                            empleado.getTipo()
                    );

                    txtBonificacion.setText(
                            String.valueOf(
                                    empleado.getBonificacion()
                            )
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Empleado encontrado."
                    );

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado no encontrado."
            );
        }

        private void actualizarEmpleado() {

            String cedula = txtCedula.getText();

            for (EMPLEADO empleado : empleados) {

                if (empleado.getCedula().equals(cedula)) {

                    try {

                        empleado.setNombre(
                                txtNombre.getText()
                        );

                        empleado.setSalarioBase(
                                Double.parseDouble(
                                        txtSalario.getText()
                                )
                        );

                        empleado.setTipo(
                                comboTipo.getSelectedItem().toString()
                        );

                        double bonificacion = 0;

                        if (empleado.getTipo()
                                .equals("Administrativo")) {

                            if (!txtBonificacion.getText().isEmpty()) {

                                bonificacion =
                                        Double.parseDouble(
                                                txtBonificacion.getText()
                                        );
                            }
                        }

                        empleado.setBonificacion(bonificacion);

                        actualizarTabla();
                        limpiarCampos();

                        JOptionPane.showMessageDialog(
                                this,
                                "Empleado actualizado."
                        );

                        return;

                    } catch (NumberFormatException e) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Verifica el salario."
                        );

                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado no encontrado."
            );
        }

        private void eliminarEmpleado() {

            String cedula = txtCedula.getText();

            for (EMPLEADO empleado : empleados) {

                if (empleado.getCedula().equals(cedula)) {

                    int opcion = JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas eliminar este empleado?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (opcion == JOptionPane.YES_OPTION) {

                        empleados.remove(empleado);

                        actualizarTabla();
                        limpiarCampos();

                        JOptionPane.showMessageDialog(
                                this,
                                "Empleado eliminado."
                        );
                    }

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Empleado no encontrado."
            );
        }

        private void actualizarTabla() {

            modelo.setRowCount(0);

            double totalNomina = 0;

            for (EMPLEADO empleado : empleados) {

                modelo.addRow(new Object[]{
                        empleado.getCedula(),
                        empleado.getNombre(),
                        empleado.getTipo(),
                        formatoDinero(empleado.getSalarioBase()),
                        formatoDinero(empleado.getSalarioTotal())
                });

                totalNomina += empleado.getSalarioTotal();
            }

            lblEmpleados.setText(
                    "Empleados: " + empleados.size()
            );

            lblTotalNomina.setText(
                    "Total nómina: " + formatoDinero(totalNomina)
            );
        }

        private void cargarEmpleado(int fila) {

            String cedula =
                    modelo.getValueAt(fila, 0).toString();

            for (EMPLEADO empleado : empleados) {

                if (empleado.getCedula().equals(cedula)) {

                    txtCedula.setText(
                            empleado.getCedula()
                    );

                    txtNombre.setText(
                            empleado.getNombre()
                    );

                    txtSalario.setText(
                            String.valueOf(
                                    empleado.getSalarioBase()
                            )
                    );

                    comboTipo.setSelectedItem(
                            empleado.getTipo()
                    );

                    txtBonificacion.setText(
                            String.valueOf(
                                    empleado.getBonificacion()
                            )
                    );

                    return;
                }
            }
        }

        private void limpiarCampos() {

            txtCedula.setText("");
            txtNombre.setText("");
            txtSalario.setText("");
            txtBonificacion.setText("");

            comboTipo.setSelectedIndex(0);

            tabla.clearSelection();
        }

        private String formatoDinero(double valor) {

            return String.format(
                    "$%,.0f",
                    valor
            ).replace(",", ".");
        }

        private void agregarEmpleadoInicial(
                String cedula,
                String nombre,
                double salario,
                String tipo,
                double bonificacion) {

            empleados.add(
                    new EMPLEADO(
                            cedula,
                            nombre,
                            salario,
                            tipo,
                            bonificacion
                    )
            );
        }
    }
