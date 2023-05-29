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

import java.util.ArrayList;
import java.util.List;

public class AdapterR extends RecyclerView.Adapter<com.example.warehouse2.adapter.AdapterR.NewViewHolder> {
        private final Context context;
        private final List<ListItemR> array;

        public AdapterR(Context context) {
            this.context = context;
            array = new ArrayList<>();
        }

        @NonNull
        @Override
        public com.example.warehouse2.adapter.AdapterR.NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_for_report, parent, false);
            return new com.example.warehouse2.adapter.AdapterR.NewViewHolder(view, context, array);
        }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
            if (array.get(position).getCountR() == 0){
                holder.setData(array.get(position).getTitleR(), String.valueOf(array.get(position).getCount1R()), array.get(position).getUnitR());
            } else {
                holder.setData(array.get(position).getTitleR(), String.valueOf(array.get(position).getCountR()), array.get(position).getUnitR());
            }

    }

        @Override
        public int getItemCount() {
            return array.size();
        }

        static class NewViewHolder extends RecyclerView.ViewHolder{
            private final TextView tvTitle;
            private final TextView tvNum;
            private final TextView tvUnit;
            private final List<ListItemR> array;
            private final Context context;

            public NewViewHolder(@NonNull View itemView, Context context, List<ListItemR> array) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvNum = itemView.findViewById(R.id.tvNum);
                tvUnit = itemView.findViewById(R.id.tvUnit);
                this.array = array;
                this.context = context;
            }

            public void setData(String title, String count, String unit) {
                tvTitle.setText(title);
                tvNum.setText(count);
                tvUnit.setText(unit);
            }

        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateAdapter(List<ListItemR> newList) {
            array.clear();
            array.addAll(newList);
            notifyDataSetChanged();
        }

    }


