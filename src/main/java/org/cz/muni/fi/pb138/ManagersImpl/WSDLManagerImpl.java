package org.cz.muni.fi.pb138.ManagersImpl;

import java.util.ArrayList;
import java.util.List;
import org.cz.muni.fi.pb138.Managers.WSDLManager;
import org.cz.muni.fi.pb138.Documents.WSDL;
import org.cz.muni.fi.pb138.MetadataParsers.WSDLParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Util;
/**
 * Implementation of WSDL Manager
 *
 * @author
 */
public class WSDLManagerImpl implements WSDLManager {
    private DatabaseManager dm;

    /*
     * Constructor. 
     * @param DatabaseManager dm
     */
    public WSDLManagerImpl(DatabaseManager dm) {
        this.dm = dm;
    }
   
    
    @Override
    public void createWSDL(WSDL wsdl) {
        //add whole XML to database
        String wholeXml = "<wsdl id='"+wsdl.getId().toString()+"'>"+wsdl.getDocument()+"</wsdl>";
        this.dm.addXML("wsdl", wsdl.getId().toString(),wholeXml);
        
        
        //add METADATA for xml to database
        String metadataXml = "<metadata wsdl_id='"+wsdl.getId().toString()+"' date='"+wsdl.getTimestamp()
                +"' fileName='"+wsdl.getFileName()+"'>"+wsdl.getExtract()+"</metadata>";
        this.dm.addXML("wsdl-metadata", wsdl.getId().toString(),metadataXml);
    }
    
    @Override
    public Long getNewId() {
        String c = this.dm.queryCollection("wsdl","count(collection('wsdl')/wsdl)");
        if(c.equals("0")) {
            return new Long(0);
        }
        else {
            return new Long(c);
        }
    }

    @Override
    public WSDL getWSDL(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        WSDL wsdl = new WSDL();
        WSDLParser wsdlParser = new WSDLParser();
        wsdl.setId(id);
        wsdl.setDocument(this.dm.queryCollection("wsdl", "collection('wsdl')/wsdl[@id='" + id.toString() + "']"));
        if(wsdl.getDocument().equals("")) {
            wsdl.setDocument(this.dm.queryCollection("wsdl", "collection('wsdl')/wsdl[@id='" + id.toString() + "']"));
        }
        wsdl.setFileName(this.dm.queryCollection("wsdl-metadata"," "
                + " for $meta in collection('wsdl-metadata')/metadata[@wsdl_id='" + id.toString() + "']"
                + " return data($meta/@fileName)"));
        wsdl.setTimestamp(this.dm.queryCollection("wsdl-metadata"," "
                + " for $meta in collection('wsdl-metadata')/metadata[@wsdl_id='" + id.toString() + "']"
                + " return data($meta/@date)"));
        wsdl.setExtract(Util.format(this.dm.queryCollection("wsdl-metadata", "collection('wsdl-metadata')/metadata[@wsdl_id='" + id.toString() + "']")));

        return wsdl;
    }

    @Override
    public List<WSDL> getAllWSDLs() {
        List<WSDL> output = new ArrayList<WSDL>();
        String c = this.dm.queryCollection("wsdl","count(collection('wsdl')/wsdl)");
        for(int i=0;i<new Integer(c);i++) {
            output.add(this.getWSDL(new Long(i)));
        }
        return output;
    }
   
    @Override
    public List<WSDL> findWSDLByData(String definitonsName, String searchTag){
        List<WSDL> output = new ArrayList<WSDL>();
        String query = this.dm.queryCollection("wsdl-metadata",
                  " distinct-values( for $wsdl in collection('wsdl-metadata')/metadata "
                + " for $node in $wsdl//" + searchTag.split("/")[0]
                + " where fn:contains(data($node/" + searchTag.split("/")[1] + "),'"+definitonsName+"')"
                + " return distinct-values($wsdl/@wsdl_id))");
        if(query.equals("")) {
            return output;
        }
        String strarray[] = query.split(" ");
        int intarray[] = new int[strarray.length];
        for (int i=0; i < intarray.length; i++) {
            intarray[i] = Integer.parseInt(strarray[i]);
        }
        for (int x : intarray) {
            output.add(this.getWSDL(new Long(x)));
        }
        return output;
    }
}
