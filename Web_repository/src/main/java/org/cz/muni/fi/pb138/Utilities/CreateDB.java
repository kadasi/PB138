package org.cz.muni.fi.pb138.Utilities;

/**
 * Initialize All DB
 * @author Peter Kováč
 */
public class CreateDB {
    public static void main(String[] args) {
        DatabaseManager wsdlDB = new DatabaseManager(Filetype.WSDL);
        DatabaseManager xsdDB = new DatabaseManager(Filetype.XSD);
        DatabaseManager warXmlDB = new DatabaseManager(Filetype.WAR);
        
        wsdlDB.createCollection("wsdl");
        wsdlDB.createCollection("wsdl-metadata");
        
        xsdDB.createCollection("xsd");
        xsdDB.createCollection("xsd-metadata");
        
        warXmlDB.createCollection("war");
        warXmlDB.createCollection("war-metadata");
    }
}
