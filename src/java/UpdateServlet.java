/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Ultilities.XML;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author thegu
 */
public class UpdateServlet extends HttpServlet {

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
        
        String id = request.getParameter("id");
        String search = request.getParameter("txtSearch");
        String sClass = request.getParameter("class");
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
                
                String expression = "//student[@id='" + id + "']";
                XPath path = XML.getXPath();
                Node node = (Node)path.evaluate(expression, doc, XPathConstants.NODE);
                if (node != null) {
                    NamedNodeMap attrs = node.getAttributes();
                    attrs.getNamedItem("class").setNodeValue(sClass);
                    Node sStatus = (Node)path.evaluate("status", node, XPathConstants.NODE);
                    sStatus.setTextContent(status);
                    
                    String xmlPath = getServletContext().getRealPath("/") + xmlFile;
                    XML.transformDOMtoStream(doc, xmlPath);
                }
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String url = "ProcessServlet?btAction=search&txtSearch=" + search;
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
