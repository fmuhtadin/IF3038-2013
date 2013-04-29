/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Asus
 */
public class daftartask extends HttpServlet {
    
    DBConnector dbc = new DBConnector();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet daftartask</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet daftartask at " + request.getParameter("kategori") + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        String kategori = request.getParameter("tkategori");
        String id = (String) session.getAttribute("userid");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        session.setAttribute("kategori", kategori);
        try {
            dbc.Init();
            ResultSet rs = dbc.ExecuteQuery("SELECT * FROM task WHERE Category='"+kategori+"'");
            
            while (rs.next()) {
                out.println(rs.getString(3));
                out.println("<br>");
                out.println(rs.getString(6));
                out.println("<br>");
                out.println("<form name='hiddenidtask"+rs.getString("ID")+"' id='hiddenidtask"+rs.getString("ID")+"' action='rincian.jsp' method='post'>\n");
                out.println("     <input type='hidden' name='idtask' value='"+rs.getString("ID")+"' />\n");
                out.println("<input type='submit' value='Lihat Rincian' />\n");
                out.println("</form></br>\n");
                if (rs.getString(2).equals(id)){
                    out.println("<form name='hiddenidtask2"+rs.getString("ID")+"' id='hiddenidtask2"+rs.getString("ID")+"' onsubmit=\"return deleteTask('"+rs.getString("ID")+"')\" method='post'>\n");
                    out.println("     <input type='hidden' id='idtask"+rs.getString("ID")+"' name='idtask' value='"+rs.getString("ID")+"' />\n");
                    out.println("<input type='submit' value='Hapus Task' />\n");
                    out.println("</form></br>\n");
                    out.println("<br>");    
                }else {
                    out.println("<br>");
                }
            }
            dbc.Close();  
        } catch(Exception e){
        out.println(e);
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
