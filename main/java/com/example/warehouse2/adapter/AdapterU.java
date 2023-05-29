package com.example.warehouse2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse2.R;
import com.example.warehouse2.db.DbManager.ManagerUnit;

import java.util.ArrayList;
import java.util.List;

public class AdapterU extends RecyclerView.Adapter<AdapterU.NewViewHolder> {
    private final Context context;
    private final List<ListItemU> array;

    public AdapterU(Context context) {
        this.context = context;
        array = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_latout, parent, false);
        return new AdapterU.NewViewHolder(view, context, array);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
        holder.setData(array.get(position).getUnit_measurement());
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    static class NewViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvUnit;
        private final List<ListItemU> array;
        private final Context context;

        public NewViewHolder(@NonNull View itemView, Context context, List<ListItemU> array) {
            super(itemView);
            tvUnit = itemView.findViewById(R.id.tvTitle);
            this.array = array;
            this.context = context;
        }

        public void setData(String unit) {
            tvUnit.setText(unit);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<ListItemU> newList) {
        array.clear();
        array.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, ManagerUnit dbManager) {
        dbManager.deleteItem(array.get(position).getId());
        array.remove(position);
        notifyItemChanged(0, array.size());
        notifyItemRemoved(position);
    }
}
