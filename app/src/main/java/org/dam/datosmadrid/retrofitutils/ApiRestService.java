package org.dam.datosmadrid.retrofitutils;

import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRestService {
    String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("203166-0-universidades-educacion.json")
    Call<MadridQueryResult> queryResult(@Query("latitud") String lat,
                                  @Query("longitud") String lon,
                                  @Query("distancia") String dist);

    @GET("203166-0-universidades-educacion.json")
    Call<MadridQueryResult> queryResult();
}
