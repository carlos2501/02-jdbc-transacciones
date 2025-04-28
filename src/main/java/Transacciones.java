import util.ConexionBD;

import java.sql.*;

public class Transacciones {
    public static void main(String[] args) throws SQLException {

        String qry ="SELECT * FROM cliente";
        try (Statement st  = ConexionBD.creaConexion().createStatement()) {
            ResultSet rs = st.executeQuery(qry);
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("nombre_cliente") + " Tlf.: " + rs.getString("telefono"));
            }
        }

        System.out.println("--------- Insertamos un nuevo registro ---------------");
        qry = "INSERT INTO cliente (codigo_cliente, nombre_cliente, telefono, ciudad) VALUES(?, ?, ?, ?)";
        try (Connection con = ConexionBD.creaConexion();
             PreparedStatement stmt = con.prepareStatement(qry)) {
            // desactivamos autocommit
            con.setAutoCommit(false);
            System.out.println("Conectado a la BBDD");
            try {

                // Asignamos los valores de los parámetros de la consulta para dos nuevos clientes
                stmt.setInt(1, 100);
                stmt.setString(2, "Carlos");
                stmt.setString(3, "+34658745269");
                stmt.setString(4, "Madrid");
                stmt.executeUpdate();

                stmt.setInt(1, 101);
                stmt.setString(2, null);
                stmt.setString(3, "+345698999");
                stmt.setString(4, "Málaga");
                stmt.executeUpdate();
                con.commit();
                System.out.println("Añadido cliente 100");
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Deshecha la transacción debido a " + e.getMessage());
            }
        }

        String qry2 ="SELECT * FROM cliente WHERE codigo_cliente = ?";
        try (Connection con2 = ConexionBD.creaConexion();
             PreparedStatement stmt2 = con2.prepareStatement(qry2)) {
            stmt2.setInt(1, 100);
            System.out.println("------ Comprobamos que se ha grabado en la BBDD -------");
            stmt2.execute();
            ResultSet rs2 = stmt2.getResultSet();
            if(rs2.next()) {
                System.out.println("Nombre: " + rs2.getString("nombre_cliente") + " Tlf.: " + rs2.getString("telefono"));
            } else {
                System.out.println("No se encontró el cliente 100");
            }
        }
        System.out.println("Desconectado de la BBDD");


    }
}
