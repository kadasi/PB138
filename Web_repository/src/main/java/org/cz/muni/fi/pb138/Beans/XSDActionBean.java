package org.cz.muni.fi.pb138.Beans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.cz.muni.fi.pb138.Managers.XSDManager;
import org.cz.muni.fi.pb138.Documents.XSD;
import org.cz.muni.fi.pb138.ManagersImpl.XSDManagerImpl;
import org.cz.muni.fi.pb138.MetadataParsers.XSDParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Filetype;
import org.cz.muni.fi.pb138.Utilities.Util;

/**
 * Action Bean for XSD
 * @author Peter Lipcak
 */
@UrlBinding("/xsd/{$event}")
public class XSDActionBean implements ActionBean {

    private FileBean xsdInput;
    private ActionBeanContext context;
    private DatabaseManager dm = new DatabaseManager(Filetype.XSD);
    private XSDManager manager = new XSDManagerImpl(dm);
    private XSDParser xsdParser = new XSDParser();
    private XSD result = new XSD();
    private List<XSD> resultList = new ArrayList<XSD>();
    
    /**
     * Get ActionBeanContext
     * @return ActionBeanContext
     */
    @Override
    public ActionBeanContext getContext() { return context; }
    
    /**
     * Set context
     * @param context ActionBeanContext
     */
    @Override
    public void setContext(ActionBeanContext context) { this.context = context; }


    /**
     * Get FileBean from XSD input
     * @return FileBean
     */
    public FileBean getxsdInput() {
        return xsdInput;
    }

    /**
     * Return XSD List
     * @return list of XSDs
     */
    public List<XSD> getResultList() {
        return resultList;
    }

    /**
     * Set XSD Input
     * @param xsdInput FileBean
     */
    public void setxsdInput(FileBean xsdInput) {
        this.xsdInput = xsdInput;
    }
    
    /**
     * XSD upload
     * @return resolution
     */
    public Resolution xsdUpload() {
        try {
            File toFile = new File(System.getProperty("user.home")+File.separator+xsdInput.getFileName());
            String extension = xsdInput.getFileName().substring(xsdInput.getFileName().lastIndexOf(".") + 1, xsdInput.getFileName().length());
            if(!extension.equals("xsd")) {
                toFile.delete();
                xsdInput.delete();
                return new ForwardResolution("/wrongFile.jsp"); 
            }
            xsdInput.save(toFile);
            String content = Util.readFile(toFile);

            XSD xsd = new XSD();
            xsd.setId(manager.getNewId());
            xsd.setTimestamp(Util.getTimeStamp());
            xsd.setFileName(xsdInput.getFileName());
            xsd.setDocument(Util.stripXMLHeader(content));
            xsd.setExtract(Util.docToString(xsdParser.xsdExtract(Util.stringToDoc(content))));
            manager.createXSD(xsd);
            
            toFile.delete();
            xsdInput.delete();
        } catch (IOException ex) {
            Logger.getLogger(XSDActionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            return new ForwardResolution("/wrongFile.jsp");
        }
        return new ForwardResolution("/showXSD.jsp");
    }
    
    /**
     * Get XSD document
     * @return XSD Document
     */
    public XSD getDocument() {
        XSD xsd;
        xsd = manager.getXSD(result.getId());
        xsd.setDocument(Util.format(xsd.getDocument()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        xsd.setExtract(Util.format(xsd.getExtract()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        return xsd;
    }
    
    /**
     * Get id 
     * @returnid as String 
     */
    public String getId(){
        return result.getId().toString();
    }
    
    /**
     * Search by id
     * @return resolution
     */
    public Resolution searchId() {
        try{
        Long searchId = Long.parseLong(context.getRequest().getParameter("searchId"));
        if(searchId >= 0 && searchId < manager.getNewId()) {
            result = manager.getXSD(searchId);
            return new ForwardResolution("/showSingleXSD.jsp");
        }
        return new ForwardResolution("/wrongSearch.jsp");
        } catch (NumberFormatException ex) {
            return new ForwardResolution("/wrongSearch.jsp");
        }
    }
    
    /**
     * Get XSDs
     * @return list of XSDs
     */
    public List<XSD> getXSDs(){
        return manager.getAllXSDs() ;
    }
    
    /**
     * Search data
     * @return resolution
     */
    public Resolution searchData() {
        String searchData = context.getRequest().getParameter("dataInput");
        String radioSelected = context.getRequest().getParameter("radioButton");
        if(radioSelected == null)return new ForwardResolution("/wrongSearch.jsp");
        resultList = manager.findXSDByData(searchData,radioSelected);
        return new ForwardResolution("/showSearchXSD.jsp");
    }

}
