package Console;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto;
import Modelo.ProductoDAO;

public class Interfaz extends JFrame {
    private ProductoDAO productoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    public Interfaz() {
        // Configurar el marco de la aplicación
        setTitle("Gestión de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear botones para CRUD
        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnListar = new JButton("Listar Productos");
        JButton btnActualizar = new JButton("Actualizar Producto");
        JButton btnEliminar = new JButton("Eliminar Producto");

        // Panel para los botones (GridLayout para organizar mejor)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnListar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        
        // Crear una tabla para mostrar los productos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Descripción", "Precio"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        
        // Agregar los componentes al panel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Agregar el panel principal al marco
        add(mainPanel);

		productoDAO = new ProductoDAO();

        // Acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    // Método para agregar un producto (lógica de ejemplo)
    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog("Nombre del producto:");
        String descripcion = JOptionPane.showInputDialog("Descripción del producto:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio del producto:"));
        Producto producto = new Producto(nombre, descripcion, precio);
        try {
            productoDAO.agregarProducto(producto);
            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente");
            listarProductos(); // Actualizar la tabla
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto");
            e.printStackTrace();
        }
    }

    // Método para listar productos en la tabla
    private void listarProductos() {
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            tableModel.setRowCount(0); // Limpiar la tabla
            for (Producto producto : productos) {
                tableModel.addRow(new Object[]{producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar productos");
            e.printStackTrace();
        }
    }

    // Método para actualizar un producto (lógica de ejemplo)
    private void actualizarProducto() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar");
            return;
        }
        int id = (int) tableModel.getValueAt(filaSeleccionada, 0);
        String nombre = JOptionPane.showInputDialog("Nuevo nombre del producto:", tableModel.getValueAt(filaSeleccionada, 1));
        String descripcion = JOptionPane.showInputDialog("Nueva descripción del producto:", tableModel.getValueAt(filaSeleccionada, 2));
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio del producto:", tableModel.getValueAt(filaSeleccionada, 3)));
        Producto producto = new Producto(id, nombre, descripcion, precio);
        try {
            productoDAO.actualizarProducto(producto);
            JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente");
            listarProductos(); // Actualizar la tabla
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto");
            e.printStackTrace();
        }
    }

    // Método para eliminar un producto
    private void eliminarProducto() {
        int filaSeleccionada = table.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
            return;
        }
        int id = (int) tableModel.getValueAt(filaSeleccionada, 0);
        try {
            productoDAO.eliminarProducto(id);
            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
            listarProductos(); // Actualizar la tabla
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Interfaz frame = new Interfaz();
        frame.setVisible(true);
    }
}
