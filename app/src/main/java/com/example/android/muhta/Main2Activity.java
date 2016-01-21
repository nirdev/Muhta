package com.example.android.muhta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.ContactsAdapter;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> contactsListView;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.listID2);
        user = ParseUser.getCurrentUser();

        contactsListView = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contact");
        query.whereEqualTo("addedBy", user.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> contactsListParse, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + contactsListParse.size() + " contacts");

                    for (int i = 0; i < contactsListParse.size(); i++) {

                        Log.e("been here", "value:" + i);

                        HashMap<String, String> data = new HashMap<>();

                        data.put("name", contactsListParse.get(i).toString());

                        Log.e("contact name", contactsListParse.get(i).toString());

                        contactsListView.add(data);

                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        Log.e("size", "value: " + contactsListView.size());

        ContactsAdapter contactsAdapter = new ContactsAdapter(Main2Activity.this , contactsListView);

        listView.setAdapter(contactsAdapter);

        Log.e("size 2", "value: " + contactsListView.size());
    }
}
