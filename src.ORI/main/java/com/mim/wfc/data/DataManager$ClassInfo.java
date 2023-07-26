// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.core.IProperties;
import com.ms.wfc.core.MemberAttribute;
import com.ms.wfc.core.DescriptionAttribute;
import com.ms.wfc.core.DefaultValueAttribute;
import com.ms.wfc.core.CategoryAttribute;
import com.ms.wfc.core.IEvents;
import com.ms.wfc.core.EventInfo;
import com.ms.wfc.core.PropertyInfo;
import com.ms.wfc.core.Component$ClassInfo;

public class DataManager$ClassInfo extends Component$ClassInfo
{
    public static final PropertyInfo dataSource;
    public static final PropertyInfo tableName;
    public static final PropertyInfo fields;
    public static final PropertyInfo modified;
    public static final PropertyInfo autoSelectMatch;
    public static final PropertyInfo boundForm;
    public static final PropertyInfo orderByClause;
    public static final PropertyInfo whereClause;
    public static final PropertyInfo debug;
    public static final EventInfo addingRecord;
    public static final EventInfo recordAdded;
    public static final EventInfo copyingRecord;
    public static final EventInfo recordCopied;
    public static final EventInfo updatingRecord;
    public static final EventInfo recordUpdated;
    public static final EventInfo deletingRecord;
    public static final EventInfo recordDeleted;
    public static final EventInfo updatingForm;
    public static final EventInfo formUpdated;
    public static final EventInfo autoSelectStart;
    public static final EventInfo autoSelectExecuted;
    public static final EventInfo autoSelectAborted;
    public static final EventInfo saveModified;
    private static Class \u00e6;
    private static Class \u00e7;
    private static Class \u00e8;
    private static Class \u00e9;
    private static Class \u00ea;
    private static Class \u00e4;
    private static Class \u00eb;
    private static Class \u00ec;
    
    public void getEvents(final IEvents events) {
        super.getEvents(events);
        events.add(DataManager$ClassInfo.addingRecord);
        events.add(DataManager$ClassInfo.recordAdded);
        events.add(DataManager$ClassInfo.copyingRecord);
        events.add(DataManager$ClassInfo.recordCopied);
        events.add(DataManager$ClassInfo.updatingRecord);
        events.add(DataManager$ClassInfo.recordUpdated);
        events.add(DataManager$ClassInfo.deletingRecord);
        events.add(DataManager$ClassInfo.recordDeleted);
        events.add(DataManager$ClassInfo.updatingForm);
        events.add(DataManager$ClassInfo.formUpdated);
        events.add(DataManager$ClassInfo.autoSelectStart);
        events.add(DataManager$ClassInfo.autoSelectExecuted);
        events.add(DataManager$ClassInfo.autoSelectAborted);
        events.add(DataManager$ClassInfo.saveModified);
    }
    
