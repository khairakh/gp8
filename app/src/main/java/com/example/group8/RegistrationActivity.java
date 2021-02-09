package com.example.group8;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.DatabaseMetaData;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword , userEmail, userPhoneNumber;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name , number , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to the database
                    String user_email= userEmail.getText().toString().trim();
                    String user_password= userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                               senEmailVerification();
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Sign up Failed. Password length must be at least 6 digit.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));


            }
        });


    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
        userPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        userProfilePic = (ImageView)findViewById((R.id.ivProfile));

    }

    private Boolean validate(){
        Boolean result = false;

         name = userName.getText().toString();
         password = userPassword.getText().toString();
         email = userEmail.getText().toString();
         number = userPhoneNumber.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }

    private void senEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                    sendUserData();
                    Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else{
                    Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(email, name, number );
        myRef.setValue(userProfile);
    }


}

