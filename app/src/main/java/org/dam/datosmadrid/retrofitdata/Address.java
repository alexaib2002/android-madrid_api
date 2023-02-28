
package org.dam.datosmadrid.retrofitdata;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Serializable
{

    @SerializedName("district")
    @Expose
    public District district;
    @SerializedName("area")
    @Expose
    public Area area;
    @SerializedName("locality")
    @Expose
    public String locality;
    @SerializedName("postal-code")
    @Expose
    public String postalCode;
    @SerializedName("street-address")
    @Expose
    public String streetAddress;
    private final static long serialVersionUID = -4485074295657904124L;

    @Override
    public String toString() {
        return String.format("%s %s %s", streetAddress, postalCode, locality);
    }
}
