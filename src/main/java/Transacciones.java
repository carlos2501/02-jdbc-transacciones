import util.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transacciones {
    public static void main(String[] args) throws SQLException {

        String qry ="SELECT * FROM cliente";
        try (Statement st  = ConexionBD.creaConexion().createStatement()) {
            ResultSet rs = st.executeQuery(qry);
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("nombre_cliente") + " Tlf.: " + rs.getString("telefono"));
            }
        }

    }
}
