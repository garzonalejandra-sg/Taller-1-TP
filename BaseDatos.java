import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

    private static final String URL = "jdbc:sqlite:productos.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "codigo TEXT PRIMARY KEY, "
                + "nombre TEXT NOT NULL, "
                + "precio REAL NOT NULL CHECK (precio >= 0))";
        try (Connection c = conectar(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            System.exit(1);
        }
    }
}