package com.Rapih.id.Model;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberTextWatcher implements TextWatcher {

    private EditText et_filed;

    private String processed = "";


    public NumberTextWatcher(EditText et_filed) {
        this.et_filed = et_filed;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String initial = editable.toString();

        if (et_filed == null) return;
        if (initial.isEmpty()) return;
        String cleanString = initial.replace( ".", "");

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        nf.setGroupingUsed(true);

        double myNumber = new Double(cleanString);

        processed = nf.format(myNumber);

        //Remove the listener
        et_filed.removeTextChangedListener(this);

        //Assign processed text
        et_filed.setText(processed);

        try {
            et_filed.setSelection(processed.length());
        } catch (Exception e) {
        }

        //Give back the listener
        et_filed.addTextChangedListener(this);
    }
}
