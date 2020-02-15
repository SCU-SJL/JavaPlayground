package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Run {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "SELECT name FROM foo WHERE id = (?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, 3);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            Foo foo = new Foo();
            foo.setId(1);
            foo.setName(result.getString("name"));
            System.out.println(foo);
        }
    }
}
