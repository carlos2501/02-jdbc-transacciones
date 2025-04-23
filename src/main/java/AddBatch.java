import com.github.javafaker.Faker;
import util.ConexionBD;

import java.sql.*;
import java.util.Locale;

public class AddBatch {
    public static void main(String[] args) throws SQLException {
        String qry = "INSERT INTO cliente (codigo_cliente, nombre_cliente, telefono, ciudad) VALUES(?, ?, ?, ?)";
        try (Connection con = ConexionBD.creaConexion();
             PreparedStatement stmt = con.prepareStatement(qry)) {
            System.out.println("Conectado a la BBDD");

            // Asignamos los valores de los parámetros de la consulta para dos nuevos clientes
            try {
                Faker faker = new Faker(new Locale("es"));
                for (int i = 100; i < 200; i++) {
                    // Cada bucle genera un insert que se ejecuta inmediatamente al no haber fijado autocommit(false)
                    stmt.setInt(1, i);
                    stmt.setString(2, faker.name().name());
                    stmt.setString(3, faker.phoneNumber().cellPhone());
                    stmt.setString(4, faker.address().city());
                    stmt.addBatch();
                }
                int[] batch = stmt.executeBatch(); // Devuelve la cantidad de registros afectados en cda sentencia
                for (int filas : batch) {
                    System.out.println("filas afectadas: " + filas);
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            // Comprobamos la inserción
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM cliente WHERE codigo_cliente >= ?");
            pstmt.setInt(1, 100);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.printf("\nId [%d] Nombre [%s]", rs.getInt("codigo_cliente"), rs.getString("nombre_cliente"));
            }
        }
        System.out.println("\nDesconectado de la BBDD");
    }
}
