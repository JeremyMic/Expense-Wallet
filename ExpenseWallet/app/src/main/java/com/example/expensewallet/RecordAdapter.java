package com.example.expensewallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Record> records;

    public RecordAdapter(Context context, ArrayList<Record> records){
        this.context = context;
        this.records = records;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        Record record = records.get(position);
        holder.recordName.setText(record.getName());
        holder.recordAmount.setText("Rp. "+record.getAmount());
        holder.recordType.setText(record.getType());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindahUpdate = new Intent(context,UpdateRecord.class);
                pindahUpdate.putExtra("id",record.getId());
                context.startActivity(pindahUpdate);
                notifyDataSetChanged();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper db = new DBHelper(context);
                        db.deleteRecord(record.getId());
                        Toast.makeText(context, "Record Deleted!", Toast.LENGTH_SHORT).show();
                        records.remove(record);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView recordName;
        private final TextView recordAmount;
        private final TextView recordType;
        private final Button deleteBtn;
        private final Button updateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordName = itemView.findViewById(R.id.recordNameItem);
            recordAmount = itemView.findViewById(R.id.recordAmountItem);
            recordType = itemView.findViewById(R.id.recordTypeItem);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            updateBtn = itemView.findViewById(R.id.updateBtn);
        }
    }
}
