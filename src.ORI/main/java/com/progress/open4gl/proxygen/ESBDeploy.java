// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.sonicsw.xqimpl.config.XQConfiguration;
import com.sonicsw.xqimpl.config.XQServiceApplicationConfig;
import com.sonicsw.xqimpl.config.XQContainerConfig;
import com.sonicsw.xqimpl.config.XQEndpointConfig;
import com.sonicsw.mf.common.config.IElement;
import com.sonicsw.xqimpl.config.IConfiguration;
import com.sonicsw.xq.XQParameters;
import com.progress.common.util.PscURLParser;
import com.sonicsw.xqimpl.util.XQParametersImpl;
import com.sonicsw.xqimpl.envelope.XQAddressFactoryImpl;
import com.sonicsw.xq.XQAddress;
import com.sonicsw.xqimpl.envelope.XQAddressImpl;
import com.sonicsw.xqimpl.config.XQServiceConfig;
import com.sonicsw.mx.config.util.SonicFSException;
import java.io.File;
import javax.naming.NamingException;
import javax.management.JMException;
import com.sonicsw.mf.common.IDirectoryFileSystemService;
import com.sonicsw.mf.jmx.client.IRemoteMBeanServer;
import javax.management.ObjectName;
import com.sonicsw.mf.jmx.client.JMSConnectorAddress;
import java.util.Hashtable;
import com.sonicsw.mf.jmx.client.DirectoryServiceProxy;
import com.sonicsw.xqimpl.config.XQConfigManager;
import com.sonicsw.mx.config.util.SonicFSFileSystem;
import com.sonicsw.mf.jmx.client.JMSConnectorClient;

public class ESBDeploy
{
    private JMSConnectorClient m_connector;
    private SonicFSFileSystem m_fs;
    private XQConfigManager m_configMgr;
    private DirectoryServiceProxy m_prxy;
    private String m_url;
    private String m_user;
    private String m_password;
    private String m_domain;
    private static String OPENEDGESRV_TYPE;
    
    ESBDeploy() {
        this.m_connector = null;
        this.m_fs = null;
        this.m_configMgr = null;
        this.m_prxy = null;
        this.m_url = "tcp://localhost:2506";
        this.m_user = "Administrator";
        this.m_password = "Administrator";
        this.m_domain = "Domain1";
    }
    
    public void connect() throws JMException, NamingException {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("ConnectionURLs", this.m_url);
        hashtable.put("DefaultUser", this.m_user);
        hashtable.put("DefaultPassword", this.m_password);
        (this.m_connector = new JMSConnectorClient()).connect(new JMSConnectorAddress((Hashtable)hashtable), 30000L);
        this.m_connector.setRequestTimeout(10000L);
        this.m_fs = new SonicFSFileSystem((IDirectoryFileSystemService)new DirectoryServiceProxy((IRemoteMBeanServer)this.m_connector, new ObjectName(this.m_domain + ".DIRECTORY SERVICE:ID=DIRECTORY SERVICE")), this.m_user);
        (this.m_configMgr = XQConfigManager.getInstance((Hashtable)null)).setDirectoryProxy(this.m_url, this.m_password, this.m_user, this.m_domain);
    }
    
    public void connect(final String url, final String domain, final String user, final String password) throws JMException, NamingException {
        this.m_url = url;
        this.m_domain = domain;
        this.m_user = user;
        this.m_password = password;
        this.connect();
    }
    
    public void disconnect() {
        if (this.m_connector != null) {
            this.m_connector.disconnect();
            this.m_connector = null;
        }
    }
    
    public boolean deploy(final File file, String s, final File file2, String s2, final String s3, final boolean b) throws SonicFSException {
        if (!this.m_fs.exists(s3)) {
            this.m_fs.createDirectoryPath(s3);
        }
        if (s3.endsWith("/") || s3.endsWith("\\")) {
            s = s3 + s;
            s2 = s3 + s2;
        }
        else {
            s = s3 + "/" + s;
            s2 = s3 + "/" + s2;
        }
        final boolean exists = this.m_fs.exists(s);
        final boolean exists2 = this.m_fs.exists(s2);
        if (!b && (exists || exists2)) {
            return false;
        }
        this.m_fs.updateFile(s, file, true);
        this.m_fs.updateFile(s2, file2, true);
        return true;
    }
    
    public boolean deploy(final File file, String s, final String s2, final boolean b) throws SonicFSException {
        if (!this.m_fs.exists(s2)) {
            this.m_fs.createDirectoryPath(s2);
        }
        if (s2.endsWith("/") || s2.endsWith("\\")) {
            s = s2 + s;
        }
        else {
            s = s2 + "/" + s;
        }
        final boolean exists = this.m_fs.exists(s);
        if (!b && exists) {
            return false;
        }
        this.m_fs.updateFile(s, file, true);
        return true;
    }
    
