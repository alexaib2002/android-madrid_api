package org.dam.datosmadrid;

import android.util.Log;

import androidx.annotation.NonNull;

import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultCallback implements Callback<MadridQueryResult> {
    private Consumer<MadridQueryResult> onResponse;

    public ResultCallback(Consumer<MadridQueryResult> onResponse) {
        this.onResponse = onResponse;
    }

    @Override
    public void onResponse(@NonNull Call<MadridQueryResult> call,
                           @NonNull Response<MadridQueryResult> response) {
        if (!response.isSuccessful()) {
            Log.e("API_REQ_ERROR", String.format("%s - %s", response.code(),
                    response.message()));
            return;
        }
        MadridQueryResult result = response.body();
        if (result == null)
            return;
        onResponse.accept(result);
    }

    @Override
    public void onFailure(@NonNull Call<MadridQueryResult> call,
                          @NonNull Throwable t) {
        Log.e("API_REQ_ERROR", t.getMessage());
    }
}
