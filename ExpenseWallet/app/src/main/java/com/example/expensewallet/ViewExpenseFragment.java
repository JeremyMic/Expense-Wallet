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
import android.widget.Toast;

import java.util.ArrayList;

public class ViewExpenseFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Record> records;
    RecordAdapter recordAdapter;
    DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        records = new ArrayList<>();
        db = new DBHelper(getContext());

        records = db.getRecordType("Expense");

        if(records.isEmpty()){
            Toast.makeText(getContext(), "Empty Record!", Toast.LENGTH_SHORT).show();
        }

        recordAdapter = new RecordAdapter(getContext(),records);
        recyclerView.setAdapter(recordAdapter);
    }
}