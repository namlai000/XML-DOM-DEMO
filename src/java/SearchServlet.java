/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Student;
import Ultilities.XML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author thegu
 */
public class SearchServlet extends HttpServlet {

    private final String xmlFile = "WEB-INF/studentsXML.xml";
    private final String searchPage = "Search.jsp";

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
        
        String search = request.getParameter("txtSearch");
        try {
            HttpSession ses = request.getSession(false);
            if (ses != null) {
                Document doc = (Document)ses.getAttribute("doc");
                if (doc == null) {
                    String realpath = getServletContext().getRealPath("/");
                    String xmlPath = realpath + xmlFile;
                    doc = XML.parseDOMfromfile(xmlPath);
                }
                
                String exp = "//student[contains(address,'" + search + "')]";
                XPath path = XML.getXPath();
                NodeList list = (NodeList)path.evaluate(exp, doc, XPathConstants.NODESET);
                
                List<Student> studentList = null;
                for (int i = 0 ; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    String id = XML.getXPathValue("@id", node);
                    String sClass = XML.getXPathValue("@class", node);
                    String firstname = XML.getXPathValue("firstname", node);
                    String middlename = XML.getXPathValue("middlename", node);
                    String lastname = XML.getXPathValue("lastname", node);
                    String sex = XML.getXPathValue("sex", node);
                    String password = XML.getXPathValue("password", node);
                    String address = XML.getXPathValue("address", node);
                    String status = XML.getXPathValue("status", node);
                    
                    if (studentList == null) studentList = new ArrayList<>();
                    Student student = new Student(id, sClass, lastname, middlename, firstname, sex, password, address, status);
                    studentList.add(student);
                }
                
                request.setAttribute("result", studentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(searchPage);
        rd.forward(request, response);
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
