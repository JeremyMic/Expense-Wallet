package com.example.expensewallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRecordFragment extends Fragment {

    Spinner spinner;
    String[] type = {"Choose Type:", "Expense","Income"};
    EditText recordNameInput, recordAmountInput;
    Button addRecord;
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recordNameInput = view.findViewById(R.id.recordName);
        recordAmountInput = view.findViewById(R.id.recordAmount);
        spinner = view.findViewById(R.id.recordType);
        addRecord = view.findViewById(R.id.addRecord);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        db = new DBHelper(getContext());

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = recordNameInput.getText().toString();
                String amount = recordAmountInput.getText().toString();
                String type = spinner.getSelectedItem().toString();

                if(name.isEmpty()){
                    recordNameInput.setError("Name Cannot be Empty");
                    return;
                }

                if(amount.isEmpty()){
                    recordAmountInput.setError("Amount must be more than 0!");
                    return;
                }

                if(type.equals("Choose Type:")){
                    Toast.makeText(getContext(), "Must Choose Expense or Income!", Toast.LENGTH_SHORT).show();
                    return;
                }

                db.addRecord(name,Integer.parseInt(amount),type);
                Toast.makeText(getContext(), "Record Added!", Toast.LENGTH_SHORT).show();
                recordNameInput.setText("");
                recordAmountInput.setText("");
            }
        });



    }
}