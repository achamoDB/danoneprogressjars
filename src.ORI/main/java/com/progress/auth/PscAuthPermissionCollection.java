// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.auth;

import java.util.Enumeration;
import java.security.Permission;
import java.util.Vector;
import java.io.Serializable;
import java.security.PermissionCollection;

final class PscAuthPermissionCollection extends PermissionCollection implements Serializable
{
    private Vector m_permissions;
    private boolean m_all_allowed;
    
    public PscAuthPermissionCollection() {
        this.m_permissions = new Vector();
        this.m_all_allowed = false;
    }
    
    public void add(final Permission permission) {
        if (!(permission instanceof PscAuthPermission)) {
            throw new IllegalArgumentException("invalid permission: " + permission);
        }
        if (this.isReadOnly()) {
            throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
        }
        final PscAuthPermission pscAuthPermission = (PscAuthPermission)permission;
        this.m_permissions.add(permission);
        if (!this.m_all_allowed && pscAuthPermission.getName().equals("*")) {
            this.m_all_allowed = true;
        }
    }
    
    public boolean canDo(final Permission permission, final boolean b) {
        boolean b2 = false;
        if (permission instanceof PscAuthPermission) {
            final int grantActionMask = ((PscAuthPermission)permission).getGrantActionMask();
            if (this.m_all_allowed) {
                b2 = true;
            }
            else {
                for (int size = this.m_permissions.size(), i = 0; i < size; ++i) {
                    boolean b3 = false;
                    boolean b4 = false;
                    final PscAuthPermission pscAuthPermission = this.m_permissions.elementAt(i);
                    if (b) {
                        if (pscAuthPermission.matchGrantAction(grantActionMask)) {
                            b3 = true;
                            b4 = true;
                        }
                        else if (pscAuthPermission.isActionDenied(grantActionMask)) {
                            b3 = true;
                            b4 = false;
                        }
                    }
                    else if (pscAuthPermission.isActionDenied(grantActionMask)) {
                        b3 = true;
                        b4 = false;
                    }
                    else if (pscAuthPermission.matchGrantAction(grantActionMask)) {
                        b3 = true;
                        b4 = true;
                    }
                    if (b3 && pscAuthPermission.matchPermissionName(permission.getName())) {
                        b2 = b4;
                        break;
                    }
                }
            }
        }
        return b2;
    }
    
    public boolean implies(final Permission permission) {
        return this.canDo(permission, false);
    }
    
    public Enumeration elements() {
        return this.m_permissions.elements();
    }
}
