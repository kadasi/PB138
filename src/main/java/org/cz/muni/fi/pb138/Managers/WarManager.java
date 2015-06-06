package org.cz.muni.fi.pb138.Managers;

import java.util.List;
import org.cz.muni.fi.pb138.Documents.WAR;

/**
 * Interface with work with WAR archives
 * @author
 */
public interface WarManager {
    
    /**
     * Saves web.XML into database
     * @param war is war archive
     */
    public void createWarArchive(WAR war);
    
    /**
     * Gets web.XML from database
     * @param id entity id
     * @return single WAR
     */
    public WAR getWarArchive(Long id);  
    
    /**
     * Gets all Wars from database.
     * @return All Wars from database as String
     */
    public List<WAR> getAllArchives();
    
    /**
     * Finds War archives by meta data
     * @param filterName is name of filter
     * @return List of WSDLDoc
     */
    public List<WAR> findWarByData(String atrName, String searchTag);
       
    /**
     * Simple ID generator.
     * @return new unique id
     */
    public Long getNewId();
}
