package com.example.contactbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contactbook.Model.Contact;
import com.example.contactbook.Params.params;
import com.example.contactbook.data.MyDbHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView goToCreateContact = findViewById(R.id.imageView6);
        // creating a new contact
        goToCreateContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Create_contact_userI.class);
                        startActivity(intent);
                    }
                }
        );


        // this was an old feature in this app to refresh the contact list
//        ImageView refresh = (ImageView)findViewById(R.id.imageView2);
//        refresh.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        View_Contacts();
//                    }
//                }
//        );

        MyDbHandler db = new MyDbHandler(MainActivity.this);
        final ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> contacts = new ArrayList<>();

        List<Contact> allContacts = db.getAllContacts();
        for(Contact contact: allContacts){
            contacts.add(contact.getName());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(arrayAdapter);

        registerForContextMenu(listView);


        // searching and opening the data of contact clicked
        listView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        try {

                            final MyDbHandler db = new MyDbHandler(MainActivity.this);
                            final String PassWord = db.contactAtposition(position).getPassword().toString();
                            Toast.makeText(MainActivity.this,"pass : " + PassWord,Toast.LENGTH_SHORT).show();
                            if(!PassWord.equals("")){

                                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                                final View promptView = li.inflate(R.layout.password_check, null);

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setView(promptView);


                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        password = (EditText) promptView.findViewById(R.id.inputPass);
                                                        String EnteredPassWord = password.getText().toString();
                                                        if(EnteredPassWord.equals(PassWord)){

                                                            Intent intent = new Intent(getApplicationContext(), ShowClickContacts.class);
                                                            intent.putExtra("id", position);
                                                            startActivity(intent);
                                                        }
                                                        else{

                                                            Toast.makeText(MainActivity.this,"Wrong Password : " + EnteredPassWord ,Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //Toast.makeText(MainActivity.this,"W" + password.getText().toString() ,Toast.LENGTH_SHORT).show();
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), ShowClickContacts.class);
                                intent.putExtra("id", position);

                                startActivity(intent);
                            }

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "error : "+e,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    // setting the menu features
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feature_menu, menu);


    }
    // applying the menu features
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        try {

            if(item.getItemId() == R.id.option_1){
                MyDbHandler db = new MyDbHandler(MainActivity.this);
                db.delete_contacts(info.position);
                Toast.makeText(MainActivity.this,"The contact is deleted",Toast.LENGTH_SHORT).show();
                View_Contacts();}
            else if(item.getItemId() == R.id.option_2){
                MyDbHandler db = new MyDbHandler(MainActivity.this);
                Contact newContact = new Contact();
                newContact = db.contactAtposition(info.position);

                if(newContact.getPassword().equals("")) {
                    Intent intent = new Intent(MainActivity.this, Create_contact_userI.class);
                    intent.putExtra("Name", newContact.getName());
                    intent.putExtra("Number", newContact.getPhoneNumber());
                    intent.putExtra("Description", newContact.getDescription());
                    intent.putExtra("id", newContact.getId());
                    intent.putExtra("updating", true);
                    intent.putExtra("password",newContact.getPassword());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "The Contact is protected " , Toast.LENGTH_SHORT).show();
                }

            }
            else if(item.getItemId() == R.id.option_3){
                Toast.makeText(MainActivity.this,"enter the message",Toast.LENGTH_SHORT).show();  }

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"error : " + e,Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);

    }
    // option to refresh the database
    public void View_Contacts(){

        MyDbHandler db = new MyDbHandler(MainActivity.this);
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> contacts = new ArrayList<>();
        ContentValues values = new ContentValues();

        List<Contact> allContacts = db.getAllContacts();
        for(Contact contact: allContacts){
            contacts.add(contact.getName());

        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(arrayAdapter);

    }

// refreshing after creating a new contact


    @Override
    protected void onResume() {
        super.onResume();
        View_Contacts();
    }
}
