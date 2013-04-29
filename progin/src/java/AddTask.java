/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class AddTask extends HttpServlet {

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
    public int ID;
    
    @Override
    public void init() {
        con = new DBConnector();
    }
    
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            con.Init();
            
            String taskname = request.getParameter("namatask");
            String deadline0 = request.getParameter("deadline");
            String deadline = "20"+deadline0.substring(0,2)+"-"+deadline0.substring(3,5)+"-"+deadline0.substring(6,8)+" 00:00:00";
            String assignee = request.getParameter("assignee");
            String tag = request.getParameter("tag");
            String user = (String) request.getSession().getAttribute("userid");
            String cat = (String) request.getSession().getAttribute("kategori");
            
            ResultSet set = con.ExecuteQuery("SELECT * FROM task");
            String lastid = "";
            int lastidnum = 0;
            while (set.next()){
                lastid = set.getString("ID");
            }
            if (!lastid.equals("")) {
                lastidnum = Integer.parseInt(lastid.substring(1));
            }
            ID = lastidnum+1;
            String nextid;
            if (ID < 10) {
                nextid = "T00"+ID;
            } else if (ID < 100) {
                nextid = "T0"+ID;
            } else {
                nextid = "T"+ID;
            }
            
            if (con.ExecuteUpdate("INSERT INTO task (ID,IDCreator,Nama,Category,Status,Deadline) VALUES ('"+nextid+"','"+user+"','"+taskname+"','"+cat+"',0,'"+deadline+"')")!=0) {
                
            }
            
            String[] tags = tag.split(",");
            for( int i = 0; i < tags.length; i++ )  
            {  
                if (con.ExecuteUpdate("INSERT INTO tags (IDTask,Tag) VALUES ('"+nextid+"','"+tags[i]+"')")!=0) {
                    
                }
            }  
            
            String[] assignees = assignee.split(",");
            for( int i = 0; i < assignees.length; i++ )  
            {  
                if(con.ExecuteUpdate("INSERT INTO assignee (IDtask,IDUser) VALUES ('"+nextid+"','"+assignees[i]+"')")!=0) {
                    
                }
            } 

            //out.print("Tugas baru telah disimpan");

            con.Close();
            response.sendRedirect("Dashboard.jsp");
        } finally {            
            out.close();
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddTask.class.getName()).log(Level.SEVERE, null, ex);
        }
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
