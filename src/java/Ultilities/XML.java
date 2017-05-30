/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ultilities;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author thegu
 */
public class XML implements Serializable {
    public static Document parseDOMfromfile(String xmlPath) {
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(xmlPath);
            
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static XPath getXPath() {
        XPathFactory fac = XPathFactory.newInstance();
        XPath path = fac.newXPath();
        return path;
    }
    
    public static String getXPathValue (String expression, Node node) throws XPathExpressionException {
        XPath path = getXPath();
        return ((String)path.evaluate(expression, node, XPathConstants.STRING)).trim();
    }
    
    public static void transformDOMtoStream(Node node, String xmlPath) throws TransformerConfigurationException, TransformerException {
        Source src = new DOMSource(node);
        File f = new File(xmlPath);
        Result result = new StreamResult(f);
        
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transform = factory.newTransformer();
        transform.transform(src, result);
    }
    
    public static Element createElement(String name, Document doc, Map<String, String> attributes, String textContext) {
        if (doc != null) {
            Element result = doc.createElement(name);
            
            if (attributes != null) {
                for (Map.Entry<String, String> entry: attributes.entrySet()) {
                    result.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            if (textContext != null) {
                result.setTextContent(textContext);
            }
            
            return result;
        }
        return null;
    }
}
