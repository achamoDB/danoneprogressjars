// 
// Decompiled by Procyon v0.5.36
// 

package com.danone2.library;

public class User
{
    private Integer id;
    private String name;
    
    public User() {
    }
    
    public User(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
