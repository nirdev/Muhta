package com.example.android.muhta;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import Util.Util;

public class SignupActivity extends AppCompatActivity {

    private EditText userPhone;
    private Button nextB;
    private Spinner prefixSpin;
    private ParseUser user;
    private Locale[] locale;
    private ArrayList<String> countries;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        Util.setupUI(findViewById(android.R.id.content), SignupActivity.this);


        userPhone = (EditText) findViewById(R.id.user_phone);
        nextB = (Button) findViewById(R.id.next_button1);
        prefixSpin = (Spinner) findViewById(R.id.spinner);

        locale = Locale.getAvailableLocales();
        countries = new ArrayList<String>();
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        prefixSpin.setAdapter(adapter);

        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if (!userPhone.equals("")) {

                user = new ParseUser();
                user.setUsername(userPhone.getText().toString());
                user.setPassword("1234");

                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {

                    if (e != null) {

                        Toast.makeText(getApplicationContext(), "Saving user failed.", Toast.LENGTH_SHORT).show();

                        if (e.getCode() == 202) {

                            Toast.makeText(getApplicationContext(), "Phone number already taken. \nPlease choose another phone number.",
                                    Toast.LENGTH_LONG).show();
                            userPhone.setText("");

                            user.deleteInBackground();
                        }

                    } else {


                        Toast.makeText(getApplicationContext(), "User Saved", Toast.LENGTH_SHORT).show();

                        ContentResolver cr = getContentResolver();
                        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                null, null, null, null);
                        if (cur.getCount() > 0) {
                            while (cur.moveToNext()) {
                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (Integer.parseInt(cur.getString(
                                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                    Cursor pCur = cr.query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                            new String[]{id}, null);
                                    while (pCur.moveToNext()) {
                                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                                        ParseObject contactsList = new ParseObject("contactsList");

                                        contactsList.put("phoneNumber", phoneNo);
                                        contactsList.put("name", name);
                                        contactsList.put("addedBy", user.getObjectId());
                                        contactsList.saveInBackground();

                                      //   Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                                    }
                                    pCur.close();
                                }
                            }
                        }

                        startActivity(new Intent(SignupActivity.this, FacebookLogin.class));
                        finish();

                    }

                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "Please insert your phone number",
                        Toast.LENGTH_SHORT).show();
            }

            }
        });


    }


}
