// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.common.Tools;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.rmi.RemoteException;
import com.progress.common.property.PropertyTransferObject;
import java.util.Vector;
import java.rmi.server.RemoteStub;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.property.IPropertyValueProvider;

public class PropertyValueSet implements IPropertyValueSet, IPropertyStatusValues, IPropertyStates, IPropertyValueProvider
{
    IPropertyManagerRemote remotePropertyManager;
    String groupName;
    String instanceName;
    String m_fullName;
    GUIMetaSchema schema;
    RemoteStub stubObject;
    String name;
    transient PropertySetDefinition definitions;
    protected PropertyValue[] values;
    protected PropertyValueSet prototype;
    transient Vector proteges;
    protected String componentName;
    protected String componentContext;
    
    public IPropertyDefinition getDefinition(final int n) throws XPropertyHasNoValue {
        return this.definitions.getDefinition(n);
    }
    
    public IPropertyDefinition getDefinition(final String s) throws XPropertyHasNoValue, XPropertyDoesNotExist {
        return this.getDefinition(this.definitions.indexOf(s));
    }
    
    public PropertyValueSet(final PropertySetDefinition definitions) throws XPropertyException {
        this.remotePropertyManager = null;
        this.groupName = null;
        this.instanceName = null;
        this.m_fullName = null;
        this.schema = null;
        this.stubObject = null;
        this.definitions = null;
        this.values = null;
        this.prototype = null;
        this.proteges = null;
        this.componentName = null;
        this.componentContext = null;
        this.definitions = definitions;
        this.values = new PropertyValue[this.definitions.properties.size()];
        this.prototype = null;
    }
    
    public PropertyValueSet(final GUIMetaSchema schema, final String groupName) throws XPropertyException {
        this(schema.findDefintion(groupName));
        this.schema = schema;
        try {
            this.schema.initialize(groupName, this.schema.findDefintion(groupName), this);
        }
        catch (InstantiationException previous) {
            final XPropertyException ex = new XPropertyException("Can't instantiate elements of PVS");
            ex.setPrevious(previous);
            throw ex;
        }
        this.groupName = groupName;
    }
    
    public PropertyValueSet(final GUIMetaSchema guiMetaSchema, final IPropertyManagerRemote remotePropertyManager, final String s, final String instanceName) throws XPropertyException {
        this(guiMetaSchema, s);
        this.instanceName = instanceName;
        this.remotePropertyManager = remotePropertyManager;
        this.reload();
    }
    
    GUIMetaSchema getSchema() {
        return this.schema;
    }
    
    void reload() throws XPropertyException {
        this.setFullInstanceName();
        load(this, this.makePropertyTransferObject(this.m_fullName));
    }
    
