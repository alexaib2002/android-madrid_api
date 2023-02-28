
package org.dam.datosmadrid.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable
{

    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("longitude")
    @Expose
    public Double longitude;
    private final static long serialVersionUID = 4394699908082696130L;

}
