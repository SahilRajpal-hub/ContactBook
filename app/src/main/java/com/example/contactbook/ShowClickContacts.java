package com.example.contactbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactbook.Model.Contact;
import com.example.contactbook.data.MyDbHandler;

public class ShowClickContacts extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_click_contacts);

        TextView name = (TextView) findViewById(R.id.textView2);
        TextView number = (TextView) findViewById(R.id.textView3);
        TextView description = (TextView) findViewById(R.id.textView4);
        ImageView callButton = (ImageView) findViewById(R.id.imageView4);
        ImageView meeasgeBtn = (ImageView) findViewById(R.id.imageView5);

        // get the contact object
        MyDbHandler db = new MyDbHandler(ShowClickContacts.this);
        Intent intent = getIntent();
        int pos = intent.getIntExtra("id", 0);
        Contact contact = new Contact();
        contact = db.contactAtposition(pos);

//      if contact is lockde with password




        final String numberTocall = contact.getPhoneNumber();

        // set the contact object
        name.setText(contact.getName());
        number.setText(contact.getPhoneNumber());
        description.setText(contact.getDescription());

        //call the selected contact
        callButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + numberTocall));
                        Toast.makeText(ShowClickContacts.this, "CALLING", Toast.LENGTH_SHORT).show();
                        startActivity(callIntent);
                    }
                }
        );

        // opening the messaging app
        meeasgeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendsms = new Intent(Intent.ACTION_VIEW);
                        sendsms.setType("vnd.android-dir/mms-sms");
                        sendsms.putExtra("address", numberTocall);
                        startActivity(sendsms);
                    }
                }
        );

    }
}
