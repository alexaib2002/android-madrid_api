package org.dam.datosmadrid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.dam.datosmadrid.retrofitdata.Graph;
import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import java.util.ArrayList;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Graph> institutions = new ArrayList<>();

    private Button btnFilter;
    private Button btnQuery;
    private TextView textViewFilter;
    private ConstraintLayout mainLayout;

    // Runs every time dataset gets updated,
    // notifies the current fragment to update its displayed data
    private Runnable datasetUpdater;
    // Lambda containing the API call,
    // changed based on the filter values
    private Call<MadridQueryResult> apiCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateMainFragment(ListViewFragment.newInstance(institutions));
        btnFilter = findViewById(R.id.btnFilter);
        btnQuery = findViewById(R.id.btnQuery);
        textViewFilter = findViewById(R.id.textViewFilter);
        mainLayout = findViewById(R.id.mainLayout);
        setApiCall(null);
        setFilterText("");

        btnFilter.setOnClickListener(v -> {
            FilterDialog filterDialog = new FilterDialog(
                    this::setApiCall,
                    this::setFilterText,
                    this::getMainLayout);
            filterDialog.show(getSupportFragmentManager(), null);
        });
        btnQuery.setOnClickListener(v -> retrieveData());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItemList) {
            updateMainFragment(ListViewFragment.newInstance(institutions));
            btnQuery.setText(R.string.str_list_query);
        } else if (item.getItemId() == R.id.menuItemMap) {
            updateMainFragment(MapViewFragment.newInstance(institutions));
            btnQuery.setText(R.string.str_map_query);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setApiCall(@Nullable Call<MadridQueryResult> apiCall) {
        this.apiCall = apiCall != null ? apiCall : ApiRestServices
                .getMadridResultService().queryResult();
    }

    public void setFilterText(String text) {
        if (text.isEmpty())
            textViewFilter.setVisibility(View.GONE);
        else
            textViewFilter.setVisibility(View.VISIBLE);
        textViewFilter.setText(text);
    }

    public ConstraintLayout getMainLayout() {
        return mainLayout;
    }

    private void updateMainFragment(@NonNull Fragment frag) {
        assert frag instanceof UpdatableDatasetHolder;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragContainer, frag);
        transaction.commit();
        // Safely run after onCreation method of Fragment has been called
        transaction.runOnCommit(() -> {
            datasetUpdater = ((UpdatableDatasetHolder) frag).getDatasetUpdate();
            if (institutions.size() > 0)
                datasetUpdater.run();
        });
    }

    private void retrieveData() {
        try {
            apiCall.clone().enqueue(new ResultCallback((result) -> {
                this.institutions.clear();
                this.institutions.addAll(result.graph);
                if (this.institutions.size() > 0)
                    this.datasetUpdater.run();
                else
                    Snackbar.make(mainLayout, R.string.err_no_data_found, Snackbar.LENGTH_SHORT)
                            .show();
            }));
        } catch (IllegalStateException e) {
            Snackbar.make(mainLayout, R.string.err_already_executed, Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

}