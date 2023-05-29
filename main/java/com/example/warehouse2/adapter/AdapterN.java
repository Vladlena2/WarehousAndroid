package com.example.warehouse2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse2.AddWare;
import com.example.warehouse2.R;
import com.example.warehouse2.db.DbContract.ContractNomenclature;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;

import java.util.ArrayList;
import java.util.List;


public class AdapterN extends RecyclerView.Adapter<AdapterN.MyViewHolder> {
    private final Context context;
    private final List<ListItemN> mainArray;

    public AdapterN(Context context) {
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_latout, parent, false);
        return new MyViewHolder(view, context, mainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final Context context;
        private final TextView tvTitle;
        private final List<ListItemN> mainArray;
        private final ManagerNomenclature DBManager;

        public MyViewHolder(@NonNull View itemView, Context context, List<ListItemN> mainArray) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            this.context = context;
            this.mainArray = mainArray;
            itemView.setOnClickListener(this);
            DBManager = new ManagerNomenclature(context.getApplicationContext());
        }

        public void setData(String title) {
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, AddWare.class);
            i.putExtra(ContractNomenclature.LIST_ITEM_INTENT, mainArray.get(getAdapterPosition()));
            i.putExtra(ContractNomenclature.EDIT_STATE, false);
            context.startActivity(i);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<ListItemN> newList) {
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, ManagerNomenclature dbManager) {
        dbManager.deleteItem(mainArray.get(position).getId());
        mainArray.remove(position);
        notifyItemChanged(0, mainArray.size());
        notifyItemRemoved(position);
    }

}
