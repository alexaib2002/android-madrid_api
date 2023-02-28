
package org.dam.datosmadrid.retrofitdata;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MadridQueryResult implements Serializable
{

    @SerializedName("@context")
    @Expose
    public Context context;
    @SerializedName("@graph")
    @Expose
    public List<Graph> graph;
    private final static long serialVersionUID = 5306781941834740778L;

}
