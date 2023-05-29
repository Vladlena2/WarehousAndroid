package com.example.warehouse2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse2.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterEN extends RecyclerView.Adapter<AdapterEN.NewViewHolder> {

    private final Context context;
    private final List<String> array;
    private final List<String> array2;
    TextChangeCallback callback;

    public AdapterEN(Context context, TextChangeCallback callback) {
        this.context = context;
        array = new ArrayList<>();
        array2 = new ArrayList<>();
        this.callback = callback;
    }

    @NonNull
    @Override //обрабатывает изменение макета карты в качестве элемента для RecyclerView.
    public AdapterEN.NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doc, parent, false);
        return new AdapterEN.NewViewHolder(view, context, array, array2);
    }


    @Override
    //занимается настройкой различных данных и методов, связанных с кликами по определенным элементам RecyclerView.
    public void onBindViewHolder(@NonNull NewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(array.get(position));
        holder.countN.setText(array2.get(position));
        holder.countN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = String.valueOf(s);
                if (start == 0) {
                    array2.add(value);
                } else {
                    array2.remove(array2.size() - 1);
                    array2.add(value);
                }
                callback.textChangedAt(holder.getAdapterPosition(), array2.get(array2.size() - 1));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    static class NewViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final EditText countN;
        private final List<String> array;
        private final List<String> array2;
        private final Context context;

        public NewViewHolder(@NonNull View itemView, Context context, List<String> array, List<String> array2) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            countN = itemView.findViewById(R.id.countN);
            this.array = array;
            this.array2 = array2;
            this.context = context;

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<String> newList, List<String> newList2) {
        array.clear();
        array.addAll(newList);
        array2.clear();
        array2.addAll(newList2);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void remove(int position) {
        array.remove(position);
        array2.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public interface TextChangeCallback {
        void textChangedAt(int position, String text);
    }
}


