
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Compaq
 */

public class SearchCoba extends HttpServlet {
    String htmlresponse;
    private DBConnector con;
    private ResultSet resultSet = null;
    private ResultSet resultSet2 = null;
    
    public SearchCoba() {
    }
    
    @Override
    public void init() {
        try {
            con = new DBConnector();
            con.Init();
        } catch (Exception ex) {
            Logger.getLogger(SearchCoba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
        String realPath = this.getServletConfig().getServletContext().getRealPath("/");
        try {
            htmlresponse = DoSearch(request.getParameter("filter"), request.getParameter("query"));
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(htmlresponse);
//            request.setAttribute("resultset", resultSet);
//            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/Dashboard.jsp");
//            requestDispatcher.forward(request,response);
        } catch (Exception ex) {
            Logger.getLogger(SearchCoba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String DoSearch(String filter, String searchquery) throws Exception {
        String query;
        String out = "";
        switch (filter) {
            case "all": {
                break;
            }
            case "user": {
                if (!searchquery.equals("")) {
                    query = "SELECT * FROM profil WHERE Username='"+searchquery+"'";
                } else {
                    query = "SELECT * FROM profil";
                }
                resultSet = con.ExecuteQuery(query);
                out+= "<table class='usersearchtable'>\n";
                while (resultSet.next()) {
                    out+= "<tr>\n";
                    out+= "<td>"+resultSet.getString("Username") +"</td>\n";
                    out+= "<td>"+resultSet.getString("Fullname") +"</td>\n";
                    out+= "<td> <img src='"+resultSet.getString("Avatar")+"'></td>\n";
                    out+= "</tr>\n";
                }
                out+= "</table>\n";
                break;
            }
            case "category": {
                if (!searchquery.equals("")) {
                    query = "SELECT * FROM category INNER JOIN task "+
                            "ON category.Category=task.Category"+
                            "WHERE category.Category='"+searchquery+"'";
                    resultSet = con.ExecuteQuery(query);
                }
                else {
                    query = "SELECT * FROM category INNER JOIN task "+
                            "ON category.Category=task.Category";
                    resultSet = con.ExecuteQuery(query);
                }
                out+= "<table class='categorysearchtable'>\n";
                out+= "<tr>\n";
                out+= "     <td>Nama Task</td>\n";
                out+= "     <td>Deadline</td>\n";
                out+= "     <td>Status</td>\n";
                out+= "     <td>Tags</td>\n";
                out+= "     <td> Check if done </td>\n";
                out+= "     </tr>\n";
                while(resultSet.next())
                {
                    query = "SELECT Tag FROM tags WHERE IDTask='"+resultSet.getString("IDTask")+"'";
                    resultSet2 = con.ExecuteQuery(query);
                    String tags = "";
                    while (resultSet2.next()) {
                        tags += resultSet2.getString("Tag");
                    }
                    out+= "<tr>\n";
                    out+= "        <td>\n";
                    out+= "        <form name='hiddenidtask' id='hiddenidtask' action='rincian.php' method='post'>\n";
                    out+= "            <input type='hidden' name='idtask' value='"+resultSet.getString("ID")+"' />\n";
                    out+= "        </form>\n";
                    out+= "        <a href=\"javascript:document.forms['hiddenidtask'].submit()\"  >"+resultSet.getString("Nama")+"</a>\n";
                    out+= "        </td>\n";
                    out+= "        <td>"+resultSet.getString("Deadline")+"</td>\n";
                    if (resultSet.getInt("Status") == 1) {
                    out+= "        <td> finished </td>\n";
                    }
                    else
                        out+= "        <td> not finished </td>\n";
                        out+= "        <td>"+tags+"</td>\n";
                    if (resultSet.getInt("Status") == 1) {
                        out+= "        <td><input type='checkbox' checked /></td>\n";
                    }
                    else
                        out+= "        <td><input type='checkbox' /></td>\n";
                    out+= "</tr>\n";
                }
                out+= "</table>\n";
                break;
            }
            case "task": {
                if (!searchquery.equals("")) {
                    query = "SELECT * FROM task LEFT JOIN tags "+
                            "ON task.ID=tags.IDTask"+
                            "WHERE task.Nama='"+searchquery+"' OR tags.Tag='"+searchquery+"'";
                    resultSet = con.ExecuteQuery(query);
                }
                else {
                    query = "SELECT * FROM task LEFT JOIN tags "+
                            "ON task.ID=tags.IDTask ";
                    resultSet = con.ExecuteQuery(query);
                }

                out+= "<table class='tasksearchtable'>\n";
                out+= "<tr>\n";
                out+= "    <td>Nama Task</td>\n";
                out+= "    <td>Deadline</td>\n";
                out+= "    <td>Status</td>\n";
                out+= "    <td>Tags</td>\n";
                out+= "    <td> Check if done </td>\n";
                out+= "</tr>\n";
                while(resultSet.next())
                {
                    query = "SELECT Tag FROM tags WHERE IDTask='"+resultSet.getString("IDTask")+"'";
                    resultSet2 = con.ExecuteQuery(query);
                    String tags = "";
                    while (resultSet.next()) {
                        tags += resultSet2.getString("Tag");
                    }
                    out+= "<tr>\n";
                    out+= "        <td>\n";
                    out+= "        <form name='hiddenidtask2' id='hiddenidtask2' action='rincian.php' method='post'>\n";
                    out+= "            <input type='hidden' name='idtask' value='"+resultSet.getString("ID")+"' />\n";
                    out+= "        </form>\n";
                    out+= "        <a href='javascript:document.getElementById('hiddenidtask2').submit();' >"+resultSet.getString("Nama")+"</a>\n";
                    out+= "        </td>\n";
                    out+= "        <td>"+resultSet.getString("Deadline")+"</td>\n";
                    out+= "        <td>"+resultSet.getString("Status")+"</td>\n";
                    out+= "        <td>"+tags+"</td>\n";
                    if (resultSet.getInt("Status") == 1) 
                        out+= "<input type='checkbox' checked />\n";
                    else
                        out+= "<input type='checkbox' />\n";
                    out+= "</tr>\n";
                }
                out+= "</table>\n";
                break;
            }
            default: break;
        }
        con.Close();
        return out;
    }
    
}
