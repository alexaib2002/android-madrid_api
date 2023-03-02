package org.dam.datosmadrid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.dam.datosmadrid.retrofitdata.Graph;

public class DetailActivity extends AppCompatActivity {

    private Graph detailedInstitution;

    private TextView textViewDetailTitle;
    private TextView textViewDetailLocality;
    private TextView textViewDetailPostalCode;
    private TextView textViewDetailStreet;
    private TextView textViewDetailOrganizationDesc;
    private TextView textViewDetailAccessLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
        textViewDetailLocality = findViewById(R.id.textViewDetailLocality);
        textViewDetailPostalCode = findViewById(R.id.textViewDetailPostalCode);
        textViewDetailStreet = findViewById(R.id.textViewDetailStreet);
        textViewDetailOrganizationDesc = findViewById(R.id.textViewDetailOrganizationDesc);
        textViewDetailAccessLevel = findViewById(R.id.textViewDetailAccessLevel);

        String[] urlSplit = getIntent().getStringExtra(ListViewFragment.EXTRA_URL).split("/");
        String jsonUrl = urlSplit[urlSplit.length - 1];
        // Initialize detailedInstitution inside lambda
        ApiRestServices.getMadridResultService()
                .queryResult(jsonUrl)
                .enqueue(new ResultCallback(result -> {
                    if (result.graph.size() != 1)
                        System.err.println(getResources().getString(R.string
                                .err_unexpected_query_size));
                    this.detailedInstitution = result.graph.get(0);
                    this.textViewDetailTitle.setText(detailedInstitution.getTitle());
                    this.textViewDetailLocality.setText(detailedInstitution.address.locality);
                    this.textViewDetailPostalCode.setText(detailedInstitution.address.postalCode);
                    this.textViewDetailStreet.setText(detailedInstitution.address.streetAddress);
                    this.textViewDetailOrganizationDesc.setText(detailedInstitution.organization.organizationDesc);
                    this.textViewDetailAccessLevel.setText(detailedInstitution.organization.accesibility);
                }));
    }
}