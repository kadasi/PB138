package org.cz.muni.fi.pb138.Documents;

/**
 *
 * @author
 *
 * This class represents XSD Entity.
 */
public class XSD {

    private Long id;
    private String timestamp;
    private String document;
    private String fileName;
    private String extract;

    /**
     * Getter for Id.
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Setter for id.
     * @param Long id for set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for XSD document.
     * @return XML as String
     */
    public String getDocument() {
        return document;
    }

    /**
     * Setter for XSD document.
     * @param String document
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * Filename getter.
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Filename setter.
     * @param String filename.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Extract getter.
     * @return extract
     */
    public String getExtract() {
        return extract;
    }

    /**
     * Setter for XSD extract.
     * @param String extract.
     */
    public void setExtract(String extract) {
        this.extract = extract;
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
     * @param String timestamp.
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
        final XSD other = (XSD) obj;
        if (this.id == null || !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * XML to String
     * @return text description of this object
     */
    @Override
    public String toString() {
        return "Type: XSD\n" + "FileName=" + fileName + "\nId=" + id + "\nVersion=" + timestamp + "\nDocument: \n\n" + document + "\n\n\nExtract: \n\n" + extract;
    }
}
