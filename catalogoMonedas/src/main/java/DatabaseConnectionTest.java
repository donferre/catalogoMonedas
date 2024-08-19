import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/catalogo_monedas"; // Cambia según tu configuración
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
                
                // Mostrar las tablas de la base de datos
                mostrarTablas(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos.");
        }
    }

    private static void mostrarTablas(Connection connection) {
        String query = "SHOW TABLES"; // Consulta para obtener la lista de tablas
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             
            System.out.println("Tablas en la base de datos:");
            while (resultSet.next()) {
                String tableName = resultSet.getString(1); // Obtener el nombre de la tabla
                System.out.println(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener la lista de tablas.");
        }
    }
}
