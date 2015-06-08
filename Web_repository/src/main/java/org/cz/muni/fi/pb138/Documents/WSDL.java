package org.cz.muni.fi.pb138.Documents;

/**
 * @author Peter Kováč, Peter Lipčák, Jakub Kadáši
 * This class represents WSDL document entity
 */
public class WSDL {

    private Long id;
    private String timestamp;
    private String document;
    private String extract;
    private String fileName;

    /**
     * Method getFileName returns name of the file.
     * @return File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Method sets FileName to specified string.
     * @param String fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get id
     * @return entity Id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id to specified value.
     * @param Long id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns XML as dom.Document
     * @return whole XML document.
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets document in entity
     * @param String document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /** 
     * Extract data from XML
     * @return extracted data.
     */
    public String getExtract() {
        return extract;
    }

    /**
     * Sets extracted data into attribute.
     * @param String extract
     */
    public void setExtract(String extract) {
        this.extract = extract;
    }
    
    /**
     * @return timestamp representing version.
     */
    public String getTimestamp() {
        return timestamp;
    }
    
    /**
     * Sets version of document.
     * @param timestamp timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get hash code
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
        final WSDL other = (WSDL) obj;
        if (this.id == null || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Return string from XML
     * @return text description of this object
     */
    @Override
    public String toString() {
        return "Type: WSDL\n" + "FileName=" + fileName + "\nId=" + id + "\nVersion=" + timestamp + "\nDocument: \n\n" + document + "\n\n\nExtract: \n\n" + extract;
    }
}
