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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Compaq
 */
public class Rincian extends HttpServlet {

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
    
    String htmlresponse = "";
    private DBConnector con;
    private ResultSet resultSet = null;
    private ResultSet resultSet2 = null;
    
    @Override
    public void init() {
        con = new DBConnector();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String requesttype = request.getParameter("requesttype");
            if (requesttype.equals("load")) {
                htmlresponse = doLoadRincian(request);
            } else if (requesttype.equals("save")) {
                htmlresponse = doSaveRincian(request);
            } else if (requesttype.equals("delete")) {
                htmlresponse = doDeleteComment(request);
            }
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(htmlresponse);
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String doLoadRincian(HttpServletRequest request) throws Exception {
        if (request.getParameter("tabletype").equals("taskdetails")) {
            return doLoadTaskDetails(request);
        } else if (request.getParameter("tabletype").equals("komentar")) {
            return doLoadComment(request);
        } else {
            return "";
        }
    }
    
    private String doLoadTaskDetails(HttpServletRequest request) throws SQLException, Exception {
        String out = "";
        con.Init();
        String username = (String) request.getSession().getAttribute("userid");
        String taskid = (String) request.getSession().getAttribute("taskid");
        String query = "SELECT * FROM task LEFT JOIN assignee "+
                        "ON task.ID=assignee.IDTask "+
                        "WHERE task.ID='"+taskid+"' AND (task.IDCreator='"+username+"' OR assignee.IDUser='"+username+"')";
        resultSet = con.ExecuteQuery(query);
        resultSet2 = con.ExecuteQuery(query);
        if (resultSet.next()) {
            out+= "<form name='rincian'>\n";
            out+= "<h1 class='judul'>Rincian Tugas</h1>\n";
            out+= "Nama Task : <input type='text' id='namatask' value='"+resultSet.getString("Nama")+"' border='0' /> <br>";
            out+= "Attachment : <br> \n";
            out+= "<video width='320' height='240' controls='controls'>\n";
            out+= "<source src='assets/mov_bbb.ogg' type='video/ogg'>\n";
            out+= "Your browser does not support the video tag.\n";
            out+= "</video>\n";
            out+= "<br>\n";
            out+= "Deadline : <input  type='text' id='deadline' value='"+resultSet.getString("Deadline")+"' border='0' /><br>\n";
            
            String listassignee = "";
            String listassignee1 = "";
            while (resultSet2.next()) {
                if (resultSet2.getString("IDUser") != null) {
                    listassignee += resultSet2.getString("IDUser")+",";
                }
            }
            if (!listassignee.equals("")) {
                listassignee1 = listassignee.substring(0, listassignee.length()-1);
            }
            out+= "Assignee : <input type='text' id='listassignee' value='"+listassignee1+"' border='0' />";
            
            query = "SELECT * FROM task LEFT JOIN tags "+
                    "ON task.ID=tags.IDTask "+
                    "WHERE task.ID='"+resultSet.getString("ID") +"'";
            resultSet2 = con.ExecuteQuery(query);
            String listtag = "";
            while (resultSet2.next()) {
                listtag += resultSet2.getString("Tag")+",";
            }
            String listtag1 = listtag.substring(0, listtag.length()-1);
            out+= "Tag : <input  type='text' id='listtag' value='"+listtag1+"' border='0' /> <br> \n";
            
            out+= "<img src='images/edit.png' width='150' id='edit-button' value='EDIT' onClick='edit()' />\n";
            out+= "<img src='images/save.png' width='150' id='save-button' value='SAVE' onClick='saveTaskDetails()' /><br><br>\n";
        } else {
            query = "SELECT * FROM task LEFT JOIN assignee "+
                    "ON task.ID=assignee.IDTask "+
                    "WHERE task.ID='"+taskid+"'";
            resultSet = con.ExecuteQuery(query);
            resultSet2 = con.ExecuteQuery(query);
            if (resultSet.next()) {
                out+= "<form name='rincian'>\n";
                out+= "<h1 class='judul'>Rincian Tugas</h1>\n";
                out+= "Nama Task : <input type='text' id='namatask' value='"+resultSet.getString("Nama")+"' border='0' /> <br>";
                out+= "Attachment : <br> \n";
                out+= "<video width='320' height='240' controls='controls'>\n";
                out+= "<source src='assets/mov_bbb.ogg' type='video/ogg'>\n";
                out+= "Your browser does not support the video tag.\n";
                out+= "</video>\n";
                out+= "<br>\n";
                out+= "Deadline : <input  type='text' id='deadline' value='"+resultSet.getString("Deadline")+"' border='0' /><br>\n";

                String listassignee = "";
                String listassignee1 = "";
                while (resultSet2.next()) {
                    if (resultSet2.getString("IDUser") != null) {
                        listassignee += resultSet2.getString("IDUser")+",";
                    }
                }
                if (!listassignee.equals("")) {
                    listassignee1 = listassignee.substring(0, listassignee.length()-1);
                }
                out+= "Assignee : <input type='text' id='listassignee' value='"+listassignee1+"' border='0' />";

                query = "SELECT * FROM task LEFT JOIN tags "+
                        "ON task.ID=tags.IDTask "+
                        "WHERE task.ID='"+resultSet.getString("ID") +"'";
                resultSet2 = con.ExecuteQuery(query);
                String listtag = "";
                while (resultSet2.next()) {
                    listtag += resultSet2.getString("Tag")+",";
                }
                String listtag1 = listtag.substring(0, listtag.length()-1);
                out+= "Tag : <input  type='text' id='listtag' value='"+listtag1+"' border='0' /> <br> \n";
            }
        }
        con.Close();
        return out;
    }
    
    private String doLoadComment(HttpServletRequest request) throws Exception {
        String out = "";
        con.Init();
        String iduser = (String) request.getSession().getAttribute("userid");
        String idtask = (String) request.getSession().getAttribute("taskid");
        

                // How many adjacent pages should be shown on each side?
                int adjacents = 3;

                /* 
                   First get total number of rows in data table. 
                   If you have a WHERE clause in your query, make sure you mirror it here.
                */
                String query = "SELECT COUNT(*) as num FROM komentar INNER JOIN task ON task.ID=komentar.IDTask WHERE task.ID='"+idtask+"'";
                resultSet = con.ExecuteQuery(query);
                if (resultSet.next()) {
                    int total_pages = resultSet.getInt("num");
                    /* Setup vars for query. */
                    String targetpage = "php/pagination.php"; 	//your file name  (the name of this file)
                    int limit = 10; 								//how many items to show per page
                    int page = 0;
                    int start;
                    if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
                        page = Integer.parseInt(request.getParameter("page"));
                        start = (page - 1) * limit; 			//first item to display on this page
                    } else {
                        start = 0;								//if no page var is given, set start to 0
                    }
                    
                    /* Get data. */
                    String sql = "SELECT * FROM komentar INNER JOIN task ON task.ID=komentar.IDTask WHERE task.ID='"+idtask+"' LIMIT "+start+", "+limit+"";
                    resultSet = con.ExecuteQuery(sql);

                    /* Setup page vars for display. */
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
                                    out+= "<a href='#' onclick=\"loadComment("+prev+")\">? previous</a>";
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
                                                    out+= "<a href='#' onclick=\"loadComment("+counter+")\">"+counter+"</a>";					
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
                                                            out+= "<a href='#' onclick=\"loadComment("+counter+")\">"+counter+"</a>";					
                                            }
                                            out+= "...";
                                            out+= "<a href='#' onclick=\"loadComment("+lpm1+")\">"+lpm1+"</a>";
                                            out+= "<a href='#' onclick=\"loadComment("+lastpage+")\">"+lastpage+"</a>";		
                                    }
                                    //in middle; hide some front and some back
                                    else if(lastpage - (adjacents * 2) > page && page > (adjacents * 2))
                                    {
                                            out+= "<a href='#' onclick=\"loadComment(1)\">1</a>";
                                            out+= "<a href='#' onclick=\"loadComment(2)\">2</a>";
                                            out+= "...";
                                            for (counter = page - adjacents; counter <= page + adjacents; counter++)
                                            {
                                                    if (counter == page)
                                                            out+= "<span class=\"current\">"+counter+"</span>";
                                                    else
                                                            out+= "<a href='#' onclick=\"loadComment("+counter+")\">"+counter+"</a>";					
                                            }
                                            out+= "...";
                                            out+= "<a href='#' onclick=\"loadComment("+lpm1+")\">"+lpm1+"</a>";
                                            out+= "<a href='#' onclick=\"loadComment("+lastpage+")\">"+lastpage+"</a>";		
                                    }
                                    //close to end; only hide early pages
                                    else
                                    {
                                            out+= "<a href='#' onclick=\"loadComment(1)\">1</a>";
                                            out+= "<a href='#' onclick=\"loadComment(2)\">2</a>";
                                            out+= "...";
                                            for (counter = lastpage - (2 + (adjacents * 2)); counter <= lastpage; counter++)
                                            {
                                                    if (counter == page)
                                                            out+= "<span class=\"current\">"+counter+"</span>";
                                                    else
                                                            out+= "<a href='#' onclick=\"loadComment("+counter+")\">"+counter+"</a>";					
                                            }
                                    }
                            }

                            //next button
                            if (page < counter - 1) 
                                    out+= "<a href='#' onclick=\"loadComment("+next+")\">next ?</a>";
                            else
                                    out+= "<span class=\"disabled\">next ?</span>\n";
                            out+= "</div>\n";		
                    }


                    while(resultSet.next())
                    {
                        out+="-- "+resultSet.getString("IDUser")+" "+resultSet.getString("Waktu")+" "+resultSet.getString("Isi")+"\n";
                        if (resultSet.getString("IDUser").equals(iduser)) {
                            out+= "<form name='hiddenidcomment"+resultSet.getString("ID")+"' id='hiddenidcomment"+resultSet.getString("ID")+"' onsubmit=\"return deleteComment('"+resultSet.getString("ID")+"')\" method='post'>\n";
                            out+= "     <input type='hidden' name='idcomment' value='"+resultSet.getString("ID")+"' />\n";
                            out+= "<input type='submit' value='delete' />\n";
                            out+= "</form></br>\n";
                            
                        } else {
                            out+= "</br>\n";
                        }
                    }
                }
                
        return out;
    }
    
    private String doSaveRincian(HttpServletRequest request) throws SQLException, Exception {
        if (request.getParameter("tabletype").equals("taskdetails")) {
            return doSaveTaskDetails(request);
        } else if (request.getParameter("tabletype").equals("komentar")) {
            return doSaveComment(request);
        } else {
            return "";
        }
    }
    
    private String doSaveTaskDetails(HttpServletRequest request) throws SQLException, Exception {
        String out = "";
        con.Init();
        String idtask = (String) request.getSession().getAttribute("taskid");
        String namatask = request.getParameter("namatask");
        //$attachment = $_GET['attachment'];
        String deadline = request.getParameter("deadline");
        String listassignee = request.getParameter("listassignee");
        String listtag = request.getParameter("listtag");
        
        String assignees[] = listassignee.split(",");
        String tags[] = listtag.split(",");
        
        String query = "UPDATE task SET NAMA='"+namatask+"', Deadline='"+deadline+"' WHERE ID='"+idtask+"'";
        
        if (con.ExecuteUpdate(query)!=0)
        {
            out+= "Record task updated";
        }
        else 
        {
            out+= "Error updating record task ";
        }
        
        query = "DELETE FROM tags WHERE IDTask='"+idtask+"'";
        if (con.ExecuteUpdate(query)!=0)
        {
            out+= "Record tags deleted";
        }
        else 
        {
            out+= "Error deleting record tags ";
        }
        
        int i = 0;
        while (i < tags.length) {
            query = "INSERT INTO tags VALUES ('"+idtask+"','"+tags[i]+"')";
            if (con.ExecuteUpdate(query)!=0)
            {
                out+= "Record tags updated";
            }
            else 
            {
                out+= "Error updating record tags ";
            }
            i++;
        }
        
        query = "DELETE FROM assignee WHERE IDTask='"+idtask+"'";
        if (con.ExecuteUpdate(query)!=0)
        {
            out+= "Record assignee deleted";
        }
        else 
        {
            out+= "Error deleting record assignee ";
        }
        
        i = 0;
        while (i < assignees.length) {
            query = "INSERT INTO assignee VALUES ('"+idtask+"','"+assignees[i]+"')";
            if (con.ExecuteUpdate(query)!=0)
            {
                out+= "Record assignee updated";
            }
            else 
            {
                out+= "Error updating record assignee ";
            }
            i++;
        }
        con.Close();
        return out;
    }
    
    private String doSaveComment(HttpServletRequest request) throws SQLException, Exception {
        String out = "";
        con.Init();
        String idtask = (String) request.getSession().getAttribute("taskid");
        String iduser = (String) request.getSession().getAttribute("userid");
        String comment = request.getParameter("commentarea");
        String query = "SELECT * FROM komentar";
        resultSet = con.ExecuteQuery(query);
        String nextidstring = "";
        while (resultSet.next()) {
            nextidstring = resultSet.getString("ID");
        }
        int lastid;
        if (!nextidstring.equals("")) {
            nextidstring = nextidstring.substring(1);
            lastid = Integer.parseInt(nextidstring);
        } else {
            lastid = 0;
        }
        
        int nextid = lastid+1;
        if (nextid < 10) {
            nextidstring = "K00"+nextid;
        } else if (nextid < 100) {
            nextidstring = "K0"+nextid;
        } else {
            nextidstring = "K"+nextid;
        }
        query = "INSERT INTO komentar (ID,IDTask,IDUser,Waktu,Isi) VALUES ('"+nextidstring+"','"+idtask+"','"+iduser+"',now(),'"+comment+"')";

        if (con.ExecuteUpdate(query)!=0)
        {
            out+= "Record inserted";
        }
        else 
        {
            out+= "Error inserting record ";
        }
        con.Close();
        return out;
    }
    
    private String doDeleteComment(HttpServletRequest request) throws Exception {
        String out = "";
        con.Init();
        String idcomment = request.getParameter("commentid");
        String query = "DELETE FROM komentar WHERE ID='"+idcomment+"'";
        if (con.ExecuteUpdate(query)!=0)
        {
            out+= "Record komentar deleted";
        }
        else 
        {
            out+= "Error deleting record komentar ";
        }
        con.Close();
        return out;
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

}
