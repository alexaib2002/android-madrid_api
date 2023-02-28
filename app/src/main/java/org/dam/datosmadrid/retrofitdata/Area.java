
package org.dam.datosmadrid.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Area implements Serializable
{

    @SerializedName("@id")
    @Expose
    public String id;
    private final static long serialVersionUID = -429180674268758680L;

}