    static {
        dataSource = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "dataSource", (DataManager$ClassInfo.\u00e7 != null) ? DataManager$ClassInfo.\u00e7 : (DataManager$ClassInfo.\u00e7 = \u00c6("com.ms.wfc.data.ui.DataSource")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Data source associated with the component"));
        tableName = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "tableName", (DataManager$ClassInfo.\u00e8 != null) ? DataManager$ClassInfo.\u00e8 : (DataManager$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Name of the table or view associated with the component"));
        fields = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "fields", (DataManager$ClassInfo.\u00e9 != null) ? DataManager$ClassInfo.\u00e9 : (DataManager$ClassInfo.\u00e9 = \u00c6("[Lcom.mim.wfc.data.DataManagerField;")), (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Database field list"));
        modified = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "modified", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Data, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Is true if the record was modified and not yet updated"));
        autoSelectMatch = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "autoSelectMatch", (Class)Boolean.TYPE, (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.FALSE, (MemberAttribute)new DescriptionAttribute("Determines the processing of string fields in auto select\nIf the property is true, the character '%' will be appended to the end of the string and the like predicate will be used in the query"));
        boundForm = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "boundForm", (DataManager$ClassInfo.\u00ea != null) ? DataManager$ClassInfo.\u00ea : (DataManager$ClassInfo.\u00ea = \u00c6("com.ms.wfc.ui.Control")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Container control associated with the manager\nTypically this is a Form or a GroupBox."));
        orderByClause = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "orderByClause", (DataManager$ClassInfo.\u00e8 != null) ? DataManager$ClassInfo.\u00e8 : (DataManager$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Defines a SQL order by clause that will be used when executing SQL select statements\nThe clause consists of a comma-separated field list without the 'order by' keywords."));
        whereClause = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "whereClause", (DataManager$ClassInfo.\u00e8 != null) ? DataManager$ClassInfo.\u00e8 : (DataManager$ClassInfo.\u00e8 = \u00c6("java.lang.String")), (MemberAttribute)CategoryAttribute.Behavior, (MemberAttribute)DefaultValueAttribute.NULL, (MemberAttribute)new DescriptionAttribute("Defines a SQL where clause that will be used when executing SQL select statements\nIn auto select mode, this clause will be prepended to the generated where clause."));
        debug = new PropertyInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "debug", (DataManager$ClassInfo.\u00e4 != null) ? DataManager$ClassInfo.\u00e4 : (DataManager$ClassInfo.\u00e4 = \u00c6("com.mim.wfc.data.DataManagerDebug")), (MemberAttribute)new DefaultValueAttribute((Object)new Integer(0)), (MemberAttribute)new DescriptionAttribute("Enables or disables debug output of SQL statements executed by the manager"));
        addingRecord = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "addingRecord", (DataManager$ClassInfo.\u00eb != null) ? DataManager$ClassInfo.\u00eb : (DataManager$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)new DescriptionAttribute("A new record will be added to the database"));
        recordAdded = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "recordAdded", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("A new record was added to the database"));
        copyingRecord = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "copyingRecord", (DataManager$ClassInfo.\u00eb != null) ? DataManager$ClassInfo.\u00eb : (DataManager$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)new DescriptionAttribute("The current record will be added to the database as a new record"));
        recordCopied = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "recordCopied", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The current record was copied to the database as a new record"));
        updatingRecord = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "updatingRecord", (DataManager$ClassInfo.\u00eb != null) ? DataManager$ClassInfo.\u00eb : (DataManager$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)new DescriptionAttribute("A record will be updated in the database"));
        recordUpdated = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "recordUpdated", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("A record was updated in the database"));
        deletingRecord = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "deletingRecord", (DataManager$ClassInfo.\u00eb != null) ? DataManager$ClassInfo.\u00eb : (DataManager$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)new DescriptionAttribute("A record will be deleted from the database"));
        recordDeleted = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "recordDeleted", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("A record was deleted from the database"));
        updatingForm = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "updatingForm", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The controls bound to the record will be updated"));
        formUpdated = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "formUpdated", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The controls bound to the record were updated"));
        autoSelectStart = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "autoSelectStart", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The auto select mode was activated"));
        autoSelectExecuted = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "autoSelectExecuted", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The auto select command was executed"));
        autoSelectAborted = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "autoSelectAborted", (DataManager$ClassInfo.\u00ec != null) ? DataManager$ClassInfo.\u00ec : (DataManager$ClassInfo.\u00ec = \u00c6("com.ms.wfc.core.EventHandler")), (MemberAttribute)new DescriptionAttribute("The auto select mode was cancelled"));
        saveModified = new EventInfo((DataManager$ClassInfo.\u00e6 != null) ? DataManager$ClassInfo.\u00e6 : (DataManager$ClassInfo.\u00e6 = \u00c6("com.mim.wfc.data.DataManager")), "saveModified", (DataManager$ClassInfo.\u00eb != null) ? DataManager$ClassInfo.\u00eb : (DataManager$ClassInfo.\u00eb = \u00c6("com.ms.wfc.core.CancelEventHandler")), (MemberAttribute)new DescriptionAttribute("A record needs to be saved"));
    }
    
    private static Class \u00c6(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    public void getProperties(final IProperties properties) {
        super.getProperties(properties);
        properties.add(DataManager$ClassInfo.dataSource);
        properties.add(DataManager$ClassInfo.tableName);
        properties.add(DataManager$ClassInfo.fields);
        properties.add(DataManager$ClassInfo.modified);
        properties.add(DataManager$ClassInfo.autoSelectMatch);
        properties.add(DataManager$ClassInfo.boundForm);
        properties.add(DataManager$ClassInfo.orderByClause);
        properties.add(DataManager$ClassInfo.whereClause);
        properties.add(DataManager$ClassInfo.debug);
    }
    
    public String getDefaultPropertyName() {
        return new String("fields");
    }
}
