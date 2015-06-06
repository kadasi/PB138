package org.cz.muni.fi.pb138.Managers;

import java.util.List;
import org.cz.muni.fi.pb138.Documents.WSDL;

/**
 * Interface with work with WSDLs
 * @author
 */
public interface WSDLManager {
    
    /**
     * Saves WSDL into database
     * @param wsdl
     */
    public void createWSDL(WSDL wsdl);
    
    /**
     * Gets WSDL from database
     * @param id is id in DB
     * @return WSDL file as String
     */
    public WSDL getWSDL(Long id);
    
    /**
     * Gets all WSDLs from database.
     * @return all WSDLs from database as String
     */
    public List<WSDL> getAllWSDLs(); 
    
    /**
     * Finds WSDLs by meta data.
     * @param definitonsName
     * @return All WSDLs satisfactory search.
     */
    public List<WSDL> findWSDLByData(String definitonsName, String searchTag);

    /** 
     * Simple ID generator.
     * @return new new unique id
     */
    public Long getNewId();
    
}
