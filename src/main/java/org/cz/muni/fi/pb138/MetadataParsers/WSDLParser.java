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
 * Class for parsing WSDL
 * @author
 */
public class WSDLParser {    
    
    /**
     * Create extract document from WSDL 
     * @param doc original WSDL as dom.Document
     * @return Extracted WSDL as dom.Document
     * @throws ParserConfigurationException 
     */
    public Document wsdlExtract(Document doc) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document output;
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            output = builder.newDocument(); 

            Element root = (Element) output.createElement("operations_and_messages"); 
            output.appendChild(root);
            
            NodeList operationList = doc.getElementsByTagName("operation");
            NodeList messageList = doc.getElementsByTagName("message");
            
            for (int i = 0; i < operationList.getLength(); i++) {
                if (operationList.item(i) instanceof Element) {
                    Element operationElement = (Element) operationList.item(i);
                    Node nodeToMove = output.importNode(operationElement, true);
                    root.appendChild(nodeToMove);
                }
            }      
            return output;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WSDLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}