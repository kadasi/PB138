package org.cz.muni.fi.pb138.ManagersImpl;

import java.util.ArrayList;
import java.util.List;
import org.cz.muni.fi.pb138.Managers.WarManager;
import org.cz.muni.fi.pb138.Documents.WAR;
import org.cz.muni.fi.pb138.MetadataParsers.WARParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Util;
/**
 * Implementation of WarManager
 * @author Peter Kováč
 */
public class WarManagerImpl implements WarManager {
    private DatabaseManager dm;
    
    /*
     * Constructor.
     * @param DatabaseManager dm
     */
    public WarManagerImpl(DatabaseManager dm) {
        this.dm = dm;
    }
    
    
    @Override
    public void createWarArchive(WAR war) {
        //add whole XML to database
        String wholeXml = "<war id='"+war.getId().toString()+"'>"+war.getWebXml()+"</war>";
        this.dm.addXML("war", war.getId().toString(),wholeXml);
        
        
        //add METADATA for xml to database
        String metadataXml = "<metadata war_id='"+war.getId().toString()+"' date='"+war.getTimestamp()
                +"' fileName='"+war.getFileName()+"'>"+war.getExtract()+"</metadata>";
        this.dm.addXML("war-metadata", war.getId().toString(),metadataXml);

    }
    
    @Override
    public Long getNewId() {
        String c = this.dm.queryCollection("war","count(collection('war')/war)");
        if(c.equals("0")) {
            return new Long(0);
        }
        else {
            return new Long(c);
        }
    }
    
    @Override
    public WAR getWarArchive(Long id){
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        WAR war = new WAR();
        WARParser webXmlParser = new WARParser();
        war.setId(id);
        war.setTimestamp(this.dm.queryCollection("war-metadata"," "
                + " for $meta in collection('war-metadata')/metadata[@war_id='" + id.toString() + "']"
                + " return data($meta/@date)"));
        war.setFileName(this.dm.queryCollection("war-metadata"," "
                + " for $meta in collection('war-metadata')/metadata[@war_id='" + id.toString() + "']"
                + " return data($meta/@fileName)"));
        war.setWebXml(Util.format(this.dm.queryCollection("war","collection('war')/war[@id='"+id.toString()+"']")));
        war.setExtract(Util.format(this.dm.queryCollection("war-metadata", "collection('war-metadata')/metadata[@war_id='" + id.toString() + "']")));
        return war;
    }
    
    @Override
    public List<WAR> getAllArchives() {
        List<WAR> output = new ArrayList<WAR>();
        String c = this.dm.queryCollection("war","count(collection('war')/war)");
         for(int i=0;i<new Integer(c);i++) { 
             output.add(this.getWarArchive(new Long(i)));
         }
        return output;
    }
     
    @Override
    public List<WAR> findWarByData(String atrName, String searchTag) {
        List<WAR> output = new ArrayList<WAR>();
        String query = this.dm.queryCollection("war-metadata",
                  " distinct-values(for $meta in collection('war-metadata')/metadata "
                + " for $node in $meta/" + searchTag
                + " where fn:contains(data($node),'"+atrName+"')"
                + " return distinct-values($meta/@war_id))");
        
        if(query.equals("")) {
            return output;
        }
        String strarray[] = query.split(" ");
        int intarray[] = new int[strarray.length];
        for (int i=0; i < intarray.length; i++) {
            intarray[i] = Integer.parseInt(strarray[i]);
        }
        for (int x : intarray) {
            output.add(this.getWarArchive(new Long(x)));
        }
        return output;
    }
}
