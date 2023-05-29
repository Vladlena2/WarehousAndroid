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
import android.widget.SearchView;

import com.example.warehouse2.adapter.ListItemN;
import com.example.warehouse2.adapter.AdapterN;
import com.example.warehouse2.db.AppExecutor;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;
import com.example.warehouse2.db.OnDataReceived;

import java.util.List;

public class Ware extends AppCompatActivity implements OnDataReceived {
    private ManagerNomenclature DBManager;
    private RecyclerView rcView;
    private AdapterN adapterN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware);

        init();
        onCrateOptionSearch();

    }


    private void init(){
        DBManager = new ManagerNomenclature(this);
        rcView = findViewById(R.id.list_of_nomenclature);
        adapterN = new AdapterN(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(adapterN);
    }

    private void onCrateOptionSearch(){
        SearchView search_product = findViewById(R.id.search_product);
        search_product.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                readFromDb(newText);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBManager.openDb();
        readFromDb("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.closeDb();
    }

    public void toAddWare(View v){
        Intent i = new Intent(this, com.example.warehouse2.AddWare.class);
        startActivity(i);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Ware.this);
                builder.setTitle("Удаление элемента");
                builder.setPositiveButton("Продолжить", (dialog, which) -> {
                    adapterN.removeItem(viewHolder.getAdapterPosition(), DBManager);
                });
                builder.setNegativeButton("Отмена", (dialog, which) -> {adapterN.notifyDataSetChanged();});
                builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    public void onClickHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void readFromDb(final String text){
        AppExecutor.getInstance().getSubIO().execute(new Runnable() {
            @Override
            public void run() {
                DBManager.getFromDb(text, Ware.this);
            }
        });
    }

    @Override
    public void onReceived(List<ListItemN> listItemNS) {
        AppExecutor.getInstance().getMainIO().execute(new Runnable() {
            @Override
            public void run() {
                adapterN.updateAdapter(listItemNS);
            }
        });
    }

}