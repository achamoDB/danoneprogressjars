// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

class SocketTimeout extends Thread
{
    private boolean alive;
    TimeoutEntry[] time_list;
    int current;
    
    SocketTimeout(final int n) {
        super("SocketTimeout");
        this.alive = true;
        try {
            this.setDaemon(true);
        }
        catch (SecurityException ex) {}
        this.setPriority(10);
        this.time_list = new TimeoutEntry[n];
        for (int i = 0; i < n; ++i) {
            this.time_list[i] = new TimeoutEntry(null);
            final TimeoutEntry timeoutEntry = this.time_list[i];
            final TimeoutEntry timeoutEntry2 = this.time_list[i];
            final TimeoutEntry timeoutEntry3 = this.time_list[i];
            timeoutEntry2.prev = timeoutEntry3;
            timeoutEntry.next = timeoutEntry3;
        }
        this.current = 0;
    }
    
    public TimeoutEntry setTimeout(final StreamDemultiplexor streamDemultiplexor) {
        final TimeoutEntry timeoutEntry = new TimeoutEntry(streamDemultiplexor);
        synchronized (this.time_list) {
            timeoutEntry.next = this.time_list[this.current];
            timeoutEntry.prev = this.time_list[this.current].prev;
            timeoutEntry.prev.next = timeoutEntry;
            timeoutEntry.next.prev = timeoutEntry;
        }
        return timeoutEntry;
    }
    
    public void run() {
        TimeoutEntry next = null;
        while (this.alive) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            synchronized (this.time_list) {
                for (TimeoutEntry timeoutEntry = this.time_list[this.current].next; timeoutEntry != this.time_list[this.current]; timeoutEntry = timeoutEntry.next) {
                    timeoutEntry.restart = false;
                }
                ++this.current;
                if (this.current >= this.time_list.length) {
                    this.current = 0;
                }
                for (TimeoutEntry timeoutEntry2 = this.time_list[this.current].next; timeoutEntry2 != this.time_list[this.current]; timeoutEntry2 = timeoutEntry2.next) {
                    if (timeoutEntry2.alive && !timeoutEntry2.hyber) {
                        final TimeoutEntry prev = timeoutEntry2.prev;
                        timeoutEntry2.kill();
                        timeoutEntry2.next = next;
                        next = timeoutEntry2;
                        timeoutEntry2 = prev;
                    }
                }
            }
            while (next != null) {
                next.demux.markForClose(null);
                next = next.next;
            }
        }
    }
    
    public void kill() {
        this.alive = false;
    }
    
    class TimeoutEntry
    {
        boolean restart;
        boolean hyber;
        boolean alive;
        StreamDemultiplexor demux;
        TimeoutEntry next;
        TimeoutEntry prev;
        
        TimeoutEntry(final StreamDemultiplexor demux) {
            this.restart = false;
            this.hyber = false;
            this.alive = true;
            this.next = null;
            this.prev = null;
            this.demux = demux;
        }
        
        void reset() {
            this.hyber = false;
            if (this.restart) {
                return;
            }
            this.restart = true;
            synchronized (SocketTimeout.this.time_list) {
                if (!this.alive) {
                    return;
                }
                this.next.prev = this.prev;
                this.prev.next = this.next;
                this.next = SocketTimeout.this.time_list[SocketTimeout.this.current];
                this.prev = SocketTimeout.this.time_list[SocketTimeout.this.current].prev;
                this.prev.next = this;
                this.next.prev = this;
            }
        }
        
        void hyber() {
            if (this.alive) {
                this.hyber = true;
            }
        }
        
        void kill() {
            this.alive = false;
            this.restart = false;
            this.hyber = false;
            synchronized (SocketTimeout.this.time_list) {
                if (this.prev == null) {
                    return;
                }
                this.next.prev = this.prev;
                this.prev.next = this.next;
                this.prev = null;
            }
        }
    }
}
