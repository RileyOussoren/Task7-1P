package com.rileyoussoren.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    Button showButton;
    Button saveButton;
    Button removeButton;

    EditText nameEntry;
    EditText phoneEntry;
    EditText descriptionEntry;
    EditText dateEntry;
    EditText locationEntry;

    TextView radioText;
    TextView nameTitle;
    TextView phoneTitle;
    TextView descriptionTitle;
    TextView dateTitle;
    TextView locationTitle;
    TextView spinnerTitle;
    TextView itemDetails;

    RadioGroup radioButtons;
    RadioButton radioSelected;
    RadioButton radioLost;
    RadioButton radioFound;

    Spinner itemsSpinner;

    private DBHandler dbHandler;




    private void loadSpinnerData(){

        DBHandler db = new DBHandler(getApplicationContext());
        ArrayList<ItemModal> labels = db.readItems();

        List<String> itemsList = new ArrayList<>();

        itemsList.add("");

        for (int loop = 0; loop < labels.size(); loop++){

            itemsList.add(labels.get(loop).getItemName());

        }

        ArrayAdapter itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsList);
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        itemsSpinner.setAdapter(itemsAdapter);

    }

    private void getItemSelected(String selection){

        DBHandler db = new DBHandler(getApplicationContext());
        ArrayList<ItemModal> labels = db.readItems();

        List<String> itemDetailsList = new ArrayList<>();

        for (int i = 0; i < labels.size(); i++){

            if (selection.equals(labels.get(i).getItemName())){

                itemDetailsList.add(labels.get(i).getItemStatus());
                itemDetailsList.add(labels.get(i).getItemName());
                itemDetailsList.add(labels.get(i).getItemPhone());
                itemDetailsList.add(labels.get(i).getItemDescription());
                itemDetailsList.add(labels.get(i).getItemDate());
                itemDetailsList.add(labels.get(i).getItemLocation());

            }

        }

        StringBuilder builder = new StringBuilder();
        for (String item: itemDetailsList){
            builder.append(item);
            builder.append(" \n");
        }

        itemDetails.setText(builder.toString());

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.createButton);
        showButton = findViewById(R.id.showButton);
        saveButton = findViewById(R.id.saveButton);
        removeButton = findViewById(R.id.removeButton);

        nameEntry = findViewById(R.id.nameEntry);
        phoneEntry = findViewById(R.id.phoneEntry);
        descriptionEntry = findViewById(R.id.descriptionEntry);
        dateEntry = findViewById(R.id.dateEntry);
        locationEntry = findViewById(R.id.locationEntry);

        radioText = findViewById(R.id.radioText);
        nameTitle = findViewById(R.id.nameTitle);
        phoneTitle = findViewById(R.id.phoneTitle);
        descriptionTitle = findViewById(R.id.descriptionTitle);
        dateTitle = findViewById(R.id.dateTitle);
        locationTitle = findViewById(R.id.locationTitle);
        spinnerTitle = findViewById(R.id.spinnerTitle);
        itemDetails = findViewById(R.id.itemDetails);

        radioButtons = findViewById(R.id.radioGroup);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);

        itemsSpinner = findViewById(R.id.itemsSpinner);

        dbHandler = new DBHandler(MainActivity.this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createButton.setVisibility(View.INVISIBLE);
                showButton.setVisibility(View.INVISIBLE);

                radioText.setVisibility(View.VISIBLE);
                radioButtons.setVisibility(View.VISIBLE);
                radioLost.setVisibility(View.VISIBLE);
                radioFound.setVisibility(View.VISIBLE);
                nameTitle.setVisibility(View.VISIBLE);
                nameEntry.setVisibility(View.VISIBLE);
                phoneTitle.setVisibility(View.VISIBLE);
                phoneEntry.setVisibility(View.VISIBLE);
                descriptionTitle.setVisibility(View.VISIBLE);
                descriptionEntry.setVisibility(View.VISIBLE);
                dateTitle.setVisibility(View.VISIBLE);
                dateEntry.setVisibility(View.VISIBLE);
                locationTitle.setVisibility(View.VISIBLE);
                locationEntry.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);

            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createButton.setVisibility(View.INVISIBLE);
                showButton.setVisibility(View.INVISIBLE);

                spinnerTitle.setVisibility(View.VISIBLE);
                itemsSpinner.setVisibility(View.VISIBLE);
                //itemDetails.setVisibility(View.VISIBLE);

                //DBHandler db = new DBHandler(getApplicationContext());
                //ArrayList<ItemModal> labels = db.readItems();

                //itemDetails.setText(labels.get(0).getItemName());


                loadSpinnerData();



            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioButtons.getCheckedRadioButtonId();
                radioSelected = findViewById(selectedId);
                String radioIn = radioSelected.getText().toString();

                String nameIn = nameEntry.getText().toString();
                String phoneIn = phoneEntry.getText().toString();
                String descriptionIn = descriptionEntry.getText().toString();
                String dateIn = dateEntry.getText().toString();
                String locationIn = locationEntry.getText().toString();

                if (nameIn.isEmpty() && phoneIn.isEmpty() && descriptionIn.isEmpty() && dateIn.isEmpty() && locationIn.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.addItem(radioIn, nameIn, phoneIn, descriptionIn, dateIn, locationIn);

                Toast.makeText(MainActivity.this, "item has been added.", Toast.LENGTH_SHORT).show();
                nameEntry.setText("");
                phoneEntry.setText("");
                descriptionEntry.setText("");
                dateEntry.setText("");
                locationEntry.setText("");

                createButton.setVisibility(View.VISIBLE);
                showButton.setVisibility(View.VISIBLE);

                radioText.setVisibility(View.INVISIBLE);
                radioButtons.setVisibility(View.INVISIBLE);
                radioLost.setVisibility(View.INVISIBLE);
                radioFound.setVisibility(View.INVISIBLE);
                nameTitle.setVisibility(View.INVISIBLE);
                nameEntry.setVisibility(View.INVISIBLE);
                phoneTitle.setVisibility(View.INVISIBLE);
                phoneEntry.setVisibility(View.INVISIBLE);
                descriptionTitle.setVisibility(View.INVISIBLE);
                descriptionEntry.setVisibility(View.INVISIBLE);
                dateTitle.setVisibility(View.INVISIBLE);
                dateEntry.setVisibility(View.INVISIBLE);
                locationTitle.setVisibility(View.INVISIBLE);
                locationEntry.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);

            }
        });

        itemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position>0) {

                    String itemSelected = String.valueOf(itemsSpinner.getSelectedItem());

                    spinnerTitle.setVisibility(View.INVISIBLE);
                    itemsSpinner.setVisibility(View.INVISIBLE);
                    itemDetails.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.VISIBLE);

                    getItemSelected(itemSelected);

                    removeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dbHandler.deleteItem(itemSelected);

                            itemDetails.setVisibility(View.INVISIBLE);
                            removeButton.setVisibility(View.INVISIBLE);

                            createButton.setVisibility(View.VISIBLE);
                            showButton.setVisibility(View.VISIBLE);

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });












    }
}