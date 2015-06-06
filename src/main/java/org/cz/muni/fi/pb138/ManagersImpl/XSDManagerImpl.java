package org.cz.muni.fi.pb138.ManagersImpl;

import java.util.ArrayList;
import java.util.List;
import org.cz.muni.fi.pb138.Managers.XSDManager;
import org.cz.muni.fi.pb138.Documents.XSD;
import org.cz.muni.fi.pb138.MetadataParsers.XSDParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Util;

/**
 * Implementation of XSDManager
 * 
 * @author
 */
public class XSDManagerImpl implements XSDManager {
    private DatabaseManager dm;

    public XSDManagerImpl(DatabaseManager dm) {
        this.dm = dm;
    }
    
    
    @Override
    public Long getNewId() {
        String c = this.dm.queryCollection("xsd","count(collection('xsd')/xsd)");
        if(c.equals("0")) {
            return new Long(0);
        }
        else {
            return new Long(c);
        }
    }

    @Override
    public void createXSD(XSD xsd) {
        //add whole XML to database
        String wholeXml = "<xsd id='"+xsd.getId().toString()+"'>"+xsd.getDocument()+"</xsd>";
        this.dm.addXML("xsd", xsd.getId().toString(),wholeXml);
        
        
        //add METADATA for xml to database
        String metadataXml = "<metadata xsd_id='"+xsd.getId().toString()+"' date='"+xsd.getTimestamp()
                +"' fileName='"+xsd.getFileName()+"'>"+xsd.getExtract()+"</metadata>";
        this.dm.addXML("xsd-metadata", xsd.getId().toString(),metadataXml);
    }

    @Override
    public XSD getXSD(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        XSD schema = new XSD();
        XSDParser xsdParser = new XSDParser();
        schema.setId(id);
        schema.setDocument(Util.format(this.dm.queryCollection("xsd",
                  "collection('xsd')/xsd[@id='" + id.toString() + "']/xs:schema")));
        schema.setTimestamp(this.dm.queryCollection("xsd-metadata",
                  " for $xsd in collection('xsd-metadata')/metadata[@xsd_id='" + id.toString() + "']"
                + " return data($xsd/@date)"));
        schema.setFileName(this.dm.queryCollection("xsd-metadata",
                  " for $xsd in collection('xsd-metadata')/metadata[@xsd_id='" + id.toString() + "']"
                + " return data($xsd/@fileName)"));
        schema.setExtract(Util.format(this.dm.queryCollection("xsd-metadata", "collection('xsd-metadata')/metadata[@xsd_id='" + id.toString() + "']")));

        return schema;
    }

    @Override
    public List<XSD> getAllXSDs() {
        List<XSD> output = new ArrayList<XSD>();
        String c = this.dm.queryCollection("xsd","count(collection('xsd')/xsd)");
        for(int i=0;i<new Integer(c);i++) {
            output.add(this.getXSD(new Long(i)));
        }
        return output;
    }

    @Override
    public List<XSD> findXSDByData(String s, String searchTag) {
        List<XSD> output = new ArrayList<XSD>();
        String query =  this.dm.queryCollection("xsd-metadata", "declare namespace xs='http://www.w3.org/2001/XMLSchema';"
                + " distinct-values( for $meta in collection('xsd-metadata')/metadata "
                + " for $node in $meta//" + searchTag
                + " where fn:contains($node/@name,'"+s+"')"
                + " return distinct-values($meta/@xsd_id))");
        
        if(query.equals("")) {
            return output;
        }
        String strarray[] = query.split(" ");
        int intarray[] = new int[strarray.length];
        for (int i=0; i < intarray.length; i++) {
            intarray[i] = Integer.parseInt(strarray[i]);
        }
        for (int x : intarray) {
            output.add(this.getXSD(new Long(x)));
        }
        return output;
    }
}
