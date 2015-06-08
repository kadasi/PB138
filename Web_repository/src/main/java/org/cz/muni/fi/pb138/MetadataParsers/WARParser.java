package org.cz.muni.fi.pb138.MetadataParsers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class for parsing web.XML from WAR archives
 * @author Jakub Kadasi
 */
public class WARParser {
    /**
    * Create metadata document from web.XML 
    * @param doc original web.XML as dom.Document
    * @return Extracted web.XML as dom.Document
    * @throws ParserConfigurationException 
    */
    public Document webXMLExtract(Document doc) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document output;
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            output = builder.newDocument(); 

            Element root = (Element) output.createElement("filters_servlets_and_listeners"); 
            output.appendChild(root);
            
            NodeList filterList = doc.getElementsByTagName("filter");
            NodeList listenerList = doc.getElementsByTagName("listener"); 
            NodeList servletList = doc.getElementsByTagName("servlet"); 
            
            for (int i = 0; i < filterList.getLength(); i++) {
                if (filterList.item(i) instanceof Element) {
                    Element operationElement = (Element) filterList.item(i);
                    Node nodeToMove = output.importNode(operationElement, true);
                    root.appendChild(nodeToMove);
                }
            }       
                   
            for (int i = 0; i <  listenerList.getLength(); i++) {
                if (listenerList.item(i) instanceof Element) {
                    Element messageElement = (Element) listenerList.item(i);
                    Node nodeToMove = output.importNode(messageElement, true);
                    root.appendChild(nodeToMove);
                }
            }
            
            for (int i = 0; i <  servletList.getLength(); i++) {
                if (servletList.item(i) instanceof Element) {
                    Element messageElement = (Element) servletList.item(i);
                    Node nodeToMove = output.importNode(messageElement, true);
                    root.appendChild(nodeToMove);
                }
            }
            return output;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WARParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
