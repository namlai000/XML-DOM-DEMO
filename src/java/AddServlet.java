/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Ultilities.XML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thegu
 */
public class AddServlet extends HttpServlet {

    private final String xmlFile = "WEB-INF/studentsXML.xml";

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
        
        String id = request.getParameter("username");
        String password = request.getParameter("password");
        String lastname = request.getParameter("lastname");
        String middlename = request.getParameter("middlename");
        String firstname = request.getParameter("firstname");
        String sClass = request.getParameter("class");
        String sex = (request.getParameter("sex") == null ? "0": "1");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        try {
            HttpSession ses = request.getSession(false);
            if (ses != null) {
                Document doc = (Document)ses.getAttribute("doc");
                if (doc == null) {
                    String realpath = getServletContext().getRealPath("/");
                    String xmlPath = realpath + xmlFile;
                    doc = XML.parseDOMfromfile(xmlPath);
                }
                
                Map<String, String> attributes = new HashMap<String, String>();
                attributes.put("id", id);
                attributes.put("class", sClass);
                
                Element student = XML.createElement("student", doc, attributes, null);
                
                Element last = XML.createElement("lastname", doc, null, lastname);
                Element first = XML.createElement("firstname", doc, null, firstname);
                Element mid = XML.createElement("middlename", doc, null, middlename);
                Element mSex = XML.createElement("sex", doc, null, sex);
                Element pass = XML.createElement("password", doc, null, password);
                Element addr = XML.createElement("address", doc, null, address);
                Element stat = XML.createElement("status", doc, null, status);
             
                student.appendChild(last);
                student.appendChild(mid);
                student.appendChild(first);
                student.appendChild(mSex);
                student.appendChild(pass);
                student.appendChild(addr);
                student.appendChild(stat);
                
                NodeList students = doc.getElementsByTagName("students");
                students.item(0).appendChild(student);
                
                String xmlPath = getServletContext().getRealPath("/") + xmlFile;
                XML.transformDOMtoStream(doc, xmlPath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        String url = "ProcessServlet?btAction=search";
        response.sendRedirect(url);
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
