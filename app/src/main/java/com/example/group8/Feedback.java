package com.example.group8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

public class Feedback extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    public EditText feedbackUsername, feedbackText;
    public Button btnFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feedbackUsername = (EditText) findViewById(R.id.feedbackUsername);
        feedbackText = (EditText) findViewById(R.id.feedbackText);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference databaseReference = firebaseDatabase.getReference("User Feedback").child(firebaseAuth.getUid());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //String usernameInput = feedbackUsername.getText().toString();
                        String feedbackInput = feedbackText.getText().toString();

                        //DatabaseReference feedbackUsername = databaseReference.child("UserName");
                        //databaseReference.setValue(usernameInput);
                        DatabaseReference feedbackText = databaseReference.child("Feedback");
                        databaseReference.setValue(feedbackInput);

                        Toast.makeText(Feedback.this, "Feedback has been sent!", Toast.LENGTH_SHORT).show();
                        finish();
                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Feedback.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}