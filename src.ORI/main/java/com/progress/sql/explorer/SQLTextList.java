// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

import java.io.OptionalDataException;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.io.Serializable;

public class SQLTextList implements Serializable
{
    protected static final String HISTORY_LOG;
    protected int m_listMaxSize;
    protected Vector m_list;
    
    public SQLTextList() {
        this.m_listMaxSize = 100;
        this.m_list = new Vector(this.m_listMaxSize + 1);
    }
    
    public int add(final String s) {
        this.m_list.addElement(s);
        if (this.m_list.size() > this.m_listMaxSize) {
            this.m_list.removeElementAt(0);
        }
        return this.m_list.indexOf(s);
    }
    
    public String get(final int index) {
        String s;
        try {
            s = this.m_list.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            s = null;
        }
        return s;
    }
    
    public String getFirst() throws NoSuchElementException {
        return this.m_list.firstElement();
    }
    
    public String getLast() throws NoSuchElementException {
        return this.m_list.lastElement();
    }
    
    public int getIdx(final String o) {
        return this.m_list.indexOf(o);
    }
    
    public int getFirstIdx() throws NoSuchElementException {
        return this.m_list.indexOf(this.m_list.firstElement());
    }
    
    public int getLastIdx() throws NoSuchElementException {
        if (this.m_list.size() > 0) {
            return this.m_list.indexOf(this.m_list.lastElement());
        }
        return -1;
    }
    
    public int getSize() {
        return this.m_list.size();
    }
    
    public boolean remove(final String obj) {
        return this.m_list.removeElement(obj);
    }
    
    public boolean remove(final int index) {
        try {
            this.m_list.removeElementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }
    
    public void store() throws IOException, FileNotFoundException {
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(SQLTextList.HISTORY_LOG), 131000));
        objectOutputStream.writeObject(this.m_list);
        objectOutputStream.close();
    }
    
    public void load() throws IOException {
        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(SQLTextList.HISTORY_LOG), 131000));
            this.m_list = (Vector)objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException ex) {
            this.m_list = new Vector();
        }
        catch (ClassNotFoundException ex2) {
            this.m_list = new Vector();
        }
        catch (OptionalDataException ex3) {
            this.m_list = new Vector();
        }
    }
    
    static {
        HISTORY_LOG = ISQLConstants.SQL_EXPLORER_HISTORY;
    }
}
