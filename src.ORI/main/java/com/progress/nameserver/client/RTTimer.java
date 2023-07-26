// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.client;

public class RTTimer
{
    private static final int RTT_RTOMIN = 2000;
    private static final int RTT_RTOMAX = 60000;
    private int rtt;
    private int srtt;
    private int rttvar;
    private int rto;
    
    RTTimer() {
        this.reinit();
    }
    
    synchronized void reinit() {
        this.rtt = 0;
        this.srtt = 0;
        this.rttvar = 750;
        this.rtoCalc();
    }
    
    int getRTO() {
        return this.rto;
    }
    
    synchronized void timeout() {
        this.rto *= 2;
        this.rtoMinMax();
    }
    
    private synchronized void rtoCalc() {
        this.rto = this.srtt + 4 * this.rttvar;
        this.rtoMinMax();
    }
    
    private void rtoMinMax() {
        if (this.rto < 2000) {
            this.rto = 2000;
        }
        else if (this.rto > 60000) {
            this.rto = 60000;
        }
    }
}
