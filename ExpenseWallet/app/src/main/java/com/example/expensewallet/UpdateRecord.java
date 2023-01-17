package com.example.expensewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateRecord extends AppCompatActivity {
    Spinner spinner;
    String[] types = {"Choose Type:", "Expense","Income"};
    EditText recordNameInput, recordAmountInput;
    Button updateRecord;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        recordNameInput = findViewById(R.id.recordNameUpdate);
        recordAmountInput = findViewById(R.id.recordAmountUpdate);
        spinner = findViewById(R.id.recordTypeUpdate);
        updateRecord = findViewById(R.id.updateRecord);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        db = new DBHelper(getApplicationContext());

        int id = getIntent().getIntExtra("id",0);
        String nameUpdate = getIntent().getStringExtra("name");
        int amountUpdate = getIntent().getIntExtra("amount",0);

        recordNameInput.setText(nameUpdate);
        recordAmountInput.setText(amountUpdate+"");

        updateRecord.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "Must Choose Expense or Income!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(UpdateRecord.this, "Record Updated!", Toast.LENGTH_SHORT).show();
                db.updateRecord(id,name,amount,type);

                Intent kembali = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(kembali);
            }
        });

    }
}