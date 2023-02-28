
package org.dam.datosmadrid.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Graph implements Serializable
{

    @SerializedName("@id")
    @Expose
    public String idJson;
    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("relation")
    @Expose
    public String relation;
    @SerializedName("address")
    @Expose
    public Address address;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("organization")
    @Expose
    public Organization organization;
    private final static long serialVersionUID = -9193037023312278036L;

    public String getTitle() {
        return title;
    }

    public Address getAddress() {
        return address;
    }
}
