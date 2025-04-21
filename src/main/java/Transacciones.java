import util.ConexionBD;

import java.sql.*;

public class Transacciones {
    public static void main(String[] args) throws SQLException {
        try (Connection con = ConexionBD.creaConexion()) {

            System.out.println("Conectado a la BBDD");

            // Ejecutamos una prueba
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cliente");
            while (rs.next()) {
                System.out.println(
                    "código: " + rs.getInt("codigo_cliente") + " " +
                    "nombre: " + rs.getString("nombre_cliente")
                );
            }
            
            // Fijamos Autocommit en false
            con.setAutoCommit(false);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Desconectado de la BBDD");
    }
}
