package com.example.android.muhta;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import Util.Util;

public class SignupActivity extends AppCompatActivity {

    private EditText userPhone;
    private Button nextB;
    private Button choose_country_btn;
    private TextView country_code_textview;
    private ParseUser user;
    private String userPhoneNum;

    //First signup activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_signup);

        //keyboard closing class
        Util.setupUI(findViewById(android.R.id.content), SignupActivity.this);


        //Declaring view variables
        userPhone = (EditText) findViewById(R.id.user_phone_signup_XMLID);
        nextB = (Button) findViewById(R.id.next_button1);
        choose_country_btn = (Button) findViewById(R.id.choose_country_button_ID);
        country_code_textview = (TextView) findViewById(R.id.country_code_signup_XMLID);

        //Call country fragment
        final Intent intent = new Intent(this, CountrycodeActivity.class);
        choose_country_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 1);
            }
        });





        //user click next after inserting phone number
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            userPhoneNum = userPhone.getText().toString();


                if (userPhoneNum.length()==9 || userPhoneNum.length() ==10) {


                    user = new ParseUser();
                    if(userPhone.getText().toString().charAt(0)=='0') {
                        user.setUsername(userPhoneNum.substring(1));
                    }
                    else {
                        user.setUsername(userPhoneNum);
                    }

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
                Toast.makeText(getApplicationContext(), "Please insert a valid phone number",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if = 1 - result for country code
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            String countryCode = data.getStringExtra(CountrycodeActivity.RESULT_CONTRYCODE);
            String countryName = data.getStringExtra(CountrycodeActivity.RESULT_CONTRYNAME);
            Toast.makeText(this, "You selected countrycode: " + countryCode + "countryname:" +countryName, Toast.LENGTH_LONG).show();
            choose_country_btn = (Button) findViewById(R.id.choose_country_button_ID);
            choose_country_btn.setText(countryName);
            country_code_textview.setText(countryCode);

        }
    }

}
