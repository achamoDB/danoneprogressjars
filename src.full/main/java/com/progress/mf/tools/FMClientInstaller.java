// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.tools;

import com.sonicsw.mf.mgmtapi.config.gen.AbstractMFMgmtBeanFactory;
import com.sonicsw.mx.config.IConfigElement;
import com.sonicsw.ma.mgmtapi.config.IMgmtBase;
import com.sonicsw.ma.mgmtapi.config.impl.AbstractMgmtBeanFactory;
import com.progress.common.util.Environment;
import com.progress.chimera.adminserver.IAdminServerConst;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import com.sonicsw.mf.common.config.IElement;
import com.sonicsw.mf.common.util.Container;
import com.sonicsw.mf.common.dirconfig.IDirElement;
import com.sonicsw.mf.common.dirconfig.IDeltaDirElement;
import com.sonicsw.mf.common.config.Reference;
import com.sonicsw.mf.common.config.IAttributeSet;
import com.sonicsw.mf.common.IDirectoryAdminService;
import com.sonicsw.mf.mgmtapi.config.IContainerBean;
import com.sonicsw.mf.common.IDirectoryFileSystemService;
import com.sonicsw.mf.jmx.client.IRemoteMBeanServer;
import com.sonicsw.mf.jmx.client.DirectoryServiceProxy;
import javax.management.ObjectName;
import com.sonicsw.mf.mgmtapi.config.MFMgmtBeanFactory;
import com.sonicsw.mf.jmx.client.JMSConnectorAddress;
import java.util.Hashtable;
import com.progress.common.log.ProLog;
import com.progress.common.message.IProMessage;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.util.PromsgsFile;
import com.sonicsw.mf.jmx.client.JMSConnectorClient;
import com.progress.message.amMsg;

public class FMClientInstaller extends ConfigurationTool implements IFMInstaller, amMsg
{
    private JMSConnectorClient m_connector;
    
