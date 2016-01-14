package com.example.android.muhta;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


 //Full guide on country code at this address: http://envyandroid.com/creating-listdialog-with-images-and-text/

public class CountrycodeActivity extends ListActivity {

    public static String RESULT_CONTRYCODE = "countrycode";
    public String[] countrynames, countrycodes;
    private List<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateCountryList();
        ArrayAdapter<Country> adapter = new CountryListArrayAdapter(this, countryList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country c = countryList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_CONTRYCODE, c.getCode());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void populateCountryList() {
        countryList = new ArrayList<Country>();
        countrynames = getResources().getStringArray(R.array.country_names);
        countrycodes = getResources().getStringArray(R.array.country_names);
        for(int i = 0; i < countrycodes.length; i++){
            countryList.add(new Country(countrynames[i], countrycodes[i]));
        }
    }

    public class Country {
        private String name;
        private String code;

        public Country(String name, String code){
            this.name = name;
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public String getCode() {
            return code;
        }
    }

}
