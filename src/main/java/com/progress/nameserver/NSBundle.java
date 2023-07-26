// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import com.progress.international.resources.TranslatableString;
import java.util.Locale;
import com.progress.international.resources.ProgressResources;

public class NSBundle extends ProgressResources
{
    double versionNumber;
    Locale myLocale;
    static final Object[][] contents;
    
    public NSBundle() {
        this.versionNumber = 1.0;
    }
    
    public Object[][] getContents() {
        return NSBundle.contents;
    }
    
    static {
        contents = new Object[][] { { "UUID", new TranslatableString("UUID", "The UUID for the broker as reflected in the properties file.", 0.0, true) }, { "Name", new TranslatableString("Name", "The Name for the broker as reflected in the properties file.", 0.0, true) }, { "Host", new TranslatableString("Host", "The Host for the broker as reflected in the properties file.", 0.0, true) }, { "Port", new TranslatableString("Port", "The Port for the broker as reflected in the properties file.", 0.0, true) }, { "Weight", new TranslatableString("Weight", "The Weight for the broker as reflected in the properties file.", 0.0, true) }, { "Timeout", new TranslatableString("Timeout", "The Timeout for the broker as reflected in the properties file.", 0.0, true) }, { "StartTime", new TranslatableString("Start Time", "The time that the NameServer was started.", 0.0, true) }, { "Name", new TranslatableString("Name", "The name of the NameServer.", 0.0, true) }, { "NumAppServices", new TranslatableString("Num AppServices", "The number of registered application services for this NameServer.", 0.0, true) }, { "NumBrokers", new TranslatableString("Num Brokers", "The number of registered brokers for this NameServer.", 0.0, true) } };
    }
}
