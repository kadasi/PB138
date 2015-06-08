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
import org.cz.muni.fi.pb138.Managers.WSDLManager;
import org.cz.muni.fi.pb138.Documents.WSDL;
import org.cz.muni.fi.pb138.ManagersImpl.WSDLManagerImpl;
import org.cz.muni.fi.pb138.MetadataParsers.WSDLParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Filetype;
import org.cz.muni.fi.pb138.Utilities.Util;

/**
 * Action Bean for WSDL
 * @author Peter Lipcak
 */
@UrlBinding("/wsdl/{$event}")
public class WSDLActionBean implements ActionBean {
    
    private FileBean wsdlInput;
    private ActionBeanContext context;
    private DatabaseManager dm = new DatabaseManager(Filetype.WSDL);
    private WSDLManager manager = new WSDLManagerImpl(dm);
    private WSDLParser wsdlParser = new WSDLParser();
    private WSDL result = new WSDL();
    private List<WSDL> resultList = new ArrayList<WSDL>();
    private Long id;
    
    /**
     * Get Context
     * @return ActionBeanContext
     */
    @Override
    public ActionBeanContext getContext() { return context; }
    
    /**
     * Set context
     * @param context  ActionBeanContext 
     */
    @Override
    public void setContext(ActionBeanContext context) { this.context = context; }

    /**
     * Get WSDL
     * @return File Bean
     */
    public FileBean getwsdlInput() {
        return wsdlInput;
    }

    /**
     * Set WSDL
     * @param wsdlInput FileBean WSDL
     */
    public void setwsdlInput(FileBean wsdlInput) {
        this.wsdlInput = wsdlInput;
    }
    
    /**
     * Set ID
     * @param id as String
     */
    public void setId(String id) {
        this.id = new Long(id);
    }
    
    /**
     * Get WSDL
     * @return WSDL
     */
    public WSDL getDoc() {
        return manager.getWSDL(this.id);
    }

    /**
     * Get List WSDLs doc
     * @return List of WSDLs Doc
     */
    public List<WSDL> getResultList() {
        return resultList;
    }
    
    /**
     * WSDL upload
     * @return resolution
     */    
    public Resolution wsdlUpload() {
        try {
            File toFile = new File(System.getProperty("user.home")+File.separator+wsdlInput.getFileName());
            String extension = wsdlInput.getFileName().substring(wsdlInput.getFileName().lastIndexOf(".") + 1, wsdlInput.getFileName().length());
            if(!extension.equals("wsdl")) {
                toFile.delete();
                wsdlInput.delete();
                return new ForwardResolution("/wrongFile.jsp");
            }
            wsdlInput.save(toFile);
            String content = Util.readFile(toFile);

            WSDL wsdl = new WSDL();
            wsdl.setId(manager.getNewId());
            wsdl.setTimestamp(Util.getTimeStamp());
            wsdl.setFileName(wsdlInput.getFileName());
            wsdl.setDocument(Util.stripXMLHeader(content));
            wsdl.setExtract(Util.docToString(wsdlParser.wsdlExtract(Util.stringToDoc(content))));
            manager.createWSDL(wsdl);
            
            toFile.delete();
            wsdlInput.delete();
        } catch (IOException ex) {
            Logger.getLogger(WSDLActionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            return new ForwardResolution("/wrongFile.jsp");
        }
        return new ForwardResolution("/showWSDL.jsp");
    }
    
    /**
     * Show single WSDL
     * @return resolution
     */
    public Resolution showSingle(){
       return new ForwardResolution("/showSingleWSDL.jsp");
    }
    
    /**
     * Get list WSDL
     * @return list of WSDL doc
     */
    public List<WSDL> getWSDLs(){
        return manager.getAllWSDLs();
    }
    
    /**
     * Get Document
     * @return WSDL
     */
    public WSDL getDocument() {
        WSDL wsdl;
        wsdl = manager.getWSDL(result.getId());
        wsdl.setDocument(Util.format(wsdl.getDocument()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        wsdl.setExtract(Util.format(wsdl.getExtract()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        return wsdl;
    }

    /**
     * Getter ID
     * @return id as String
     */
    public String getId(){
        return result.getId().toString();
    }
    
    /**
     * Search id
     * @return resolution
     */
    public Resolution searchId() {
        try{
            Long searchId = Long.parseLong(context.getRequest().getParameter("searchId"));
            if(searchId >= 0 && searchId < manager.getNewId()) {
            result = manager.getWSDL(searchId);
            return new ForwardResolution("/showSingleWSDL.jsp");
        }
        return new ForwardResolution("/wrongSearch.jsp");
        } catch (NumberFormatException ex) {
            return new ForwardResolution("/wrongSearch.jsp");
        }
        
    }
    
    /**
     * Search data
     * @return resolution
     */
    public Resolution searchData() {
        String searchData = context.getRequest().getParameter("dataInput");
        String radioSelected = context.getRequest().getParameter("radioButton");
        if(radioSelected == null)return new ForwardResolution("/wrongSearch.jsp");
        resultList = manager.findWSDLByData(searchData, radioSelected);
        return new ForwardResolution("/showSearchWSDL.jsp");
    }
}
