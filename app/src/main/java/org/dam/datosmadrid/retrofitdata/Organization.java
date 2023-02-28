
package org.dam.datosmadrid.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Organization implements Serializable
{

    @SerializedName("organization-desc")
    @Expose
    public String organizationDesc;
    @SerializedName("accesibility")
    @Expose
    public String accesibility;
    @SerializedName("schedule")
    @Expose
    public String schedule;
    @SerializedName("services")
    @Expose
    public String services;
    @SerializedName("organization-name")
    @Expose
    public String organizationName;
    private final static long serialVersionUID = 8925982061765857572L;

}
