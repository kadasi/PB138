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
import org.cz.muni.fi.pb138.Managers.WarManager;
import org.cz.muni.fi.pb138.Documents.WAR;
import org.cz.muni.fi.pb138.ManagersImpl.WarManagerImpl;
import org.cz.muni.fi.pb138.MetadataParsers.WARParser;
import org.cz.muni.fi.pb138.Utilities.DatabaseManager;
import org.cz.muni.fi.pb138.Utilities.Filetype;
import org.cz.muni.fi.pb138.Utilities.Util;

/**
 * Action Bean for WAR
 * @author Peter Lipcak
 */
@UrlBinding("/war/{$event}")
public class WARActionBean implements ActionBean {

    private FileBean warInput;
    private ActionBeanContext context;
    private DatabaseManager dm = new DatabaseManager(Filetype.WAR);
    private WarManager manager = new WarManagerImpl(dm);
    private WARParser webParser = new WARParser();
    private WAR result = new WAR();
    private List<WAR> resultList = new ArrayList<WAR>();

    /**
     * Getter for context
     * @return Context
     */
    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    /**
     * Get WAR archive list
     * @return war archive List
     */
    public List<WAR> getResultList() {
        return resultList;
    }

    /**
     * Setter for context
     * @param context 
     */
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    /**
     * Getter for WAR input
     * @return war input
     */
    public FileBean getwarInput() {
        return warInput;
    }

    /**
     * Setter for WAR input
     * @param warInput war input
     */
    public void setwarInput(FileBean warInput) {
        this.warInput = warInput;
    }

    /**
     * Upload WAR file
     * @return resolution
     */
    public Resolution warUpload() {
        try {
            File toFile = new File(System.getProperty("user.home") + File.separator + warInput.getFileName());
            String extension = warInput.getFileName().substring(warInput.getFileName().lastIndexOf(".") + 1, warInput.getFileName().length());
            if(!extension.equals("war")) {
                toFile.delete();
                warInput.delete();
                return new ForwardResolution("/wrongFile.jsp");
            }
            warInput.save(toFile);
            String content = Util.docToString(Util.warExtract(toFile));;

            WAR war = new WAR();
            war.setId(manager.getNewId());
            war.setTimestamp(Util.getTimeStamp());
            war.setFileName(warInput.getFileName());
            war.setWebXml(Util.stripXMLHeader(content));
            war.setExtract(Util.docToString(webParser.webXMLExtract(Util.stringToDoc(content))));
            manager.createWarArchive(war);

            toFile.delete();
            warInput.delete();
        } catch (IOException ex) {
            Logger.getLogger(WARActionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            return new ForwardResolution("/wrongFile.jsp");
        }
        return new ForwardResolution("/showWAR.jsp");
    }

    /**
     * Get all WARs
     * @return list of WARs
     */
    public List<WAR> getWARs() {
        return manager.getAllArchives();
    }
    
    /**
     * Get Document from WARs archives
     * @return WAR
     */
    public WAR getDocument() {
        WAR war;
        war = manager.getWarArchive(result.getId());
        war.setWebXml(Util.format(war.getWebXml()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        war.setExtract(Util.format(war.getExtract()).replaceAll("<", "&lt;").replaceAll(">","&gt;"));
        return war;
    }
    
    /**
     * Getter for ID
     * @return id as String
     */
    public String getId(){
        return result.getId().toString();
    }

    /**
     * Resolution search id
     * @return resolution
     */
    public Resolution searchId() {
        try{
            Long searchId = Long.parseLong(context.getRequest().getParameter("searchId"));
            if(searchId >= 0 && searchId < manager.getNewId()) {
            result = manager.getWarArchive(searchId);
            return new ForwardResolution("/showSingleWAR.jsp");
        }
        return new ForwardResolution("/wrongSearch.jsp");
        } catch (NumberFormatException ex) {
            return new ForwardResolution("/wrongSearch.jsp");
        }
    }
    
    /**
     * Resolution for search data
     * @return resolution
     */
    public Resolution searchData() {
        String searchData = context.getRequest().getParameter("dataInput");
        String radioSelected = context.getRequest().getParameter("radioButton");
        if(radioSelected == null)return new ForwardResolution("/wrongSearch.jsp");
        resultList = manager.findWarByData(searchData, radioSelected);
        return new ForwardResolution("/showSearchWAR.jsp");
    }
}
