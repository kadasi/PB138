package org.cz.muni.fi.pb138.Documents;
/**
 *
 * @author Peter Kováč, Peter Lipčák, Jakub Kadáši
 
 This class represents WAR.
 */
public class WAR {

    private Long id;
    private String timestamp;
    private String webXml;
    private String extract;
    private String fileName;

    /**
     * Get web.XML as String
     * @return extracted web.XML
     */
    public String getWebXml() {
        return webXml;
    }

    /**
     * Extract data from XML
     * @returns extract from web.XML.
     */
    public String getExtract() {
        return extract;
    }

    /**
     * Sets extracted data from web.XML into attribute.
     * @param String extract
     */
    public void setExtract(String extract) {
        this.extract = extract;
    }

    /**
     * Sets extracted file into attribute
     * @param String webXml
     */
    public void setWebXml(String webXml) {
        this.webXml = webXml;
    }

    /**
     * Getter for  ID
     * @return Entity ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Setter for Id.
     * @param id for set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for FileName.
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * Setter for filename.
     * @param fileName filename for set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Version getter.
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }
       
    /**
     * Version setter.
     * @param timestamp for set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Generate Hash code
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    /**
     * compares two objects of this types
     * @param obj to compare
     * @return true if they are the same
     * @return false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WAR other = (WAR) obj;
        if (this.id == null || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * XML to string
     * @return text description of this object
     */
    @Override
    public String toString() {
        return "Type: web.xml\n" + "FileName=" + fileName + "\nId=" + id + "\nVersion=" + timestamp + "\nDocument: \n\n" + webXml + "\n\n\nExtract: \n\n" + extract;
    }


 
}
