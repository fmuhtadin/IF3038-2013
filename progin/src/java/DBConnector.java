
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Compaq
 */
public class DBConnector {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    public DBConnector() {
        
    }
    
    public void Init() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1/progin";
        con = (Connection) DriverManager.getConnection(url, "progin", "progin");
        statement = (Statement) con.createStatement();
        
    }
    
    public ResultSet ExecuteQuery(String query) throws SQLException {
        statement = (Statement) con.createStatement();
        return statement.executeQuery(query);
    }
    
    public int ExecuteUpdate(String query) throws SQLException {
        return statement.executeUpdate(query);
    }
    
    public void Close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
