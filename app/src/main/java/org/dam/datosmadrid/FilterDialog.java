package org.dam.datosmadrid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import org.dam.datosmadrid.retrofitdata.MadridQueryResult;

import java.util.function.Consumer;
import java.util.function.Supplier;

import retrofit2.Call;

public class FilterDialog extends DialogFragment {
    protected TextView editTextLat;
    protected TextView editTextLon;
    protected TextView editTextDist;

    private Consumer<Call<MadridQueryResult>> madridQueryResultSetter;
    private Consumer<String> filterTextSetter;
    protected Supplier<ConstraintLayout> mainLayoutGetter;

    public FilterDialog(Consumer<Call<MadridQueryResult>> madridQueryResultSetter, Consumer<String> filterTextSetter, Supplier<ConstraintLayout> mainLayoutGetter) {
        this.madridQueryResultSetter = madridQueryResultSetter;
        this.filterTextSetter = filterTextSetter;
        this.mainLayoutGetter = mainLayoutGetter;
    }

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
                .create();
        alertDialog.setOnShowListener(dialog -> alertDialog
                .getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new FilterAcceptListener()));
        alertDialog.setCanceledOnTouchOutside(false); // modal dialog
        return alertDialog;
    }

    private class FilterAcceptListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (editTextLat.getText().toString().isEmpty() && editTextLon.getText().toString()
                    .isEmpty() && editTextDist.getText().toString().isEmpty()) {
                Snackbar.make(mainLayoutGetter.get(), getResources().getString(R.string
                                .hint_default_filter),
                        Snackbar.LENGTH_SHORT).show();
                madridQueryResultSetter.accept(null);
                filterTextSetter.accept("");
                dismiss();
                return;
            }
            if (editTextLat.getText().toString().isEmpty()) {
                editTextLat.setError(getString(R.string.err_lat_empty));
                return;
            }
            if (editTextLon.getText().toString().isEmpty()) {
                editTextLon.setError(getString(R.string.err_lon_empty));
                return;
            }
            if (editTextDist.getText().toString().isEmpty()) {
                editTextDist.setError(getString(R.string.err_dist_empty));
                return;
            }
            Call<MadridQueryResult> call;
            try {
                call = ApiRestServices.getMadridResultService().queryResult(
                        Double.parseDouble(editTextLat.getText().toString()),
                        Double.parseDouble(editTextLon.getText().toString()),
                        Integer.parseInt(editTextDist.getText().toString()));
            } catch (NumberFormatException e) {
                Snackbar.make(v, getResources().getString(R.string.err_no_parse),
                                Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }
            madridQueryResultSetter.accept(call);
            filterTextSetter.accept(String.format(getString(R.string.filter_str_format),
                    editTextLat.getText().toString(),
                    editTextLon.getText().toString(),
                    editTextDist.getText().toString()));
            dismiss();
        }
    }
}