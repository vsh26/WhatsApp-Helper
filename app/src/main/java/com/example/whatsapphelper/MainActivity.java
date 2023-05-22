package com.example.whatsapphelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText message, phoneNo;
    Button sendButton;
    String messageStr, phoneStr="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.ccp);
        message = findViewById(R.id.editText);
        phoneNo = findViewById(R.id.phoneNo);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageStr = message.getText().toString();
                phoneStr = phoneNo.getText().toString();

                if (!messageStr.isEmpty() && !phoneStr.isEmpty()) {

                    countryCodePicker.registerCarrierNumberEditText(phoneNo);
                    phoneStr = countryCodePicker.getFullNumber();

                    if(isWhatsAppInstalled()){

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phoneStr +
                                "&text=" + messageStr));

                        startActivity(i);
                        phoneNo.setText("");
                        message.setText("");
                    } else{
                        Toast.makeText(MainActivity.this, "WhatsApp is not installed!", Toast.LENGTH_LONG).show();
                    }

                } else if (!messageStr.isEmpty() && phoneStr.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Please enter phone number! It can't be empty.", Toast.LENGTH_LONG).show();

                } else if (messageStr.isEmpty() && !phoneStr.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Please write a message! It can't be empty.", Toast.LENGTH_LONG).show();

                } else if (messageStr.isEmpty() && phoneStr.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Please enter phone number and write a message! They can't be empty", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private boolean isWhatsAppInstalled(){

        PackageManager packageManager = getPackageManager();
        boolean installed;

        try{
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch(PackageManager.NameNotFoundException e) {
            installed = false;
        }

        return  installed;
    }


}