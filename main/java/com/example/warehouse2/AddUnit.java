package com.example.warehouse2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.warehouse2.adapter.AdapterU;
import com.example.warehouse2.db.DbManager.ManagerUnit;

public class AddUnit extends AppCompatActivity {
    private RecyclerView list_of_unit;
    private AdapterU adapterU;
    private ManagerUnit managerUnit;
    private EditText etForInputUnit;
    private SearchView search_unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        init();
        onCrateOptionSearch();

    }

    private void init(){
        list_of_unit = findViewById(R.id.list_of_unit);
        adapterU = new AdapterU(this);
        list_of_unit.setLayoutManager(new LinearLayoutManager(this));
        list_of_unit.setAdapter(adapterU);
        etForInputUnit = findViewById(R.id.etForInputUnit);
        managerUnit = new ManagerUnit(this);
        search_unit = findViewById(R.id.search_unit);
        getItemTouchHelper().attachToRecyclerView(list_of_unit);
    }

    private void onCrateOptionSearch(){
        search_unit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                adapterU.updateAdapter(managerUnit.getFromDb(newText));
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        managerUnit.openDb();
        adapterU.updateAdapter(managerUnit.getFromDb(""));
    }

    public void onClickSaveNewUnit(View v){
        managerUnit.insertToDb(etForInputUnit.getText().toString());
        adapterU.updateAdapter(managerUnit.getFromDb(""));
    }

    public void onClickHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerUnit.closeDb();
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddUnit.this);
                builder.setTitle("Удаление элемента");
                builder.setPositiveButton("Продолжить", (dialog, which) -> {
                    adapterU.removeItem(viewHolder.getAdapterPosition(), managerUnit);
                });
                builder.setNegativeButton("Отмена", (dialog, which) -> {adapterU.notifyDataSetChanged();});
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}