/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Ultilities.XML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thegu
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String xmlFile = "WEB-INF/studentsXML.xml";
    private boolean found = false;
    private String fullName = null;
    private final String invalidPage = "Invalid.html";
    private final String success = "Search.jsp";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String url = invalidPage;
        try {
            this.found = false;
            String realpath = getServletContext().getRealPath("/");
            String xmlPath = realpath + xmlFile;
            Document doc = XML.parseDOMfromfile(xmlPath);
            checkLogin(username, password, doc.getFirstChild());
            if (this.found == true) {
                url = success;
                HttpSession ses = request.getSession();
                ses.setAttribute("fullname", fullName);
                ses.setAttribute("dom", doc);
            }
            
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void checkLogin(String username, String password, Node node) {
        if (node == null || found) {
            return;
        }
        
        if (node.getNodeName().equals("student")) {
            NamedNodeMap attrs = node.getAttributes();
            String id = attrs.getNamedItem("id").getNodeValue();
            if (id.equals(username)) {
                NodeList list = node.getChildNodes();
                for (int i = 0; i < list.getLength(); i++) {
                    Node n = list.item(i);
                    if (n.getNodeName().equals("lastname")) {
                        fullName = n.getTextContent().trim();
                    } else if (n.getNodeName().equals("middlename")) {
                        fullName += " " + n.getTextContent().trim();
                    } else if (n.getNodeName().equals("firstname")) {
                        fullName += " " + n.getTextContent().trim();
                    } else if (n.getNodeName().equals("password")) {
                        String pass = n.getTextContent().trim();
                        if (!pass.equals(password)) {
                            break;
                        }
                    } else if (n.getNodeName().equals("status")) {
                        String str = n.getTextContent().trim();
                        if (!str.toLowerCase().equals("dropout")) {
                            this.found = true;
                            return;
                        }
                    }
                }
            }
        }
        
        int i = 0;
        NodeList children = node.getChildNodes();
        while (i < children.getLength()) {
            checkLogin(username, password, children.item(i++));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
