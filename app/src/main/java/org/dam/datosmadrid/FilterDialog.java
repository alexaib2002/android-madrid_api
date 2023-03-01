package org.dam.datosmadrid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import java.util.function.Consumer;

import retrofit2.Call;

public class FilterDialog extends DialogFragment {
    protected TextView editTextLat;
    protected TextView editTextLon;
    protected TextView editTextDist;

    private FilterAcceptListener acceptListener;
    private Consumer<Call<MadridQueryResult>> madridQueryResultSetter;
    private Consumer<String> filterTextSetter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_filter_dialog,
                null);
        editTextLat = v.findViewById(R.id.editTextLat);
        editTextLon = v.findViewById(R.id.editTextLon);
        editTextDist = v.findViewById(R.id.editTextDist);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_filter_dialog)
                .setView(v)
                .setPositiveButton(getResources().getText(R.string.filter_apply_btn), null)
                .setNegativeButton(getResources().getString(R.string.filter_cancel_btn),
                        (dialog, which) -> dialog.dismiss())
                .show();
        acceptListener = new FilterAcceptListener();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(acceptListener);
        return alertDialog;
    }

    public void updateCallables(MainActivity mainActivity) {
        madridQueryResultSetter = mainActivity::setApiCall;
        filterTextSetter = mainActivity::setFilterText;
    }

    private class FilterAcceptListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (editTextLat.getText().toString().isEmpty() && editTextLon.getText().toString()
                    .isEmpty() && editTextDist.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Utilizando filtro por defecto",
                        Toast.LENGTH_SHORT).show();
                madridQueryResultSetter.accept(null);
                filterTextSetter.accept("");
                dismiss();
                return;
            }
            if (editTextLat.getText().toString().isEmpty()) {
                editTextLat.setError("La latitud no puede estar vacía");
                return;
            }
            if (editTextLon.getText().toString().isEmpty()) {
                editTextLon.setError("La longitud no puede estar vacía");
                return;
            }
            if (editTextDist.getText().toString().isEmpty()) {
                editTextDist.setError("La distancia no puede estar vacía");
                return;
            }
            Call<MadridQueryResult> call;
            try {
                call = ApiRestServices.getMadridResultService().queryResult(
                        Double.parseDouble(editTextLat.getText().toString()),
                        Double.parseDouble(editTextLon.getText().toString()),
                        Integer.parseInt(editTextDist.getText().toString()));
            } catch (NumberFormatException e) {
                Snackbar.make(getView(), "Error al parsear los números", Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }
            madridQueryResultSetter.accept(call);
            filterTextSetter.accept(String.format("Latitud:\t%s%nLongitud:\t%s%nDistancia:\t%s",
                    editTextLat.getText().toString(),
                    editTextLon.getText().toString(),
                    editTextDist.getText().toString()));
            dismiss();
        }
    }
}