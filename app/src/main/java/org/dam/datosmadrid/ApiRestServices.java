package org.dam.datosmadrid;

import org.dam.datosmadrid.retrofitutils.ApiRestService;
import org.dam.datosmadrid.retrofitutils.RetrofitClient;

public class ApiRestServices {
    private static final ApiRestService madridResultService = RetrofitClient
            .getClient(ApiRestService.BASE_URL)
            .create(ApiRestService.class);

    public static ApiRestService getMadridResultService() {
        return madridResultService;
    }
}
