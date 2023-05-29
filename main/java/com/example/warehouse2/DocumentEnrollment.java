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

import com.example.warehouse2.adapter.AdapterEN;
import com.example.warehouse2.adapter.ListItemN;
import com.example.warehouse2.db.AppExecutor;
import com.example.warehouse2.db.DbManager.ManagerDoc1;
import com.example.warehouse2.db.DbManager.ManagerEN;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;
import com.example.warehouse2.db.OnDataReceived;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentEnrollment extends AppCompatActivity implements OnDataReceived {
    private Spinner spinner;
    private ArrayList<String> spinnerArrayList;
    private ArrayAdapter adapter;
    private ManagerNomenclature managerNomenclature;
    private ManagerDoc1 managerDoc1;
    private ManagerEN managerEN;
    private TextView numberDoc;
    private EditText editTextDate;
    private RecyclerView list_of_num;
    private AdapterEN adapterEN;
    private List<String> listItemEN, listItemN2, listItemC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_enrollment);

        init();
        textNumber();
        textDate();
        forSpinner();
    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        managerNomenclature = new ManagerNomenclature(this);
        managerDoc1 = new ManagerDoc1(this);
        managerEN = new ManagerEN(this);
        spinnerArrayList = new ArrayList<>();
        editTextDate = findViewById(R.id.editTextDate);
        numberDoc = findViewById(R.id.numberDoc);
        list_of_num = findViewById(R.id.list_of_num);
        list_of_num.setLayoutManager(new LinearLayoutManager(this));
        listItemEN = new ArrayList<>();
        listItemN2 = new ArrayList<>();
        listItemC = new ArrayList<>();
        removeItemRecyclerView().attachToRecyclerView(list_of_num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        managerDoc1.openDb();
        managerEN.openDb();
        managerNomenclature.openDb();
    }

    private void textNumber() {
        String number = managerEN.getNumber();
        numberDoc.setText(number);
    }

    private void textDate() {
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

                        list_of_num.setAdapter(adapterEN);
                        adapterEN.updateAdapter(listItemEN, listItemC);

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

    private void itemsRecyclerView(String selectedItemText, String value) {
        if (listItemEN.size() == 0) {
            listItemEN.add(0, selectedItemText + ",  " + value);
        } else {
            listItemEN.add(listItemEN.size(), selectedItemText + ",  " + value);
        }
        listItemN2.add(selectedItemText);

        for (int i = 0; i < listItemEN.size(); i++) {
            listItemC.add("0");
        }

        adapterEN = new AdapterEN(DocumentEnrollment.this, new AdapterEN.TextChangeCallback() {
            @Override
            public void textChangedAt(int position, String text) {
                listItemC.set(position, text);
            }
        });
    }

    public void onClickSave(View v) throws ParseException {
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
            int intermediate_number = managerDoc1.compareNumbers(number);

            if (intermediate_number == 0) {
                for (int i = 0; i < listItemEN.size(); i++) {
                    String element = listItemN2.get(i);
                    String countN = listItemC.get(i);
                    int idN = spinnerArrayList.indexOf(element);
                    managerEN.insertToDb(idN, Integer.parseInt(countN), number);
                    managerDoc1.insertToDb(number, val1);
                    makeText(this, R.string.saved, LENGTH_SHORT).show();
                }
            } else {
                makeText(this, R.string.reminder5, LENGTH_SHORT).show();
            }
        }
    }

    public void onClickHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerEN.closeDb();
        managerDoc1.closeDb();
    }

    private ItemTouchHelper removeItemRecyclerView() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(DocumentEnrollment.this);
                builder.setTitle("Удаление элемента");
                builder.setPositiveButton("Продолжить", (dialog, which) -> {
                    adapterEN.remove(position);
                    listItemEN.remove(position);
                    listItemC.remove(position);
                    listItemN2.remove(position);
                });
                builder.setNegativeButton("Отмена", (dialog, which) -> {
                    adapterEN.notifyDataSetChanged();
                });
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onReceived(List<ListItemN> listItemNS) {
        AppExecutor.getInstance().getMainIO().execute(new Runnable() {
            @Override
            public void run() {
                adapterEN.updateAdapter(listItemEN, listItemC);
            }
        });
    }
}

