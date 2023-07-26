// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;
import java.util.Enumeration;
import com.progress.chimera.common.Tools;
import java.util.Hashtable;

public class DatatypeModelMapping extends Hashtable
{
    protected static DatatypeModelMapping self;
    private static Class superclass;
    
    Object get(final Class clazz) {
        final Enumeration<Class> keys = this.keys();
        while (keys.hasMoreElements()) {
            final Class key = keys.nextElement();
            if (Tools.isaSubtype(clazz, key)) {
                return super.get(key);
            }
        }
        return null;
    }
    
    protected DatatypeModelMapping() {
        try {
            this.insert(String.class, StringSurrogate.class);
            this.insert(Boolean.class, BooleanSurrogate.class);
            this.insert(Integer.class, IntegerSurrogate.class);
            this.insert(Long.class, LongSurrogate.class);
            this.insert(Short.class, ShortSurrogate.class);
            this.insert(Float.class, FloatSurrogate.class);
            this.insert(Double.class, DoubleSurrogate.class);
            this.insert(Enumeration.class, EnumerationSurrogate.class);
            this.insert(Vector.class, VectorSurrogate.class);
            this.insert(Object[].class, ArraySurrogate.class);
        }
        catch (Throwable t) {
            Tools.px(t, "Can't create DatatypeModelMapping");
        }
    }
    
    public static IPropertyDatatype getDatatypeModel(final Object o) {
        if (Tools.isaInstance(o, DatatypeModelMapping.superclass)) {
            return (IPropertyDatatype)o;
        }
        return getDatatypeModelMapping().findModel(o);
    }
    
    public static DatatypeModelMapping getDatatypeModelMapping() {
        if (DatatypeModelMapping.self == null) {
            DatatypeModelMapping.self = new DatatypeModelMapping();
        }
        return DatatypeModelMapping.self;
    }
    
    protected void insert(final Class clazz, final Class clazz2) {
        try {
            this.put(clazz, clazz2);
        }
        catch (Throwable t) {
            Tools.px(t, "Can't create DatatypeModelMapping for " + clazz + "/" + clazz2);
        }
    }
    
    public static Object makeInstance(final Class obj) throws InstantiationException, XPropertyException {
        final Class clazz = (Class)getDatatypeModelMapping().get(obj);
        try {
            if (clazz != null) {
                return clazz.newInstance().makeInstance();
            }
            try {
                return obj.newInstance();
            }
            catch (NoSuchMethodError noSuchMethodError) {
                throw new XPropertyException("No default constuctor for type: " + obj);
            }
        }
        catch (IllegalAccessException ex) {
            Tools.px(ex, "Can't create model in DatatypeModelMapping");
            throw new InstantiationException();
        }
    }
    
    IPropertyDatatype findModel(final Object baseValueField) {
        Class<?> class1 = baseValueField.getClass();
        if (baseValueField instanceof Object[]) {
            class1 = Object[].class;
        }
        final Class clazz = (Class)this.get(class1);
        DatatypeSurrogate datatypeSurrogate = null;
        try {
            if (clazz != null) {
                datatypeSurrogate = clazz.newInstance();
                datatypeSurrogate.setBaseValueField(baseValueField);
            }
        }
        catch (Throwable t) {
            Tools.px(t, "Can't create model in DatatypeModelMapping");
        }
        return datatypeSurrogate;
    }
    
    static {
        DatatypeModelMapping.self = null;
        DatatypeModelMapping.superclass = IPropertyDatatype.class;
    }
}
