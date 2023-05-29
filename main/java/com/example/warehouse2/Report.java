package com.example.warehouse2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouse2.adapter.AdapterR;
import com.example.warehouse2.adapter.ListItemR;
import com.example.warehouse2.db.DbManager.ManagerEN;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<String> spinnerArrayList;
    private ArrayAdapter adapter;
    private AdapterR adapterR;
    private ManagerNomenclature managerNomenclature;
    private ManagerEN managerEN;
    private TextView textCount, textUnit;
    private RecyclerView list_for_report;
    private List<ListItemR> listItemR, listItemR2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setStatusBarColor(R.color.peach2);

        init();
        forSpinner();
    }

    private void init(){
        adapterR = new AdapterR(this);
        managerNomenclature = new ManagerNomenclature(this);
        managerEN = new ManagerEN(this);
        spinner = findViewById(R.id.spinner2);
        spinnerArrayList = new ArrayList<>();
        textCount = findViewById(R.id.textCount);
        textUnit = findViewById(R.id.textUnit);
        list_for_report = findViewById(R.id.list_for_report);
        list_for_report.setLayoutManager(new LinearLayoutManager(this));
        list_for_report.setAdapter(adapterR);
        listItemR = new ArrayList<>();
        listItemR2 = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        managerNomenclature.openDb();

    }

    private void forSpinner(){
        forSpinnerDesign();

        spinnerArrayList.add("Выберите позицию");
        spinnerArrayList.addAll(managerNomenclature.getTitleForSpinner());
        spinnerArrayList.add("Все");
        listItemR.addAll(managerEN.getForListItems());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinner.getSelectedItem().equals("Все")) {
                            if (listItemR.size() == 0){
                                Toast.makeText(getBaseContext(), "Пусто", Toast.LENGTH_SHORT).show();
                            }
                            textCount.setVisibility(View.GONE);
                            textUnit.setVisibility(View.GONE);
                            adapterR.updateAdapter(listItemR);
                            list_for_report.setAdapter(adapterR);
                            list_for_report.setVisibility(View.VISIBLE);
                        } else {
                            textCount.setVisibility(View.VISIBLE);
                            textUnit.setVisibility(View.VISIBLE);
                            listItemR2.clear();
                            listItemR2.addAll(managerEN.getForItem(spinner.getSelectedItemPosition()));
                            adapterR.updateAdapter(listItemR2);
                            list_for_report.setAdapter(adapterR);
                            list_for_report.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    private void forSpinnerDesign() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }

            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.GRAY);
                }
                else { textView.setTextColor(Color.BLACK); }
                return view;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerNomenclature.closeDb();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void setStatusBarColor(int color) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { return; }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, color));
    }


}