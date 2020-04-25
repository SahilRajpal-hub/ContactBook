package com.example.contactbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactbook.Model.Contact;
import com.example.contactbook.data.MyDbHandler;

public class Create_contact_userI extends AppCompatActivity {

    public Button save_btn;
    public EditText Name_plate;
    public EditText Contact_plate;
    public EditText password_plate;
    public EditText Description_plate;
    public Switch password_switch;
    boolean updating=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_user_i);

        save_btn = (Button)findViewById(R.id.button2);
        Name_plate = (EditText)findViewById(R.id.editText);
        Contact_plate = (EditText)findViewById(R.id.editText2);
        password_plate = (EditText)findViewById(R.id.editText3);
        Description_plate = (EditText)findViewById(R.id.editText4);
        password_switch = (Switch)findViewById(R.id.switch1);
        ImageView Image_Of_contact = (ImageView)findViewById(R.id.imageView);
        password_plate.setEnabled(false);
        password_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password_plate.setEnabled(true);
                }
                else if(!isChecked){
                    password_plate.setEnabled(false);
                }
            }
        });

        Intent intent = getIntent();
        Name_plate.setText(intent.getStringExtra("Name"));
        Contact_plate.setText(intent.getStringExtra("Number"));
        Description_plate.setText(intent.getStringExtra("Description"));
        String password = intent.getStringExtra("password");
        if(password!=""){
            password_plate.setEnabled(true);
            password_switch.setChecked(true);
            password_plate.setText(password);
        }
        final int id = intent.getIntExtra("id",-1);
        updating = intent.getBooleanExtra("updating",false);

        save_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MyDbHandler db = new MyDbHandler(Create_contact_userI.this);
                        String name = Name_plate.getText().toString();
                        String PhoneNumber = Contact_plate.getText().toString();
                        String Description = Description_plate.getText().toString();
                        String password = password_plate.getText().toString();
                        if(!password_switch.isChecked()){
                            password = "";
                        }
                        Contact contact = new Contact(name,PhoneNumber,Description,password);



                        try{
                        if(updating){
                            int a = db.Update_contacts(id,name,Description,PhoneNumber,password);
                            Toast.makeText(Create_contact_userI.this,"Contact updated "+a ,Toast.LENGTH_SHORT).show();
                            updating=false;
                        }
                        else{
                        long a = db.addContact(contact);
                            Toast.makeText(Create_contact_userI.this,"Contact saved " + a,Toast.LENGTH_SHORT).show();
                        }
                        }
                        catch (Exception e){

                        }

                        Name_plate.setText("");
                        Contact_plate.setText("");
                        Description_plate.setText("");
                        password_plate.setText("");
                        finish();

                    }
                }
        );

        Image_Of_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();
            }
        });




    }

//
//    private void selectImage() {
//
//        CharSequence[] options = { "Open camera", "Open gallery" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Create_contact_userI.this);
//        builder.setTitle("Add Photos ");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0){
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                    Uri photouri = FileProvider.getUriForFile(Create_contact_userI.this,
//                            BuildConfig.APPLICATION_ID + ".provider", f);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    startActivityForResult(intent,1);
//                }
//                else if(which ==  1){
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                }
//            }
//        });
//
//        builder.show();
//    }
}