    public static void main(final String[] array) {
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        }
        catch (PromsgsFile.PromsgsFileIOException ex) {
            System.out.println("There is a problem with the Progress Installation, promsgs cannot be found");
        }
        System.exit(new FMClientInstaller().configureFramework(array) ? 0 : 1);
    }
    
    public boolean configureFramework(final String[] array) {
        boolean b = false;
        if (array.length == 0) {
            this.printUsage();
            return false;
        }
        super.exitStatus = this.validateCommandLineArguments(array, false);
        if (super.exitStatus != 0) {
            if (super.m_help) {
                this.printUsage();
            }
            return false;
        }
        if (super.m_debug) {
            System.out.println(ProLog.format(7020267394140482794L));
        }
        if (super.m_enable || super.m_update) {
            String x = ProLog.format(7020267394140482916L);
            if (super.m_update) {
                x = ProLog.format(7020267394140482917L);
            }
            System.out.println(x);
            b = this.loadProperties();
            if (b) {
                b = this.createContainer();
            }
            if (b) {
                b = this.setEnabledProperty(true);
                if (!b) {
                    System.out.println(ProLog.format(7020267394140482918L));
                }
                else {
                    b = this.copyXsdFile(System.getProperty("Install.Dir"));
                    if (b) {
                        System.out.println(ProLog.format(7020267394140482919L));
                    }
                    else {
                        System.out.println(ProLog.format(7020267394140482920L));
                    }
                    this.checkDiskPerfCounters();
                    System.out.println(" ");
                }
            }
        }
        return b;
    }
    
    public boolean createContainer() {
        boolean b = false;
        Object o = null;
        MFMgmtBeanFactory mfMgmtBeanFactory = null;
        Object o2 = null;
        try {
            String x = "";
            final String string = super.m_hostName + ":" + super.m_portNumber;
            final boolean value = true;
            final String hostName = this.getHostName();
            final Hashtable<String, Boolean> hashtable = new Hashtable<String, Boolean>();
            hashtable.put("BrokerURL", (Boolean)string);
            hashtable.put("DefaultUser", (Boolean)super.m_userName);
            hashtable.put("DefaultPassword", (Boolean)super.m_password);
            hashtable.put("LoadBalancingBoolean", new Boolean(value));
            final JMSConnectorAddress jmsConnectorAddress = new JMSConnectorAddress((Hashtable)hashtable);
            mfMgmtBeanFactory = new MFMgmtBeanFactory();
            System.out.println(ProLog.format(7020267394140482921L, string));
            this.m_connector = new JMSConnectorClient();
            if (super.m_debug) {
                System.out.println(ProLog.format(7020267394140482922L, new Integer(new Long(30L).intValue())));
            }
            this.m_connector.connect(jmsConnectorAddress, 30000L);
            if (!this.m_connector.isConnected()) {
                throw new Exception(ProLog.format(7020267394140482923L));
            }
            System.out.print(ProLog.format(7020267394140482925L));
            o2 = new DirectoryServiceProxy((IRemoteMBeanServer)this.m_connector, new ObjectName(super.m_domainName + ".DIRECTORY SERVICE:ID=DIRECTORY SERVICE"));
            ((AbstractMgmtBeanFactory)mfMgmtBeanFactory).connect((IDirectoryFileSystemService)o2);
            System.out.println(ProLog.format(7020267394140482804L));
            final String containerName = ConfigurationTool.m_properties.getContainerName();
            final boolean b2 = containerName == null || containerName.length() < 1;
            String s = ProLog.format(7020267394140482804L);
            if (!b2 && super.m_containerName == null) {
                System.out.print(ProLog.format(7020267394140482926L, containerName));
                o = this.createContainerForName(mfMgmtBeanFactory, (IDirectoryFileSystemService)o2, containerName, false);
                super.m_containerName = containerName;
            }
            else if (super.m_containerName != null) {
                System.out.print(ProLog.format(7020267394140482926L, super.m_containerName));
                o = this.createContainerForName(mfMgmtBeanFactory, (IDirectoryFileSystemService)o2, super.m_containerName, super.m_update);
            }
            else {
                System.out.print(ProLog.format(7020267394140482927L, hostName));
                o = this.createContainerForHost(mfMgmtBeanFactory, (IDirectoryFileSystemService)o2, hostName);
                s = s + IConfigToolConst.NEWLINE + ProLog.format(7020267394140482930L, super.m_containerName);
            }
            if (o == null) {
                System.out.println(ProLog.format(7020267394140482937L));
                System.out.println(ProLog.format(7020267394140482944L));
                System.exit(1);
            }
            System.out.println(s);
            System.out.println(ProLog.format(7020267394140482946L, super.m_containerName));
            super.m_containerId = ((IConfigElement)((IMgmtBase)o).getConfigBean()).getName();
            super.m_containerName = ((IMgmtBase)o).getConfigBeanNameTail();
            b = this.addFathomComponents(mfMgmtBeanFactory, (IDirectoryFileSystemService)o2, (IContainerBean)o, super.m_containerName);
            if (b) {
                System.out.print(ProLog.format(7020267394140482948L, "container.xml"));
                b = this.createBootFile((IDirectoryAdminService)o2, super.m_containerName);
                if (!b) {
                    System.out.println(ProLog.format(7020267394140482937L));
                    x = ProLog.format(7020267394140482951L);
                }
                System.out.println(ProLog.format(7020267394140482804L));
            }
            else {
                x = ProLog.format(7020267394140482953L);
            }
            if (!b) {
                System.out.println(x);
                System.out.println(ProLog.format(7020267394140482944L));
            }
        }
        catch (Exception ex) {
            System.out.println(ProLog.format(7020267394140482954L));
            if (super.m_debug) {
                ex.printStackTrace();
            }
        }
        finally {
            if (!b && o != null) {
                this.removeSonicContainer(mfMgmtBeanFactory, (IDirectoryFileSystemService)o2, super.m_containerId);
            }
            if (this.m_connector != null) {
                this.m_connector.disconnect();
            }
        }
        return b;
    }
    
    private IContainerBean createSonicContainer(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final String s) {
        IContainerBean containerBean = null;
        try {
            containerBean = ((AbstractMFMgmtBeanFactory)mfMgmtBeanFactory).createContainerBean(s);
            ((AbstractMFMgmtBeanFactory)mfMgmtBeanFactory).saveContainerBean(containerBean);
            ((AbstractMgmtBeanFactory)mfMgmtBeanFactory).commit();
        }
        catch (Exception ex) {
            if (super.m_debug) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return containerBean;
    }
    
    private IContainerBean getSonicContainer(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final String s) {
        IContainerBean containerBean = null;
        try {
            containerBean = ((AbstractMFMgmtBeanFactory)mfMgmtBeanFactory).getContainerBean(s);
        }
        catch (Exception ex) {
            if (super.m_debug) {
                System.out.println(ex.getMessage());
            }
        }
        return containerBean;
    }
    
    private boolean removeSonicContainer(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final String s) {
        boolean b = false;
        IContainerBean sonicContainer = null;
        try {
            sonicContainer = this.getSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, s);
        }
        catch (Exception ex2) {
            b = true;
        }
        if (sonicContainer != null) {
            try {
                ((AbstractMFMgmtBeanFactory)mfMgmtBeanFactory).deleteContainerBean(sonicContainer);
                ((AbstractMgmtBeanFactory)mfMgmtBeanFactory).commit();
                b = true;
            }
            catch (Exception ex) {
                if (super.m_debug) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return b;
    }
    
    private IContainerBean createContainerForName(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final String str, final boolean b) {
        final String string = "/containers/" + str;
        IContainerBean containerBean = this.getSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, string);
        if (containerBean != null) {
            if (b) {
                final boolean removeSonicContainer = this.removeSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, string);
                containerBean = null;
                if (!removeSonicContainer) {
                    System.out.println(ProLog.format(7020267394140482937L));
                    System.out.println(ProLog.format(7020267394140482955L, str));
                }
            }
            else {
                System.out.println(ProLog.format(7020267394140482956L, str));
            }
        }
        if (containerBean == null) {
            containerBean = this.createSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, string);
        }
        return containerBean;
    }
    
    private IContainerBean createContainerForHost(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final String s) {
        IContainerBean containerBean = null;
        for (int i = 1; i < 10; ++i) {
            final String string = "/containers/" + s + i;
            containerBean = this.getSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, string);
            if (containerBean == null) {
                if (super.m_debug) {
                    System.out.println(ProLog.format(7020267394140482957L, string));
                }
                containerBean = this.createSonicContainer(mfMgmtBeanFactory, directoryFileSystemService, string);
                super.m_containerName = s + i;
                break;
            }
            if (super.m_debug) {
                System.out.println(ProLog.format(7020267394140482958L, string));
            }
        }
        return containerBean;
    }
    
    private void createConnectionAttributeSet(final IAttributeSet set) {
        try {
            System.out.println("Creating CONNECTION attribute set...");
            set.setStringAttribute("ConnectionURLs", "tcp://" + super.m_hostName + ":" + super.m_portNumber);
            set.setStringAttribute("DefaultPassword", super.m_userName);
            set.setStringAttribute("DefaultUser", super.m_password);
        }
        catch (Exception ex) {
            System.out.println("---> Exception while creating attribute set: " + ex.getMessage());
        }
    }
    
    private static void createComponentAttributeSet(final IAttributeSet set, final String s) {
        try {
            System.out.println("Creating COMPONENT attribute set for " + s + "...");
            final IAttributeSet attributeSet = set.createAttributeSet(s);
            attributeSet.setReferenceAttribute("CONFIG_REF", new Reference("/Framework Components/" + s));
            attributeSet.setBooleanAttribute("AUTO_START", Boolean.FALSE);
        }
        catch (Exception ex) {
            System.out.println("---> Exception while creating attribute set: " + ex.getMessage());
        }
    }
    
    private boolean addFathomComponents(final MFMgmtBeanFactory mfMgmtBeanFactory, final IDirectoryFileSystemService directoryFileSystemService, final IContainerBean containerBean, final String str) {
        boolean b = false;
        try {
            final IDirElement fsElement = directoryFileSystemService.getFSElement("/containers/" + str, true);
            final IAttributeSet attributes = ((IElement)fsElement).getAttributes();
            this.createConnectionAttributeSet((IAttributeSet)attributes.getAttribute("CONNECTION"));
            final IAttributeSet set = (IAttributeSet)attributes.getAttribute("COMPONENTS");
            createComponentAttributeSet(set, "ManagementPlugin");
            createComponentAttributeSet(set, "osmetrics");
            createComponentAttributeSet(set, "AppServer");
            createComponentAttributeSet(set, "Database");
            createComponentAttributeSet(set, "NameServer");
            createComponentAttributeSet(set, "SMDatabase");
            createComponentAttributeSet(set, "WebSpeed");
            createComponentAttributeSet(set, "AppServer Internet Adapter");
            createComponentAttributeSet(set, "ODBC DataServer");
            createComponentAttributeSet(set, "Oracle DataServer");
            createComponentAttributeSet(set, "MSS DataServer");
            createComponentAttributeSet(set, "Messengers");
            createComponentAttributeSet(set, "SonicMQ Adapter");
            createComponentAttributeSet(set, "Web Services Adapter");
            directoryFileSystemService.updateFSElement((IDeltaDirElement)fsElement.doneUpdate());
            b = true;
        }
        catch (Exception ex) {
            if (super.m_debug) {
                System.out.println(ProLog.format(7020267394140482959L, str));
            }
        }
        return b;
    }
    
    private boolean loadComponents(final IDirectoryFileSystemService directoryFileSystemService, final String s) {
        boolean b = true;
        try {
            System.out.println("Loading " + directoryFileSystemService.logicalToStorage("/Framework Components/" + s));
        }
        catch (Exception ex) {
            System.out.println("Unable to load component " + s);
            b = false;
        }
        return b;
    }
    
    public boolean createBootFile(final IDirectoryAdminService directoryAdminService, final String str) {
        final String string = System.getProperty("Install.Dir") + IConfigToolConst.FILE_SEPARATOR + "container.xml";
        boolean b;
        try {
            final String exportContainerBootConfiguration = Container.exportContainerBootConfiguration((IElement)directoryAdminService.getElement(((IDirectoryFileSystemService)directoryAdminService).logicalToStorage("/containers/" + str), true), super.m_domainName);
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(string)));
            outputStreamWriter.write(exportContainerBootConfiguration);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            b = true;
            if (super.m_debug && super.VERBOSE) {
                System.out.println("Reporting contents:");
                System.out.println();
                System.out.println(exportContainerBootConfiguration);
                System.out.println();
                System.out.println("Done.");
            }
        }
        catch (Exception ex) {
            if (super.m_debug && super.VERBOSE) {
                ex.printStackTrace();
            }
            b = false;
        }
        return b;
    }
    
    public boolean checkDiskPerfCounters() {
        boolean b = true;
        if (!this.isDiskPerfEnabled()) {
            System.out.println(ProLog.format(7020267394140482962L));
            try {
                final Process exec = Runtime.getRuntime().exec("diskperf.exe -y");
                this.readProcessOutput(new BufferedReader(new InputStreamReader(exec.getInputStream())));
                final int processExitStatus = this.getProcessExitStatus(exec);
                if (super.m_debug && processExitStatus != 0) {
                    System.out.println(ProLog.format(7020267394140482811L, new Integer(processExitStatus)));
                }
                b = (processExitStatus == 0);
            }
            catch (IOException ex) {
                b = false;
                System.out.println(ProLog.format(7020267394140482963L));
            }
            if (b) {
                System.out.println("");
                System.out.println(ProLog.format(7020267394140482964L));
                System.out.println(ProLog.format(7020267394140482966L));
            }
        }
        return b;
    }
    
    public boolean isDiskPerfEnabled() {
        final String s = "@{\\SYSTEM\\CurrentControlSet\\Services\\Diskperf\\Start}";
        final boolean equalsIgnoreCase = System.getProperty("os.name").equalsIgnoreCase("Windows XP");
        boolean b = false;
        if (IAdminServerConst.IS_NT && !equalsIgnoreCase) {
            final Environment environment = new Environment();
            if (environment != null) {
                final String expandPropertyValue = environment.expandPropertyValue(s);
                if (expandPropertyValue != null && expandPropertyValue.length() > 0) {
                    b = (expandPropertyValue.charAt(0) == '\u0004');
                }
            }
        }
        return !b;
    }
}
