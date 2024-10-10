package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
	// Variables de conexión
	private static final String DB_URL = "jdbc:mysql://localhost:3306/tienda";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	// Método para conectar a la base de datos
	private Connection conectar() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	// Método para obtener todos los productos de la base de datos
	public List<Producto> obtenerProductos() throws SQLException {
		List<Producto> productos = new ArrayList<>();
		String query = "SELECT * FROM productos"; // Asegúrate de que la tabla exista
		try (Connection conn = conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				double precio = rs.getDouble("precio");
				Producto producto = new Producto(id, nombre, descripcion, precio);
				productos.add(producto);
			}
		}
		return productos;
	}

	// Método para agregar un producto
	public void agregarProducto(Producto producto) throws SQLException {
		// Asegúrate de que el objeto producto no sea null y tenga valores válidos
		if (producto == null || producto.getNombre() == null || producto.getDescripcion() == null
				|| producto.getPrecio() < 0) {
			throw new IllegalArgumentException("El producto no puede ser nulo y los valores deben ser válidos.");
		}

		// Definir la consulta SQL para agregar un producto
		String sql = "INSERT INTO productos (nombre, descripcion, precio) VALUES (?, ?, ?)";

		// Usar try-with-resources para asegurar que la conexión y el PreparedStatement
		// se cierren adecuadamente
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, producto.getNombre());
			pstmt.setString(2, producto.getDescripcion());
			pstmt.setDouble(3, producto.getPrecio());

			// Ejecutar la consulta
			int filasAfectadas = pstmt.executeUpdate();

			// Opcional: Puedes comprobar cuántas filas se vieron afectadas
			if (filasAfectadas > 0) {
				System.out.println("Producto agregado con éxito.");
			} else {
				System.out.println("No se pudo agregar el producto.");
			}
		} catch (SQLException e) {
			// Manejo de la excepción SQL
			System.err.println("Error al agregar el producto: " + e.getMessage());
			throw e; // Lanzar de nuevo la excepción para que el llamador pueda manejarla
		}
	}

	// Método para listar productos
	public String listarProductos() throws SQLException {
		StringBuilder productos = new StringBuilder();
		String sql = "SELECT * FROM productos";
		try (Connection conn = conectar();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				productos.append("ID: ").append(rs.getInt("id")).append(", Nombre: ").append(rs.getString("nombre"))
						.append(", Descripción: ").append(rs.getString("descripcion")).append(", Precio: ")
						.append(rs.getDouble("precio")).append("\n");
			}
		}
		return productos.toString();
	}

	// Método para actualizar un producto
	public void actualizarProducto(Producto producto) throws SQLException {
		String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ? WHERE id = ?";
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, producto.getNombre());
			pstmt.setString(2, producto.getDescripcion());
			pstmt.setDouble(3, producto.getPrecio());
			pstmt.setInt(4, producto.getId());
			pstmt.executeUpdate();
		}
	}

	// Método para eliminar un producto
	public void eliminarProducto(int id) throws SQLException {
		String sql = "DELETE FROM productos WHERE id = ?";
		try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}
}
