import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        BaseDatos.crearTabla();
        String opcion;

        do {
            System.out.println("\n===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar todos");
            System.out.println("3. Buscar (código o nombre)");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            opcion = leer("Opción: ");

            switch (opcion) {
                case "1" -> registrar();
                case "2" -> mostrar("SELECT * FROM productos", null);
                case "3" -> buscar();
                case "4" -> actualizar();
                case "5" -> eliminar();
                case "6" -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opción inválida.");
            }
        } while (!opcion.equals("6"));
    }

    // CREATE

    static void registrar() {
        String codigo = leer("Codigo: ");
        String nombre = leer("Nombre: ");
        double precio = leerPrecio("Precio: ");

        if (codigo.isEmpty() || nombre.isEmpty()) {
            System.out.println("El código, el nombre y el precio son obligatorios. El precio debe ser mayor a cero.");
            return;
        }

        String sql = "INSERT INTO productos (codigo, nombre, precio) VALUES (?, ?, ?)";
        try (Connection c = BaseDatos.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setDouble(3, precio);
            ps.executeUpdate();
            System.out.println("Producto registrado.");
        } catch (SQLException e) {
            System.out.println("No se pudo registrar (¿código repetido?): " + e.getMessage());
        }
    }

    // READ 

    static void buscar() {
        String texto = leer("Código o nombre: ");
        mostrar("SELECT * FROM productos WHERE codigo = ? OR LOWER(nombre) LIKE ?", texto);
    }

    static void mostrar(String sql, String texto) {
        try (Connection c = BaseDatos.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (texto != null) {
                ps.setString(1, texto);
                ps.setString(2, "%" + texto.toLowerCase() + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                boolean hay = false;
                while (rs.next()) {
                    hay = true;
                    System.out.printf("Código: %s | Nombre: %s | Precio: $%,.2f ",
                            rs.getString("codigo"), rs.getString("nombre"),
                            rs.getDouble("precio"));
                }
                if (!hay) {
                    System.out.println("No se encontraron productos.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }

    // UPDATE 

    static void actualizar() {
        String codigo = leer("Código  o nombredel producto a actualizar: ");
        double precio = leerPrecio("Nuevo precio: ");

        String sql = "UPDATE productos SET precio = ? WHERE codigo = ?";
        try (Connection c = BaseDatos.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, precio);
            ps.setString(2, codigo);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Producto actualizado." : "No existe un producto con ese codigo.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    // DELETE

    static void eliminar() {
        String codigo = leer("Código del producto a eliminar: ");
        String confirmar = leer("¿Seguro que desea eliminarlo? (SI/NO): ");

        if (!confirmar.equalsIgnoreCase("SI")) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        String sql = "DELETE FROM productos WHERE codigo = ?";
        try (Connection c = BaseDatos.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, codigo);
            int filas = ps.executeUpdate();
            System.out.println(filas > 0 ? "Producto eliminado." : "No existe un producto con ese código.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // ENTRADA Y VALIDACIÓN 

    static String leer(String mensaje) {
        System.out.print(mensaje);
        return in.nextLine().trim();
    }

    static double leerPrecio(String mensaje) {
        while (true) {
            try {
                double v = Double.parseDouble(leer(mensaje).replace(",", "."));
                if (v >= 0) {
                    return v;
                }
                System.out.println("El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: el precio debe ser un número.");
            }
        }
    }

}