    PropertyTransferObject makePropertyTransferObject(final String str) throws XPropertyException {
        try {
            return this.remotePropertyManager.makePropertyTransferObject(str);
        }
        catch (RemoteException previous) {
            final XPropertyException ex = new XPropertyException("Error making remote property transfer object for group: " + str);
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    public int updateServer(final String s, final Vector vector, final Object o) throws XPropertyException, XPropertySaveError, XPropertyFileSaveError {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle");
        int updateFromClient;
        try {
            this.remotify();
            this.setFullInstanceName();
            updateFromClient = this.remotePropertyManager.updateFromClient(s, this, (RemoteStub)o, this.m_fullName, vector);
        }
        catch (PropertyManager.SaveIOException previous) {
            Tools.px(previous);
            final XPropertyFileSaveError xPropertyFileSaveError = new XPropertyFileSaveError(progressResources.getTranString("Error saving property file:", (Object)s));
            xPropertyFileSaveError.setPrevious(previous);
            throw xPropertyFileSaveError;
        }
        catch (Exception previous2) {
            Tools.px(previous2);
            final XPropertySaveError xPropertySaveError = new XPropertySaveError(progressResources.getTranString("An error occurred on the server while saving properties."));
            xPropertySaveError.setPrevious(previous2);
            throw xPropertySaveError;
        }
        return updateFromClient;
    }
    
    public void restoreServerDefaults(final String s, final Vector vector, final Object o) throws XPropertyException {
        try {
            this.remotify();
            this.setFullInstanceName();
            this.remotePropertyManager.restoreDefaults(s, this, (RemoteStub)o, this.m_fullName, vector);
            this.reload();
        }
        catch (Exception previous) {
            final XPropertyException ex = new XPropertyException("Can't restore property defaults");
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    public PropertyValueSet(final PropertySetDefinition propertySetDefinition, final IPropertyManagerRemote propertyManagerRemote, final String s) throws XPropertyException, RemoteException {
        this(propertySetDefinition);
        this.load(propertyManagerRemote, s);
    }
    
    public RemoteStub evObj() {
        try {
            if (this.stubObject == null) {
                this.remotify();
            }
        }
        catch (RemoteException ex) {}
        return this.stubObject;
    }
    
    protected void remotify() throws RemoteException {
        if (this.stubObject != null) {
            return;
        }
        this.stubObject = UnicastRemoteObject.exportObject(this);
    }
    
    public String name() {
        return this.name;
    }
    
    public PropertyValueSet(final PropertyValueSet prototype) throws XAttemptToUseUnnamedPrototype {
        this.remotePropertyManager = null;
        this.groupName = null;
        this.instanceName = null;
        this.m_fullName = null;
        this.schema = null;
        this.stubObject = null;
        this.definitions = null;
        this.values = null;
        this.prototype = null;
        this.proteges = null;
        this.componentName = null;
        this.componentContext = null;
        this.prototype = prototype;
        this.definitions = this.prototype.definitions;
        this.values = new PropertyValue[this.definitions.properties.size()];
        this.definitions.addValueSet(this);
        this.prototype.addProtege(this);
        this.componentName = this.prototype.componentName;
        this.componentContext = this.prototype.componentContext;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Object getValue(final int i) throws XPropertyHasNoValue {
        final PropertyValue propertyValue = this.values[i];
        if (propertyValue != null && propertyValue.hasLocalValue) {
            return propertyValue.localValue;
        }
        if (this.prototype == null) {
            throw new XPropertyHasNoValue("" + i);
        }
        return this.prototype.getValue(i);
    }
    
    public Object setValue(final int n, final Object localValue) throws XPropertyValueIsNotValid {
        PropertyValue propertyValue = this.values[n];
        Object o;
        if (propertyValue == null) {
            o = null;
            final PropertyValue[] values = this.values;
            final PropertyValue propertyValue2 = new PropertyValue(localValue);
            values[n] = propertyValue2;
            propertyValue = propertyValue2;
            propertyValue.hasLocalValue = true;
        }
        else if (propertyValue.hasLocalValue) {
            o = propertyValue.localValue;
        }
        else if (this.prototype == null) {
            o = null;
            propertyValue.hasLocalValue = true;
        }
        else {
            try {
                o = this.prototype.getValue(n);
            }
            catch (XPropertyHasNoValue xPropertyHasNoValue) {
                o = null;
            }
        }
        propertyValue.localValue = localValue;
        return o;
    }
    
    public Object setValue(final String s, final Object o) throws XPropertyHasNoValue, XPropertyDoesNotExist, XPropertyValueIsNotValid {
        return this.setValue(this.definitions.indexOf(s), o);
    }
    
    public Object getValue(final String s) throws XPropertyHasNoValue, XPropertyDoesNotExist {
        return this.getValue(this.definitions.indexOf(s));
    }
    
    public Object getValueRemote(final String str) throws RemoteException {
        try {
            this.remotify();
            final Object value = this.getValue(str);
            if (value instanceof GroupReferenceSubsumed) {
                final GroupReferenceSubsumed groupReferenceSubsumed = (GroupReferenceSubsumed)value;
                final PropertyTransferObject pto = groupReferenceSubsumed.pto;
                if (pto.getGroupName() == null) {
                    pto.setGroupName(groupReferenceSubsumed.prefix + this.instanceName);
                }
                return groupReferenceSubsumed.pto;
            }
            return this.getValuesAsString(str);
        }
        catch (Exception cause) {
            throw new RemoteException("Error getting value of property: " + str, cause);
        }
    }
    
    public String getValueAsString(final String s) throws XPropertyException {
        String s2 = "";
        final String[] valuesAsString = DatatypeModelMapping.getDatatypeModel(this.getValue(s)).getValuesAsString();
        for (int i = 0; i < valuesAsString.length; ++i) {
            if (i != 0) {
                s2 += ", ";
            }
            s2 += valuesAsString[i];
        }
        return s2;
    }
    
    public String[] getValuesAsString(final String s) throws XPropertyException {
        return DatatypeModelMapping.getDatatypeModel(this.getValue(s)).getValuesAsString();
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    protected Object unsetValue(final int i) throws XPropertyHasNoValue, XUnsettingNonDelegatedPropertyValue {
        if (this.prototype == null) {
            throw new XUnsettingNonDelegatedPropertyValue("" + i);
        }
        final PropertyValue propertyValue = this.values[i];
        Object localValue;
        if (propertyValue == null) {
            localValue = null;
        }
        else if (propertyValue.hasLocalValue) {
            localValue = propertyValue.localValue;
            this.values[i] = null;
        }
        else {
            localValue = null;
        }
        return localValue;
    }
    
    public Object unsetValue(final String s) throws XPropertyHasNoValue, XPropertyDoesNotExist, XUnsettingNonDelegatedPropertyValue {
        return this.unsetValue(this.definitions.indexOf(s));
    }
    
    protected void addProtege(final PropertyValueSet obj) {
        if (this.proteges == null) {
            this.proteges = new Vector();
        }
        this.proteges.addElement(obj);
    }
    
    public IPropertySetDefinition getDefinition() {
        return this.definitions;
    }
    
    public void startUpdate(final Object o) {
    }
    
    public void commitUpdate() {
    }
    
    public void cancelUpdate() {
    }
    
    protected int state(final int n) throws XPropertyDoesNotExist {
        if (this.values[n].hasLocalValue) {
            return 1;
        }
        if (this.prototype != null) {
            return this.prototype.state(n);
        }
        return 2;
    }
    
    public int state(final String s) throws XPropertyDoesNotExist {
        final int index = this.definitions.indexOf(s);
        if (this.values[index].hasLocalValue) {
            return 3;
        }
        return this.state(index);
    }
    
    public boolean isComplete(final boolean b) {
        for (int i = 0; i < this.definitions.properties.size(); ++i) {
            final PropertyDefinition propertyDefinition = this.definitions.properties.elementAt(i);
            if (!b || !propertyDefinition.isMandatory) {
                if (!this.hasValue(i)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasMandatoryProperties() {
        for (int i = 0; i < this.definitions.properties.size(); ++i) {
            if (((PropertyDefinition)this.definitions.properties.elementAt(i)).isMandatory) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean hasValue(final int n) {
        return this.values[n].hasLocalValue || (this.prototype != null && this.prototype.hasValue(n));
    }
    
    public boolean hasValue(final String s) throws XPropertyDoesNotExist {
        return this.hasValue(this.definitions.indexOf(s));
    }
    
    protected IPropertyValueSet locationOfValue(final int i) throws XPropertyHasNoValue {
        if (this.values[i].hasLocalValue) {
            return this;
        }
        if (this.prototype != null) {
            return this.prototype.locationOfValue(i);
        }
        throw new XPropertyHasNoValue("" + i);
    }
    
    public IPropertyValueSet locationOfValue(final String s) throws XPropertyHasNoValue, XPropertyDoesNotExist {
        return this.locationOfValue(this.definitions.indexOf(s));
    }
    
    public int indexOf(final String s) throws XPropertyDoesNotExist {
        return this.definitions.indexOf(s);
    }
    
    public static void load(final IPropertyValueSet set, final PropertyTransferObject propertyTransferObject) throws XPropertyException {
        String string = "";
        try {
            final Enumeration<PropertyTransferObject.Element> elements = propertyTransferObject.elements().elements();
            while (elements.hasMoreElements()) {
                String key = null;
                try {
                    final PropertyTransferObject.Element element = elements.nextElement();
                    key = element.key();
                    if (key.equals(TProp.propName)) {}
                    final PropertyDefinition propertyDefinition = (PropertyDefinition)set.getDefinition(key);
                    final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(set.getValue(key));
                    final String[] array = element.values();
                    if (datatypeModel instanceof IDatatypeComposite) {
                        if (datatypeModel instanceof IDatatypeSingular) {
                            final IDatatypeSingular datatypeSingular = (IDatatypeSingular)datatypeModel;
                            datatypeSingular.setValue(DatatypeModelMapping.getDatatypeModel(propertyDefinition.baseDatatypeInstance()).toObject(array[0]));
                            set.setValue(key, datatypeSingular);
                        }
                        else {
                            final IDatatypeMultiple datatypeMultiple = (IDatatypeMultiple)datatypeModel;
                            final IPropertyDatatype datatypeModel2 = DatatypeModelMapping.getDatatypeModel(propertyDefinition.baseDatatypeInstance());
                            if (array != null) {
                                for (int i = 0; i < array.length; ++i) {
                                    final Object object = datatypeModel2.toObject(array[i]);
                                    if (i == 0) {
                                        datatypeMultiple.setValues(new Object[] { object });
                                    }
                                    else {
                                        datatypeMultiple.addValue(object);
                                    }
                                }
                            }
                            set.setValue(key, datatypeMultiple);
                        }
                    }
                    else {
                        if (array == null) {
                            continue;
                        }
                        if (array.length <= 0) {
                            continue;
                        }
                        set.setValue(key, datatypeModel.toObject(array[0]));
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    string += key;
                }
            }
        }
        catch (Exception previous) {
            final XPropertyException ex2 = new XPropertyException("Failed loading properties.  Errors to this point::\n   " + string);
            ex2.setPrevious(previous);
            throw ex2;
        }
        if (string.length() > 0 && TProp.production) {
            throw new XPropertyException("Failed loading the following properties:\n   " + string);
        }
    }
    
    public void load(final IPropertyManagerRemote propertyManagerRemote, final String s) throws XPropertyException, RemoteException {
        load(this, propertyManagerRemote.makePropertyTransferObject(s));
    }
    
    void dumpI() {
        System.out.println("---------------------------------------");
        System.out.println("Dump of " + this);
        System.out.println("  definitions = " + this.definitions);
        System.out.println("  values = " + this.values);
        System.out.println("  prototype = " + this.prototype);
        System.out.println("  proteges       = " + this.proteges);
        System.out.println("  componentName = " + this.componentName);
        System.out.println("---------------------------------------");
    }
    
    void dumpValues() {
        System.out.println("---------------------------------------");
        System.out.println("Dump of values of" + this);
        System.out.println("  #values = " + this.values.length);
        for (int i = 0; i < this.values.length; ++i) {
            final PropertyValue propertyValue = this.values[i];
            System.out.println("  name = " + ((PropertyDefinition)this.definitions.properties.elementAt(i)).name);
            if (propertyValue == null) {
                System.out.println("  PV is null!");
            }
            else {
                System.out.println("  localValue = " + propertyValue.localValue);
                System.out.println("  hasLocalValue = " + propertyValue.hasLocalValue);
            }
        }
        System.out.println("---------------------------------------");
    }
    
    public void dump() {
        System.out.println("---------------------------------------");
        System.out.println("Dump of values of " + this.name());
        System.out.println("  #values = " + this.values.length + "\n");
        for (int i = 0; i < this.values.length; ++i) {
            final PropertyValue propertyValue = this.values[i];
            final PropertyDefinition propertyDefinition = this.definitions.properties.elementAt(i);
            System.out.print("  " + propertyDefinition.name + " = ");
            Object value = null;
            try {
                value = this.getValue(i);
            }
            catch (Throwable t) {
                Tools.px(t);
            }
            if (value != null) {
                try {
                    System.out.print(this.getValueAsString(propertyDefinition.name()));
                    if (propertyValue == null || !propertyValue.hasLocalValue) {
                        System.out.print(" (delegated)");
                    }
                    System.out.println("");
                }
                catch (Exception ex) {
                    System.out.println("*** Error dumping property: " + propertyDefinition.name());
                }
            }
            else {
                System.out.println("<null>");
            }
        }
        System.out.println("---------------------------------------");
    }
    
    private void setFullInstanceName() {
        if (this.m_fullName == null) {
            if (this.instanceName == null) {
                this.m_fullName = this.groupName;
            }
            else {
                this.m_fullName = this.groupName + "." + this.instanceName;
            }
        }
    }
}
