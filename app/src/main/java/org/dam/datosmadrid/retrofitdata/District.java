
package org.dam.datosmadrid.retrofitdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class District implements Serializable
{

    @SerializedName("@id")
    @Expose
    public String id;
    private final static long serialVersionUID = 3066860030750309186L;

}
