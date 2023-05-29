package com.example.warehouse2;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.warehouse2.adapter.AdapterON;
import com.example.warehouse2.db.DbManager.ManagerDoc2;
import com.example.warehouse2.db.DbManager.ManagerEN;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;
import com.example.warehouse2.db.DbManager.ManagerON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentOffs extends AppCompatActivity {
    private List<String> listItemON, listItemN2, listItemC, spinnerArrayList;
    private Spinner spinner;
    private ArrayAdapter adapter;
    private ManagerNomenclature managerNomenclature;
    private ManagerON managerON;
    private ManagerEN managerEN;
    private TextView numberDoc;
    private ManagerDoc2 managerDoc2;
    private EditText editTextDate;
    private RecyclerView list_of_num;
    private AdapterON adapterON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_offs);

        init();
        textNumber();
        textDate();
        forSpinner();
    }

    private void init() {
        managerDoc2 = new ManagerDoc2(this);
        spinner = findViewById(R.id.spinner);
        managerNomenclature = new ManagerNomenclature(this);
        managerON = new ManagerON(this);
        managerEN = new ManagerEN(this);
        spinnerArrayList = new ArrayList<>();
        numberDoc = findViewById(R.id.numberDoc);
        editTextDate = findViewById(R.id.editTextDate);
        list_of_num = findViewById(R.id.list_of_num);
        list_of_num.setLayoutManager(new LinearLayoutManager(this));
        listItemON = new ArrayList<>();
        listItemN2 = new ArrayList<>();
        listItemC = new ArrayList<>();
        removeItemRecyclerView().attachToRecyclerView(list_of_num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        managerDoc2.openDb();
        managerON.openDb();
    }

    private void textNumber(){
        String number = managerON.getNumber();
        numberDoc.setText(number);
    }

    private void textDate(){
        long date = System.currentTimeMillis();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = sdf.format(date);
        editTextDate.setText(dateString);
    }

    private void forSpinner() {
        forSpinnerDesign();

        spinnerArrayList.add("Выберите позицию");
        spinnerArrayList.addAll(managerNomenclature.getTitleForSpinner());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);
                        String value = managerNomenclature.getTitle(selectedItemText);

                        itemsRecyclerView(selectedItemText, value);

                        list_of_num.setAdapter(adapterON);
                        adapterON.updateAdapter(listItemON, listItemC);

                        removeItemRecyclerView();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        spinner.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void forSpinnerDesign() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

    }

    private void itemsRecyclerView(String selectedItemText, String value){
        if (listItemON.size() == 0) {
            listItemON.add(0, selectedItemText + ",  " + value);
        } else {
            listItemON.add(listItemON.size(), selectedItemText + ",  " + value);
        }
        listItemN2.add(selectedItemText);

        for (int i = 0; i < listItemON.size(); i++) {
            listItemC.add("0");
        }

        adapterON = new AdapterON(DocumentOffs.this, new AdapterON.TextChangeCallback() {
            @Override
            public void textChangedAt(int position, String text) {
                listItemC.set(position, text);
            }
        });
    }

    public void onClickSaveDoc2(View v) throws ParseException {
        int number = 0;

        try {
          number = Integer.parseInt(numberDoc.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String someDate = editTextDate.getText().toString();
        if (someDate.equals("") && number == 0) {
            makeText(this, R.string.reminder3, LENGTH_SHORT).show();
        } else if (someDate.equals("")) {
            makeText(this, R.string.reminder, LENGTH_SHORT).show();
        } else if (number == 0) {
            makeText(this, R.string.reminder2, LENGTH_SHORT).show();
        } else {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = sdf.parse(someDate);
            long val1 = date.getTime();
            int intermediate_number = managerDoc2.compareNumbers(number);

            if (intermediate_number == 0) {

                for (int i = 0; i < listItemON.size(); i++) {
                    String element = listItemN2.get(i);
                    String countN = listItemC.get(i);
                    int idN = spinnerArrayList.indexOf(element);
                    int sumElement = managerEN.checkingNegativeNumber(idN);
                    int result = sumElement - Integer.parseInt(countN);

                    if (result > 0) {
                        managerON.insertToDb(idN, Integer.parseInt(countN), number);
                        managerDoc2.insertToDb(number, val1);
                        makeText(this, R.string.saved, LENGTH_SHORT).show();
                    } else {
                        makeText(this, "На складе недостает " + Math.abs(result) +
                                " (" + listItemON.get(i) + ")", LENGTH_SHORT).show();
                    }

                }
            } else {
                makeText(this, R.string.reminder5, LENGTH_SHORT).show();
            }

        }
    }

    public void onClickHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerDoc2.closeDb();
        managerON.closeDb();
    }

    private ItemTouchHelper removeItemRecyclerView(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(DocumentOffs.this);
                builder.setTitle("Удаление элемента");
                builder.setPositiveButton("Продолжить", (dialog, which) -> {
                    adapterON.remove(position);
                    listItemON.remove(position);
                    listItemC.remove(position);
                    listItemN2.remove(position);
                });
                builder.setNegativeButton("Отмена", (dialog, which) -> {adapterON.notifyDataSetChanged();});
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

}