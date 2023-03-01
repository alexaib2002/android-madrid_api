package org.dam.datosmadrid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dam.datosmadrid.retrofitdata.Graph;

import java.util.ArrayList;

public class ListViewFragment extends Fragment implements UpdatableDatasetHolder {
    private static final String ARG_PARAM_INST = "INSTITUTIONS";

    private RecyclerView recyclerView;
    private InstitutionRecyclerViewAdapter adapter;
    private ArrayList<Graph> institutions;

    public static ListViewFragment newInstance(@NonNull ArrayList<Graph> institutions) {
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
            adapter = new InstitutionRecyclerViewAdapter(institutions, v -> {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("url", institutions.get(recyclerView
                        .getChildAdapterPosition(v)).getIdJson());
                startActivity(intent);
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public Runnable getDatasetUpdate() {
        return adapter::notifyDataSetChanged;
    }
}