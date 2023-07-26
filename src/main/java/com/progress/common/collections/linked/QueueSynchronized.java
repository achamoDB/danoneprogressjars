// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class QueueSynchronized extends OrderedList
{
    public boolean insertAtHead(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == null) {
            return false;
        }
        while (true) {
            break Label_0012;
            try {
                while (true) {
                    synchronized (super.last) {
                        // monitorenter(this)
                        try {
                            return this.addLast(o);
                        }
                        catch (NullPointerException ex) {
                        }
                        // monitorexit(this)
                    }
                }
            }
            catch (NullPointerException ex2) {
                synchronized (this) {
                    if (super.last != null) {
                        continue;
                    }
                    return this.addLast(o);
                }
            }
            break;
        }
    }
    
    public boolean insert(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == null) {
            return false;
        }
        while (true) {
            break Label_0012;
            try {
                while (true) {
                    synchronized (super.first) {
                        // monitorenter(this)
                        try {
                            return this.addFirst(o);
                        }
                        catch (NullPointerException ex) {
                        }
                        // monitorexit(this)
                    }
                }
            }
            catch (NullPointerException ex2) {
                synchronized (this) {
                    if (super.first != null) {
                        continue;
                    }
                    return this.addFirst(o);
                }
            }
            break;
        }
    }
    
    private OlElem extract2() {
        try {
            synchronized (super.last.prev) {
                synchronized (this) {
                    super.last.prev.next = null;
                    return super.last.prev;
                }
            }
        }
        catch (NullPointerException ex) {
            return null;
        }
    }
    
    public Object extract() {
        OlElem last = null;
        try {
            last = null;
            synchronized (super.last) {
                last = super.last;
                final OlElem extract2 = this.extract2();
                super.last = extract2;
                if (extract2 == null) {
                    super.first = null;
                }
            }
        }
        catch (NullPointerException ex) {}
        catch (Throwable t) {
            System.out.println("222");
        }
        if (last != null) {
            return last.value;
        }
        return null;
    }
    
    public boolean remove(final OlElem olElem) {
        while (true) {
            if (olElem.next == null) {
                synchronized (olElem) {
                    if (olElem.removed) {
                        return true;
                    }
                    if (olElem.prev == null) {
                        synchronized (this) {
                            return super.remove(olElem);
                        }
                    }
                    synchronized (olElem.prev) {
                        synchronized (this) {
                            return super.remove(olElem);
                        }
                    }
                }
            }
            try {
                synchronized (olElem.next) {
                    try {
                        synchronized (olElem) {
                            if (olElem.removed) {
                                return true;
                            }
                            if (olElem.prev == null) {
                                synchronized (this) {
                                    super.remove(olElem);
                                }
                            }
                            else {
                                synchronized (olElem.prev) {
                                    synchronized (this) {
                                        super.remove(olElem);
                                    }
                                }
                            }
                        }
                    }
                    catch (NullPointerException ex) {}
                }
            }
            catch (NullPointerException ex2) {}
        }
    }
}
