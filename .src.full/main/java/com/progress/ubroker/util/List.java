// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.exception.ProException;
import com.progress.common.ehnlog.IAppLogger;

public class List
{
    private ListNode firstNode;
    private ListNode lastNode;
    private String name;
    Logger log;
    IAppLogger applog;
    private int listsize;
    
    public List(final String name, final Logger log) {
        this.name = name;
        final ListNode listNode = null;
        this.lastNode = listNode;
        this.firstNode = listNode;
        this.log = log;
        this.applog = null;
        this.listsize = 0;
    }
    
    public List(final String name, final IAppLogger applog) {
        this.name = name;
        final ListNode listNode = null;
        this.lastNode = listNode;
        this.firstNode = listNode;
        this.applog = applog;
        this.log = null;
        this.listsize = 0;
    }
    
    public String toString() {
        return this.name;
    }
    
    public synchronized void insertAtFront(final Object o) {
        if (this.isEmpty()) {
            final ListNode listNode = new ListNode(o);
            this.lastNode = listNode;
            this.firstNode = listNode;
        }
        else {
            this.firstNode = new ListNode(o, this.firstNode);
        }
        ++this.listsize;
    }
    
    public synchronized void insertAtBack(final Object o) {
        if (this.isEmpty()) {
            final ListNode listNode = new ListNode(o);
            this.lastNode = listNode;
            this.firstNode = listNode;
        }
        else {
            final ListNode lastNode = this.lastNode;
            final ListNode listNode2 = new ListNode(o);
            lastNode.next = listNode2;
            this.lastNode = listNode2;
        }
        ++this.listsize;
    }
    
    public synchronized Object removeFromList(final Object o) throws EmptyListException {
        if (this.isEmpty()) {
            throw new EmptyListException(this.name);
        }
        if (o == null) {
            return null;
        }
        if (this.firstNode.data == o) {
            return this.removeFromFront();
        }
        ListNode lastNode;
        for (lastNode = this.firstNode; lastNode != this.lastNode && lastNode.next.data != o; lastNode = lastNode.next) {}
        if (lastNode == this.lastNode) {
            return null;
        }
        final Object data = lastNode.next.data;
        if (lastNode.next == this.lastNode) {
            this.lastNode = lastNode;
        }
        lastNode.next = lastNode.next.next;
        --this.listsize;
        return data;
    }
    
    public synchronized Object removeFromFront() throws EmptyListException {
        if (this.isEmpty()) {
            throw new EmptyListException(this.name);
        }
        final Object data = this.firstNode.data;
        if (this.firstNode.equals(this.lastNode)) {
            final ListNode listNode = null;
            this.lastNode = listNode;
            this.firstNode = listNode;
        }
        else {
            this.firstNode = this.firstNode.next;
        }
        --this.listsize;
        return data;
    }
    
    public synchronized Object removeFromBack() throws EmptyListException {
        if (this.isEmpty()) {
            throw new EmptyListException(this.name);
        }
        final Object data = this.lastNode.data;
        if (this.firstNode.equals(this.lastNode)) {
            final ListNode listNode = null;
            this.lastNode = listNode;
            this.firstNode = listNode;
        }
        else {
            ListNode lastNode;
            for (lastNode = this.firstNode; lastNode.next != this.lastNode; lastNode = lastNode.next) {}
            this.lastNode = lastNode;
            lastNode.next = null;
        }
        --this.listsize;
        return data;
    }
    
    public boolean isEmpty() {
        return this.firstNode == null;
    }
    
    public synchronized Object findFirst() {
        return this.isEmpty() ? null : this.firstNode.data;
    }
    
    public synchronized Object findNext(final Object o) {
        if (this.isEmpty() || o == null) {
            return null;
        }
        ListNode listNode;
        for (listNode = this.firstNode; listNode != null && listNode.data != o; listNode = listNode.next) {}
        if (listNode == null) {
            return null;
        }
        final ListNode next = listNode.next;
        return (next == null) ? null : next.data;
    }
    
    public synchronized Object findPrev(final Object o) {
        if (this.isEmpty() || o == null) {
            return null;
        }
        ListNode listNode;
        for (listNode = this.firstNode; listNode != this.lastNode && listNode.next.data != o; listNode = listNode.next) {}
        return (listNode == this.lastNode) ? null : listNode.data;
    }
    
    public synchronized Object findLast() {
        return this.isEmpty() ? null : this.lastNode.data;
    }
    
    public boolean inList(final Object o) {
        if (this.isEmpty() || o == null) {
            return false;
        }
        ListNode listNode;
        for (listNode = this.firstNode; listNode != null && listNode.data != o; listNode = listNode.next) {}
        return listNode != null;
    }
    
    public synchronized void print(final Logger logger, final int n, final int n2) {
        if (this.isEmpty()) {
            logger.LogMsgln(n, n2, false, "List " + this.name + " is empty");
            return;
        }
        logger.LogMsgln(n, n2, false, "List " + this.name + " is :");
        for (ListNode listNode = this.firstNode; listNode != null; listNode = listNode.next) {
            logger.LogMsgln(n, n2, false, listNode.data.toString() + " : ");
        }
    }
    
    public synchronized void print(final IAppLogger appLogger, final int n, final int n2) {
        if (this.isEmpty()) {
            appLogger.logWithThisLevel(n, n2, "List " + this.name + " is empty");
            return;
        }
        appLogger.logWithThisLevel(n, n2, "List " + this.name + " is :");
        for (ListNode listNode = this.firstNode; listNode != null; listNode = listNode.next) {
            appLogger.logWithThisLevel(n, n2, listNode.data.toString() + " : ");
        }
    }
    
    public String getListName() {
        return this.name;
    }
    
    public int size() {
        return this.listsize;
    }
    
    public static class EmptyListException extends ProException
    {
        public EmptyListException(final String s) {
            super("EmptyListException", new Object[] { s });
        }
        
        public String getDetail() {
            return (String)this.getArgument(0);
        }
    }
}
