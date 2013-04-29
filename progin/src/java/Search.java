/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Compaq
 */
public class Search extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String htmlresponse;
    private DBConnector con;
    private ResultSet resultSet = null;
    private ResultSet resultSet2 = null;
    private ResultSet resultSet3 = null;
    private ResultSet resultSet4 = null;
    
    @Override
    public void init() {
        con = new DBConnector();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String realPath = this.getServletConfig().getServletContext().getRealPath("/");
        System.out.println("realpath: "+realPath);
        try {
            htmlresponse = DoSearch(request);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(htmlresponse);
            out.close();
//            request.setAttribute("resultset", resultSet);
//            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/pages/Dashboard.jsp");
//            requestDispatcher.forward(request,response);
        } catch (Exception ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private String DoSearch(HttpServletRequest request) throws Exception {
        con.Init();
        String query;
        String filter = request.getParameter("filtertype");
        String searchquery = request.getParameter("searchquery");
        System.out.println(searchquery);
        String out = "";
        switch (filter) {
            case "All": {
                out+= doSearchUsername(request, out);
                out= doSearchAllTask(request, out);
                break;
            }
            case "Username": {
                out = doSearchUsername(request, out);
                break;
            }
            case "Category": {
                out = doSearchCategory(request, out);
                break;
            }
            case "Task": {
                out = doSearchTask(request, out);
                break;
            }
            default: break;
        }
        con.Close();
        return out;
    }

    private String doSearchUsername(HttpServletRequest request, String out) throws SQLException {
        String searchquery = request.getParameter("searchquery");
        
        //Pagination setup
        
        String querycount;
        if (!searchquery.equals("")) {
            querycount = "SELECT COUNT(*) as num FROM profil WHERE Username='"+searchquery+"'";
        } else {
            querycount = "SELECT COUNT(*) as num FROM profil";
        }
        resultSet = con.ExecuteQuery(querycount);
        if (resultSet.next()) {
            int total_pages = resultSet.getInt("num");
            int limit = 3; 
            int page = 0;
            int start;
            if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                page = Integer.parseInt(request.getParameter("page"));
                start = (page - 1) * limit; 			//first item to display on this page
            } else {
                start = 0;								//if no page var is given, set start to 0
            }
            
            String query;
            if (!searchquery.equals("")) {
                query = "SELECT * FROM profil WHERE Username='"+searchquery+"' LIMIT "+start+", "+limit+"";
            } else {
                query = "SELECT * FROM profil LIMIT "+start+", "+limit+"";
            }
            resultSet = con.ExecuteQuery(query);
            
            out += setupPagination(page, total_pages, limit, out);
            
            out+= "<table class='usersearchtable'>\n";
            while (resultSet.next()) {
                out+= "<tr>\n";
                out+= "<td>"+resultSet.getString("Username") +"</td>\n";
                out+= "<td>"+resultSet.getString("Fullname") +"</td>\n";
                out+= "<td> <img src='"+resultSet.getString("Avatar")+"'></td>\n";
                out+= "</tr>\n";
            }
            out+= "</table>\n";
        }
        
        
        return out;
    }

    private String doSearchCategory(HttpServletRequest request, String out) throws SQLException {
        String searchquery = request.getParameter("searchquery");
        
        String querycount;
        if (!searchquery.equals("")) {
            querycount = "SELECT COUNT(*) as num FROM category INNER JOIN task "+
                        "ON category.Category=task.Category "+
                        "WHERE category.Category='"+searchquery+"'";
        } else {
            querycount = "SELECT COUNT(*) as num FROM category INNER JOIN task "+
                        "ON category.Category=task.Category";
        }
        resultSet = con.ExecuteQuery(querycount);
        
        if (resultSet.next()) {
            
            int total_pages = resultSet.getInt("num");
            int limit = 3; 
            int page = 0;
            int start;
            if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                page = Integer.parseInt(request.getParameter("page"));
                start = (page - 1) * limit; 			//first item to display on this page
            } else {
                start = 0;								//if no page var is given, set start to 0
            }
            
            String query;
            if (!searchquery.equals("")) {
                query = "SELECT * FROM category INNER JOIN task "+
                        "ON category.Category=task.Category "+
                        "WHERE category.Category='"+searchquery+"' LIMIT "+start+", "+limit+"";
                resultSet = con.ExecuteQuery(query);
            }
            else {
                query = "SELECT * FROM category INNER JOIN task "+
                        "ON category.Category=task.Category LIMIT "+start+", "+limit+"";
                resultSet = con.ExecuteQuery(query);
            }
            resultSet = con.ExecuteQuery(query);
            
            out += setupPagination(page, total_pages, limit, out);
            
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
                query = "SELECT Tag FROM tags WHERE IDTask='"+resultSet.getString("ID")+"'";
                resultSet2 = con.ExecuteQuery(query);
                String tags = "";
                while (resultSet2.next()) {
                    tags += resultSet2.getString("Tag");
                }
                out+= "<tr>\n";
                out+= "        <td>\n";
                out+= "        <form name='hiddenidtask"+resultSet.getString("ID")+"' id='hiddenidtask"+resultSet.getString("ID")+"' action='rincian.jsp' method='post'>\n";
                out+= "            <input type='hidden' name='idtask' value='"+resultSet.getString("ID")+"' />\n";
                out+= "        </form>\n";
                out+= "        <a href=\"javascript:document.forms['hiddenidtask"+resultSet.getString("ID")+"'].submit()\"  >"+resultSet.getString("Nama")+"</a>\n";
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
        }
        return out;
    }
    
    private String doSearchTask(HttpServletRequest request, String out) throws SQLException {
        String searchquery = request.getParameter("searchquery");
        
        String querycount;
        if (!searchquery.equals("")) {
            querycount = "SELECT COUNT(*) as num FROM "
                    + "(SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline FROM task LEFT JOIN tags "+
                    "ON task.ID=tags.IDTask "+
                    "WHERE task.Nama='"+searchquery+"' OR tags.Tag='"+searchquery+"') as tabeltask";
        }
        else {
            querycount = "SELECT COUNT(*) as num FROM "
                    + "(SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline FROM task LEFT JOIN tags "+
                    "ON task.ID=tags.IDTask) as tabeltask";
        }
        resultSet = con.ExecuteQuery(querycount);
        
        if (resultSet.next()) {
            
            int total_pages = resultSet.getInt("num");
            int limit = 3; 
            int page = 0;
            int start;
            if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                page = Integer.parseInt(request.getParameter("page"));
                start = (page - 1) * limit; 			//first item to display on this page
            } else {
                start = 0;								//if no page var is given, set start to 0
            }
            
            String query;
            if (!searchquery.equals("")) {
                query = "SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline FROM task LEFT JOIN tags "+
                        "ON task.ID=tags.IDTask "+
                        "WHERE task.Nama='"+searchquery+"' OR tags.Tag='"+searchquery+"' LIMIT "+start+", "+limit+"";
                resultSet = con.ExecuteQuery(query);
            }
            else {
                query = "SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline FROM task LEFT JOIN tags "+
                        "ON task.ID=tags.IDTask LIMIT "+start+", "+limit+"";
                resultSet = con.ExecuteQuery(query);
            }
            out += setupPagination(page, total_pages, limit, out);
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
                query = "SELECT Tag FROM tags WHERE IDTask='"+resultSet.getString("ID")+"'";
                resultSet2 = con.ExecuteQuery(query);
                String tags = "";
                while (resultSet2.next()) {
                    tags += resultSet2.getString("Tag");
                }
                out+= "<tr>\n";
                out+= "        <td>\n";
                out+= "        <form name='hiddenidtask"+resultSet.getString("ID")+"' id='hiddenidtask"+resultSet.getString("ID")+"' action='rincian.jsp' method='post'>\n";
                out+= "            <input type='hidden' name='idtask' value='"+resultSet.getString("ID")+"' />\n";
                out+= "        </form>\n";
                out+= "        <a href=\"javascript:document.forms['hiddenidtask"+resultSet.getString("ID")+"'].submit()\"  >"+resultSet.getString("Nama")+"</a>\n";
                out+= "        </td>\n";
                out+= "        <td>"+resultSet.getString("Deadline")+"</td>\n";
                if (resultSet.getInt("Status") == 1) {
                out+= "        <td> finished </td>\n";
                }
                else
                    out+= "        <td> not finished </td>\n";
                out+= "        <td>"+tags+"</td>\n";
                if (resultSet.getInt("Status") == 1) 
                    out+= "<td><input type='checkbox' checked /></td>\n";
                else
                    out+= "<td><input type='checkbox' />\n</td>";
                out+= "</tr>\n";
            }
            out+= "</table>\n";
        }
        return out;
    }
    
    private String doSearchAllTask(HttpServletRequest request, String out) throws SQLException {
        String searchquery = request.getParameter("searchquery");
        
        String querycount;
        if (!searchquery.equals("")) {
            querycount = "SELECT COUNT(*) as num FROM "
                    + "(SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline " +
                    "FROM category INNER JOIN task LEFT JOIN tags " +
                    "ON category.Category=task.Category AND task.ID=tags.IDTask " +
                    "WHERE Tag='"+searchquery+"' OR Nama='"+searchquery+"' OR task.Category='"+searchquery+"') as tabeltask";
        } else {
            querycount = "SELECT COUNT(*) as num FROM "
                    + "(SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline " +
                    "FROM category INNER JOIN task LEFT JOIN tags " +
                    "ON category.Category=task.Category AND task.ID=tags.IDTask) as tabeltask";
        }
        resultSet = con.ExecuteQuery(querycount);
        
        if (resultSet.next()) {
            
            int total_pages = resultSet.getInt("num");
            int limit = 3; 
            int page = 0;
            int start;
            if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                page = Integer.parseInt(request.getParameter("page"));
                start = (page - 1) * limit; 			//first item to display on this page
            } else {
                start = 0;								//if no page var is given, set start to 0
            }
        
            String query;
            if (!searchquery.equals("")) {
                query = "SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline " +
                        "FROM category INNER JOIN task LEFT JOIN tags " +
                        "ON category.Category=task.Category AND task.ID=tags.IDTask " +
                        "WHERE Tag='"+searchquery+"' OR Nama='"+searchquery+"' OR task.Category='"+searchquery+"' LIMIT "+start+", "+limit+"";
            } else {
                query = "SELECT DISTINCT task.ID, task.IDCreator, task.Nama, task.Category, task.Status, task.Deadline " +
                        "FROM category INNER JOIN task LEFT JOIN tags " +
                        "ON category.Category=task.Category AND task.ID=tags.IDTask LIMIT "+start+", "+limit+"";
            }
            resultSet = con.ExecuteQuery(query);
            out= setupPagination(page, total_pages, limit, out);
            out+= "<p>Task found</p>";
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
                query = "SELECT Tag FROM tags WHERE IDTask='"+resultSet.getString("ID")+"'";
                resultSet2 = con.ExecuteQuery(query);
                String tags = "";
                while (resultSet2.next()) {
                    tags += resultSet2.getString("Tag");
                }
                out+= "<tr>\n";
                out+= "        <td>\n";
                out+= "        <form name='hiddenidtask"+resultSet.getString("ID")+"' id='hiddenidtask"+resultSet.getString("ID")+"' action='rincian.jsp' method='post'>\n";
                out+= "            <input type='hidden' name='idtask' value='"+resultSet.getString("ID")+"' />\n";
                out+= "        </form>\n";
                out+= "        <a href=\"javascript:document.forms['hiddenidtask"+resultSet.getString("ID")+"'].submit()\"  >"+resultSet.getString("Nama")+"</a>\n";
                out+= "        </td>\n";
                out+= "        <td>"+resultSet.getString("Deadline")+"</td>\n";
                if (resultSet.getInt("Status") == 1) {
                out+= "        <td> finished </td>\n";
                }
                else
                    out+= "        <td> not finished </td>\n";
                out+= "        <td>"+tags+"</td>\n";
                if (resultSet.getInt("Status") == 1) 
                    out+= "<td><input type='checkbox' checked /></td>\n";
                else
                    out+= "<td><input type='checkbox' />\n</td>";
                out+= "</tr>\n";
            }
            out+= "</table>\n";
        }
        return out;
    }
    
    private String setupPagination(int page, int total_pages, int limit, String out) {
        /* Setup page vars for display. */
        int adjacents = 3;
        if (page == 0) page = 1;					//if no page var is given, default to 1.
        int prev = page - 1;							//previous page is page - 1
        int next = page + 1;							//next page is page + 1
        int lastpage = (int) Math.ceil((float)total_pages/(float)limit );		//lastpage is = total pages / items per page, rounded up.
        int lpm1 = lastpage - 1;						//last page minus 1
        /*
        Now we apply our rules and draw the pagination object.
        We're actually saving the code to a variable in case we want to draw it more than once.
         */
        if(lastpage > 1)
        {	
                out+= "<div class=\"pagination\">\n";
                //previous button
                if (page > 1) 
                        out+= "<a href='#' onclick=\"loadSearchResult("+prev+")\">? previous</a>";
                else
                        out+= "<span class=\"disabled\">? previous</span>";	

                //pages
                int counter = 1;
                if (lastpage < 7 + (adjacents * 2))	//not enough pages to bother breaking it up
                {	
                        for (counter = 1; counter <= lastpage; counter++)
                        {
                                if (counter == page)
                                        out+= "<span class=\"current\">"+counter+"</span>";
                                else
                                        out+= "<a href='#' onclick=\"loadSearchResult("+counter+")\">"+counter+"</a>";					
                        }
                }
                else if(lastpage > 5 + (adjacents * 2))	//enough pages to hide some
                {
                        //close to beginning; only hide later pages
                        if(page < 1 + (adjacents * 2))		
                        {
                                for (counter = 1; counter < 4 + (adjacents * 2); counter++)
                                {
                                        if (counter == page)
                                                out+= "<span class=\"current\">"+counter+"</span>";
                                        else
                                                out+= "<a href='#' onclick=\"loadSearchResult("+counter+")\">"+counter+"</a>";					
                                }
                                out+= "...";
                                out+= "<a href='#' onclick=\"loadSearchResult("+lpm1+")\">"+lpm1+"</a>";
                                out+= "<a href='#' onclick=\"loadSearchResult("+lastpage+")\">"+lastpage+"</a>";		
                        }
                        //in middle; hide some front and some back
                        else if(lastpage - (adjacents * 2) > page && page > (adjacents * 2))
                        {
                                out+= "<a href='#' onclick=\"loadSearchResult(1)\">1</a>";
                                out+= "<a href='#' onclick=\"loadSearchResult(2)\">2</a>";
                                out+= "...";
                                for (counter = page - adjacents; counter <= page + adjacents; counter++)
                                {
                                        if (counter == page)
                                                out+= "<span class=\"current\">"+counter+"</span>";
                                        else
                                                out+= "<a href='#' onclick=\"loadSearchResult("+counter+")\">"+counter+"</a>";					
                                }
                                out+= "...";
                                out+= "<a href='#' onclick=\"loadSearchResult("+lpm1+")\">"+lpm1+"</a>";
                                out+= "<a href='#' onclick=\"loadSearchResult("+lastpage+")\">"+lastpage+"</a>";		
                        }
                        //close to end; only hide early pages
                        else
                        {
                                out+= "<a href='#' onclick=\"loadSearchResult(1)\">1</a>";
                                out+= "<a href='#' onclick=\"loadSearchResult(2)\">2</a>";
                                out+= "...";
                                for (counter = lastpage - (2 + (adjacents * 2)); counter <= lastpage; counter++)
                                {
                                        if (counter == page)
                                                out+= "<span class=\"current\">"+counter+"</span>";
                                        else
                                                out+= "<a href='#' onclick=\"loadSearchResult("+counter+")\">"+counter+"</a>";					
                                }
                        }
                }

                //next button
                if (page < counter - 1) 
                        out+= "<a href='#' onclick=\"loadSearchResult("+next+")\">next ?</a>";
                else
                        out+= "<span class=\"disabled\">next ?</span>\n";
                out+= "</div>\n";		
        }
        return out;
    }
}
