package org.dam.datosmadrid.retrofitutils;

import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRestService extends Serializable {
    String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("203166-0-universidades-educacion.json")
    Call<MadridQueryResult> queryResult(@Query("latitud") double lat,
                                  @Query("longitud") double lon,
                                  @Query("distancia") int dist);

    @GET("203166-0-universidades-educacion.json")
    Call<MadridQueryResult> queryResult();

    @GET("tipo/entidadesyorganismos/{id_url}")
    Call<MadridQueryResult> queryResult(@Path("id_url") String url);
}
