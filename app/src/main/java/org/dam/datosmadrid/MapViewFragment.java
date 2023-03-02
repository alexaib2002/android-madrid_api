package org.dam.datosmadrid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dam.datosmadrid.retrofitdata.Graph;

import java.util.ArrayList;

public class MapViewFragment extends Fragment implements UpdatableDatasetHolder {
    protected SupportMapFragment map;
    private ArrayList<Graph> institutions;

    public static MapViewFragment newInstance(ArrayList<Graph> institutions) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putSerializable(ARG_PARAM_INST, institutions);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            institutions = (ArrayList<Graph>) getArguments().getSerializable(ARG_PARAM_INST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        return v;
    }

    @Override
    public Runnable getDatasetUpdate() {
        return () -> map.getMapAsync(googleMap -> {
            googleMap.clear();
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            LatLng pos;
            for (Graph institution: institutions) {
                pos = new LatLng(institution.location.latitude,
                        institution.location.longitude);
                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(institution.title));
                boundsBuilder.include(pos);
            }
            googleMap.animateCamera(CameraUpdateFactory
                    .newLatLngBounds(boundsBuilder.build(), getResources()
                            .getInteger(R.integer.map_pad)));
        });
    }
}