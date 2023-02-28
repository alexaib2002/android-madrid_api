package org.dam.datosmadrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import org.dam.datosmadrid.retrofitdata.Graph;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Graph> institutions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateMainFragment(ListViewFragment.newInstance(institutions));
        findViewById(R.id.btnFilter).setOnClickListener(v -> {
            // call API with filters
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItemList) {
            updateMainFragment(ListViewFragment.newInstance(institutions));
        } else if (item.getItemId() == R.id.menuItemMap) {
            // TODO implement map
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMainFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, frag);
        transaction.commit();
    }
}