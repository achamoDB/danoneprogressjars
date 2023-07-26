// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

public interface MProservJuniperAPI
{
    public static final String mProservPropertySeparator = ";";
    public static final int defaultMProservPort = 7833;
    public static final String mnRegistrationAcknowledged = "REGA";
    public static final String mnRegistrationDenied = "REGD";
    public static final String mrAdminServerShutdown = "ADSD";
    public static final String mrShutdown = "SHDN";
    public static final String mrServerGroupQuery = "SGR?";
    public static final String mrBIQuery = "BIU?";
    public static final String mrAIQuery = "AIU?";
    public static final String mrWatchdogQuery = "WDU?";
    public static final String mrAPWQuery = "AWU?";
    public static final String mrContinuationQuery = "CON?";
    public static final String mrDBAQuery = "DAU?";
    public static final String mrDBAgentShutdown = "DARS";
    public static final String mrReplServerQuery = "RSU?";
    public static final String mrReplAgentQuery = "RAU?";
    public static final String mrReplControlAgentQuery = "RCU?";
    public static final String mnStarted = "STRD";
    public static final String mnRunning = "RUNG";
    public static final String mnShutdownBegun = "BSHU";
    public static final String mnContinue = "CONT";
    public static final String mnServerGroupUp = "SGRN";
    public static final String mnBIUp = "BIRN";
    public static final String mnAIUp = "AIRN";
    public static final String mnWatchdogUp = "WDRN";
    public static final String mnAPWsUp = "AWRN";
    public static final String mnServerGroupCrash = "SGCR";
    public static final String mnBICrash = "BICR";
    public static final String mnAICrash = "AICR";
    public static final String mnWatchdogCrash = "WDCR";
    public static final String mnAPWCrash = "AWCR";
    public static final String mnUserRequestedShutdown = "URSD";
    public static final String mnAbnormalGracefulShutdown = "AGSD";
    public static final String mnAbnormalForcedShutdown = "AFSD";
    public static final String mrReplServerUp = "RSRN";
    public static final String mrReplServerUnknown = "RSCR";
    public static final String mrReplAgentUP = "RARN";
    public static final String mrReplAgentUnknown = "RACR";
    public static final String mrReplControlAgentUP = "RCRN";
    public static final String mrReplControlAgentUnknown = "RCCR";
    public static final String mnReplAgentShutdownNormalNotif = "RASD";
    public static final String mnReplAgentShutdownEmergencyNotif = "ARAD";
    public static final String mnReplAgentDisconnectNotif = "URAD";
    public static final String mnReplShutdownErrNotif = "ARID";
    public static final String mnReplAutoTransitionNotif = "RSTA";
    public static final String mnReplManualTransitionNotif = "RSTM";
    public static final String mnReplInrecoveryNotif = "RSIR";
    public static final String mnReplAgentConnLostNotif = "ARCL";
    public static final String mnDBAgentUP = "DARN";
    public static final String mnDBAgentDisconnectNotif = "DASD";
    public static final String mnDBAgentCrash = "DACR";
    public static final String mrLoadTestPlugin = "TEST";
    public static final String mrTestPluginLoaded = "TSTA";
    public static final String mrTestPluginNotLoaded = "TSTD";
}