    public void createService(final String s, final String entryEndpoint, final String s2, final String s3, final String s4, final String wsdlUrl, final String s5) throws Exception {
        IElement emptiedElement = null;
        if (entryEndpoint.length() > 0 && !this.m_configMgr.doesElementExist(entryEndpoint, "endpoint")) {
            this.createEndpoint(entryEndpoint);
        }
        if (s2.length() > 0 && !this.m_configMgr.doesElementExist(s2, "endpoint")) {
            this.createEndpoint(s2);
        }
        if (s3.length() > 0 && !this.m_configMgr.doesElementExist(s3, "endpoint")) {
            this.createEndpoint(s3);
        }
        if (this.m_configMgr.doesElementExist(s, "service")) {
            emptiedElement = this.m_configMgr.getEmptiedElement(s, "service");
        }
        final XQServiceConfig xqServiceConfig = new XQServiceConfig();
        ((XQConfiguration)xqServiceConfig).setParent(this.m_configMgr);
        if (emptiedElement == null) {
            ((XQConfiguration)xqServiceConfig).createElement(s);
        }
        else {
            ((XQConfiguration)xqServiceConfig).setElement(emptiedElement);
        }
        xqServiceConfig.setServiceType(ESBDeploy.OPENEDGESRV_TYPE);
        xqServiceConfig.setEntryEndpoint(entryEndpoint);
        if (s2.length() > 0) {
            xqServiceConfig.setFaultEndpoint((XQAddress)new XQAddressImpl(s2, 0));
        }
        if (s3.length() > 0) {
            xqServiceConfig.setRejectedMessageEndpoint((XQAddress)new XQAddressImpl(s3, 0));
        }
        xqServiceConfig.addExitEndpoint(XQAddressFactoryImpl.getReplyToStatic());
        xqServiceConfig.setWSDLUrl(wsdlUrl);
        final XQParametersImpl parameters = new XQParametersImpl();
        parameters.setParameter("WSM", 1, "", s4);
        final PscURLParser pscURLParser = new PscURLParser(s5);
        parameters.setParameter("appServiceProtocol", 1, pscURLParser.getScheme());
        parameters.setParameter("appServiceHost", 1, pscURLParser.getHost());
        parameters.setIntParameter("appServicePort", 1, pscURLParser.getPort());
        xqServiceConfig.setParameters((XQParameters)parameters);
        xqServiceConfig.writeToDS();
        this.m_configMgr.writeComponentToDS((IConfiguration)xqServiceConfig, s, "service");
    }
    
    public boolean checkEndpoint(final String s) throws Exception {
        boolean b = false;
        if (s.length() > 0 && !this.m_configMgr.doesElementExist(s, "endpoint")) {
            this.createEndpoint(s);
            b = true;
        }
        return b;
    }
    
    private boolean createEndpoint(final String str) throws Exception {
        System.out.println("Creating new endpoint... " + str);
        final XQEndpointConfig xqEndpointConfig = new XQEndpointConfig();
        ((XQConfiguration)xqEndpointConfig).setParent(this.m_configMgr);
        System.out.println("Creating DS Element");
        ((XQConfiguration)xqEndpointConfig).createElement(str);
        xqEndpointConfig.setConnection("jms_defaultConnection");
        xqEndpointConfig.setEndpointType("JMSType");
        xqEndpointConfig.setQOS("BEST_EFFORT");
        final XQParametersImpl parameters = new XQParametersImpl();
        parameters.setParameter("jmsPriority", 1, "4");
        parameters.setParameter("destination", 1, str);
        parameters.setParameter("durableSubscriber", 1, "false");
        parameters.setParameter("type", 1, "QUEUE");
        parameters.setParameter("priority", 1, "4");
        xqEndpointConfig.setParameters(parameters);
        xqEndpointConfig.writeToDS();
        System.out.println("Writing new endpoint to DS");
        this.m_configMgr.writeComponentToDS((IConfiguration)xqEndpointConfig, str, "endpoint");
        return true;
    }
    
    public boolean addToContainer(final String s, final String s2) throws Exception {
        if (!this.m_configMgr.doesElementExist(s2, "XQ_CONTAINER")) {
            return false;
        }
        final XQContainerConfig xqContainerConfig = (XQContainerConfig)this.m_configMgr.lookup(s2, "XQ_CONTAINER");
        if (xqContainerConfig.getServiceApplication(s) == null) {
            final XQServiceApplicationConfig serviceApplication = new XQServiceApplicationConfig();
            serviceApplication.setServiceName(s);
            serviceApplication.setServiceApplicationName(s);
            serviceApplication.setInstances(1);
            xqContainerConfig.setServiceApplication(serviceApplication);
            xqContainerConfig.writeToDS();
            this.m_configMgr.writeComponentToDS((IConfiguration)xqContainerConfig, s2, "XQ_CONTAINER");
        }
        return true;
    }
    
    static {
        ESBDeploy.OPENEDGESRV_TYPE = "OpenEdgeSrvType";
    }
}
