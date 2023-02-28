package org.dam.datosmadrid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dam.datosmadrid.retrofitdata.Graph;
import org.dam.datosmadrid.retrofitdata.MadridQueryResult;
import org.dam.datosmadrid.retrofitutils.ApiRestService;
import org.dam.datosmadrid.retrofitutils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListViewFragment extends Fragment {
    private RecyclerView recyclerView;
    private InstitutionRecyclerViewAdapter adapter;
    private ArrayList<Graph> institutions;

    private static final String ARG_PARAM_INST = "INSTITUTIONS";

    public static ListViewFragment newInstance(ArrayList<Graph> institutions) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_INST, institutions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            institutions = (ArrayList<Graph>) getArguments().getSerializable(ARG_PARAM_INST);
            adapter = new InstitutionRecyclerViewAdapter(institutions);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        retrieveData();
        return v;
    }

    private void retrieveData() {
        Retrofit retrofit = RetrofitClient.getClient(ApiRestService.BASE_URL);
        ApiRestService apiRestService = retrofit.create(ApiRestService.class);
        Call<MadridQueryResult> call = apiRestService.queryResult();

        call.enqueue(new Callback<MadridQueryResult>() {
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
                institutions.clear();
                institutions.addAll(result.graph);
                adapter.notifyDataSetChanged();
                System.out.println("Everything was updated");
            }

            @Override
            public void onFailure(@NonNull Call<MadridQueryResult> call,
                                  @NonNull Throwable t) {
                Log.e("API_REQ_ERROR", t.getMessage());
            }
        });
    }
}