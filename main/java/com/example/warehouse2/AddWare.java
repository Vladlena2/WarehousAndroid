package com.example.warehouse2;

import static android.view.View.*;
import static android.widget.Toast.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.warehouse2.adapter.AdapterU;
import com.example.warehouse2.adapter.ListItemN;
import com.example.warehouse2.db.AppExecutor;
import com.example.warehouse2.db.DbContract.ContractNomenclature;
import com.example.warehouse2.db.DbManager.ManagerNomenclature;
import com.example.warehouse2.db.DbManager.ManagerUnit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddWare extends AppCompatActivity {
    private final int PICK_IMAGE_CODE = 123;
    private ImageView imNewImage, ivToUnit;
    private ConstraintLayout imageContainer;
    private FloatingActionButton button_add_image;
    private EditText edTitle, etForInputUnit;
    private ManagerNomenclature managerNomenclature;
    private String tempUri = "empty";
    private boolean isEditState = true;
    private ListItemN listItemN;
    private Spinner spinner;
    private List<String> spinnerArrayList;
    private ArrayAdapter arrayAdapter;
    private SurfaceView sv;
    private FrameLayout add_unit_layout;
    private ImageButton btDeleteUnit, btAddUnit;
    private ManagerUnit managerUnit;
    private AdapterU adapterU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ware);

        init();
        getMyIntents();
        forSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        managerNomenclature.openDb();
    }

    private void init() {
        managerNomenclature = new ManagerNomenclature(this);
        managerUnit = new ManagerUnit(this);
        edTitle = findViewById(R.id.nameTitle);
        imageContainer = findViewById(R.id.imageContainer);
        button_add_image = findViewById(R.id.Button_add_image);
        imNewImage = findViewById(R.id.imNewImage);
        spinner = findViewById(R.id.choosing_units_measurement);
        spinnerArrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        sv = findViewById(R.id.surfaceView);
        add_unit_layout = findViewById(R.id.add_unit_layout);
        btDeleteUnit = findViewById(R.id.btDeleteUnit);
        btAddUnit = findViewById(R.id.btAddUnit);
        etForInputUnit = findViewById(R.id.etForInputUnit);
        ivToUnit = findViewById(R.id.ivToUnit);
        adapterU = new AdapterU(this);
    }

    private void forSpinner() {
        forSpinnerDesign();

        spinnerArrayList.add("Ед. измерения");
        spinnerArrayList.addAll(managerUnit.getUnits()); //добавление значений ед. измерений, которые храняться в бд
        spinnerArrayList.add("Добавить ед.");

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinner.getSelectedItem().toString().equals("Добавить ед.")) {
                            if (sv.getVisibility() == View.GONE) {
                                sv.setVisibility(View.VISIBLE);
                                btAddUnit.setVisibility(VISIBLE);
                                btDeleteUnit.setVisibility(VISIBLE);
                                etForInputUnit.setVisibility(VISIBLE);
                                ivToUnit.setVisibility(VISIBLE);

                                final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_layout);
                                add_unit_layout.startAnimation(show);

                                btAddUnit.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        managerUnit.insertToDb(etForInputUnit.getText().toString());
                                        adapterU.updateAdapter(managerUnit.getFromDb(""));
                                        arrayAdapter.clear();
                                        spinnerArrayList.add("Ед. измерения");
                                        spinnerArrayList.addAll(managerUnit.getUnits());
                                        spinnerArrayList.add("Добавить ед.");
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        } else {
                            hideLayout();
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
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList) {
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

    public void hideLayout() {
        if (sv.getVisibility() == View.VISIBLE) {
            final Animation hide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_layout);
            hide.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    sv.setVisibility(View.GONE);
                    btAddUnit.setVisibility(GONE);
                    btDeleteUnit.setVisibility(GONE);
                    etForInputUnit.setVisibility(GONE);
                    ivToUnit.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            add_unit_layout.startAnimation(hide);
        }
    }

    private void getMyIntents() {
        Intent i = getIntent();
        if (i != null) {
            listItemN = (ListItemN) i.getSerializableExtra(ContractNomenclature.LIST_ITEM_INTENT);
            isEditState = i.getBooleanExtra(ContractNomenclature.EDIT_STATE, true);

            if (!isEditState) {
                edTitle.setText(listItemN.getTitle());
                String value = managerNomenclature.getTitle(listItemN.getTitle());
                spinner.post(new Runnable() {
                    @Override
                    public void run() {
                        int spinnerPosition = arrayAdapter.getPosition(value);
                        spinner.setSelection(spinnerPosition);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });


                if (!listItemN.getUri().equals("empty")) {
                    tempUri = listItemN.getUri();
                    imageContainer.setVisibility(View.VISIBLE);
                    imNewImage.setImageURI(Uri.parse(listItemN.getUri()));
                }
            }
        }
    }


    public void saveNewProduct(View v) {
        final String title = edTitle.getText().toString();
        final String unit_measurement = spinner.getSelectedItem().toString();

        if (title.equals("")) {
            makeText(this, R.string.reminder, LENGTH_SHORT).show();
        } else if (tempUri.equals("empty")) {
            makeText(this, R.string.reminder2, LENGTH_SHORT).show();
        } else if (title.equals("") && tempUri.equals("empty")) {
            makeText(this, R.string.reminder3, LENGTH_SHORT).show();
        } else {

            if (!spinner.getSelectedItem().toString().equals("Ед. измерения")) {

                if (isEditState) {
                    AppExecutor.getInstance().getSubIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            managerNomenclature.insertToDb(title, unit_measurement, tempUri);
                        }
                    });
                    makeText(this, R.string.saved, LENGTH_SHORT).show();
                } else {
                    managerNomenclature.updateItem(title, unit_measurement, tempUri, listItemN.getId());
                    makeText(this, R.string.saved, LENGTH_SHORT).show();
                }
                managerNomenclature.closeDb();
                finish();

            } else {
                makeText(this, "Выберите ед. измерения", LENGTH_SHORT).show();
            }

        }
    }

    public void onClickDeleteImage(View v) {
        imNewImage.setImageResource(R.drawable.ic_baseline_image_24);
        tempUri = "empty";
        imageContainer.setVisibility(GONE);
        button_add_image.setVisibility(VISIBLE);
    }

    public void onClickAddImage(View v) {
        imageContainer.setVisibility(VISIBLE);
        button_add_image.setVisibility(GONE);
    }

    public void onClickChooseImage(View v) {
        Intent chooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooser.addCategory(Intent.CATEGORY_OPENABLE);
        chooser.setType("image/*");
        mStartForResult.launch(chooser);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            tempUri = intent.getData().toString();
                            imNewImage.setImageURI(intent.getData());
                            try {
                                getApplicationContext().grantUriPermission(getCallingPackage(),
                                        Uri.parse(tempUri), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


    public void onClickDeleteUnit(View v) {
        hideLayout();
    }

    public void onClickToUnit(View v) {
        Intent i = new Intent(this, AddUnit.class);
        startActivity(i);
    }

    public void onClickHome(View v) {
        Intent i = new Intent(this, Ware.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerNomenclature.closeDb();
    }
}