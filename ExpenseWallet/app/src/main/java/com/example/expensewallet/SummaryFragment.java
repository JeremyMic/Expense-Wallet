package com.example.expensewallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SummaryFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Record> records;
    RecordAdapter recordAdapter;
    DBHelper db;
    TextView expense, income;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        expense.setText(expenseSum);
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expense = view.findViewById(R.id.expense);
        income = view.findViewById(R.id.income);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        records = new ArrayList<>();
        db = new DBHelper(getContext());

        records = db.getAllRecord();

        if(records.isEmpty()){
            Toast.makeText(getContext(), "Empty Record!", Toast.LENGTH_SHORT).show();
        }

        recordAdapter = new RecordAdapter(getContext(),records);
        recyclerView.setAdapter(recordAdapter);

        int totalExpense = 0;
        int totalIncome = 0;
        for (Record record: records) {
            if(record.getType().equals("Expense")){
                totalExpense += record.getAmount();
            }else{
                totalIncome += record.getAmount();
            }
        }

        expense.setText("Total Expense: Rp."+totalExpense + ",-");
        income.setText("Total Income: Rp." +totalIncome + ",-");
    }
}