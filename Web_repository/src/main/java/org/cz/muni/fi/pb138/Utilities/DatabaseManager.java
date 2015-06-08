package org.cz.muni.fi.pb138.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.Add;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.Set;
import org.basex.core.cmd.XQuery;
/**
 * Class for working with BaseX DB
 * @author Peter Kováč
 */
public class DatabaseManager {
    private String DBPath;
    public static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
  
    /**
     * Database manager
     * @param fileType fileType
     */
     public DatabaseManager(Filetype fileType) {
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(System.getProperty("user.home")+File.separator+"webrep.log", true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        logger.addHandler(new StreamHandler(fs, new SimpleFormatter()));
        
        if((Filetype.WSDL.equals(fileType) && !Filetype.XSD.equals(fileType) && !Filetype.WAR.equals(fileType))
                ||(!Filetype.WSDL.equals(fileType) && Filetype.XSD.equals(fileType) && !Filetype.WAR.equals(fileType))
                ||(!Filetype.WSDL.equals(fileType) && !Filetype.XSD.equals(fileType) && Filetype.WAR.equals(fileType))) {
            String path = System.getProperty("user.home")+File.separator+"BaseXDB"+File.separator;
            File basexdbDir = new File(path);
            if(!basexdbDir.exists()) {
                basexdbDir.mkdir();
            }
            
            File yourFile = new File(path+fileType+"Database");
            if(yourFile.exists()) {
                
                this.DBPath = path+fileType+"Database";
            }
            else {
                logger.info("mkdir:"+path+fileType+"Database");
                yourFile.mkdir();
                this.DBPath = path+fileType+"Database";
            }
        }
        else {
            logger.info(fileType + " is not used baseX database");
            throw new IllegalArgumentException(fileType + " is not used baseX database");
        }
    }
    
     /**
      * Set db path
      * @param path 
      */
    public void setDBPath(String path) {
        this.DBPath = path;
    }
    
   /**(WarArchive war
    * Adds a xml file to a collection
    * 
    * @param collection collection name
    * @param name name of the XML file (without extension)
    * @param xml well-formed XML string
    */
    public void addXML(String collection, String name, String xml) {
        Context context = new Context();
        try {
            new Set("dbpath", this.DBPath).execute(context);
            new Open(collection).execute(context);
            new Add(name, xml).execute(context);
        } catch (BaseXException ex) {
            logger.log(Level.SEVERE, "DBError when adding XML file");
        }
        context.close();
    }
    
    /**
    * Runs a XPath or XQuery query on a collection
    * @param query the query as a String
    * @return String
    * @throws BaseXException on database error
    */
    public String queryCollection(String collection, String query) {
        try {
            String toReturn;
            Context context = new Context();
            new Set("dbpath", this.DBPath).execute(context);
            new Open(collection).execute(context);
            toReturn = new XQuery(query).execute(context).toString();
            context.close();
            return toReturn;
        } catch (BaseXException ex) {
            logger.log(Level.SEVERE, "DBError when collecting query");
        }
        return null;
    }
    
    /**
    * Creates a collection
    * @param collection name of the collection
    */
    public void createCollection(String collection) {
        Context context = new Context();
        try {
            new Set("dbpath", this.DBPath).execute(context);
            new CreateDB(collection).execute(context);
        } catch (BaseXException ex) {
            logger.log(Level.SEVERE, "DBError when creating collection.");
        }
        context.close();
    }

}
