// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.util.Hashtable;

public interface IMetaSchema
{
    public static final String ATTR_PROP_FULL_NAME = "fullname";
    public static final String ATTR_PROP_GROUP = "group";
    public static final String ATTR_PROP_SCHEMA_GROUP = "schemagroup";
    public static final String ATTR_PROP_TYPE = "type";
    public static final String ATTR_PROP_NAME = "name";
    public static final String ATTR_PROP_DISPLAY_NAME = "displayname";
    public static final String ATTR_PROP_HIDDEN = "hidden";
    public static final String ATTR_PROP_ISMANDATORY = "ismandatory";
    public static final String ATTR_PROP_CONTROL_TYPE = "controltype";
    public static final String ATTR_PROP_VALIDATION_METHOD = "validationmethod";
    public static final String ATTR_PROP_CHOICES_METHOD = "choicesmethod";
    public static final String ATTR_PROP_CATEGORY_TYPE = "categorytype";
    public static final String ATTR_PROP_TAG = "tag";
    public static final String ATTR_PROP_ISARRAY = "isarray";
    public static final String ATTR_PROP_ISARRAY_SEPARATOR = "isarrayseparator";
    public static final String ATTR_PROP_CHOICES = "choices";
    public static final String ATTR_PROP_DEFAULT_CHOICE_INDEX = "defaultchoiceindex";
    public static final String ATTR_PROP_DEFAULT_CHOICE = "defaultchoice";
    public static final String ATTR_PROP_DEFAULT_VALUE = "defaultvalue";
    public static final String ATTR_PROP_MIN_VALUE = "minvalue";
    public static final String ATTR_PROP_MAX_VALUE = "maxvalue";
    public static final String ATTR_PROP_GROUP_SUBSUME = "subsume";
    public static final String ATTR_PROP_GROUP_PREFIX = "prefix";
    public static final String ATTR_PROP_GROUP_VALIDATE = "validate";
    public static final String ATTR_CAT_DISPLAY_NAME = "displayname";
    public static final String ATTR_CAT_PARENT = "parent";
    public static final String ATTR_CAT_FIELDS = "fields";
    public static final String ATTR_GRP_DISPLAY_NAME = "displayname";
    public static final String ATTR_GRP_CATEGORIES = "categories";
    
    Hashtable getGroupSchemaHashtable();
    
    Hashtable getGroupAttributeHashtable(final String p0);
    
    Hashtable getPropertySchemaHashtable(final String p0);
    
    Hashtable getCategorySchemaHashtable(final String p0);
    
    Hashtable getCategorySchemaHashtable(final String p0, final String p1);
    
    Hashtable getCategorySchemaHashtable(final String p0, final String[] p1);
    
    Hashtable getCategoryAttributeHashtable(final String p0);
    
    Hashtable makePropertyValueHash(final String p0);
}
