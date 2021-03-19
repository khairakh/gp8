package com.example.group8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class DisplayBooking extends AppCompatActivity {

    public TextView dName, dNumber, dDate, dTime, dService;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dName = findViewById(R.id.tvName);
        dNumber = findViewById(R.id.tvNumber);
        dDate = findViewById(R.id.tvDate);
        dTime = findViewById(R.id.tvTime);
        dService = findViewById(R.id.tvService);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getInstance().getReference("Booking Data").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BookingData bookingData = snapshot.getValue(BookingData.class);

                dName.setText("Name: " + bookingData.getName());
                dNumber.setText("Phone Number: " + bookingData.getPhoneNumber());
                dDate.setText("Date: " + bookingData.getDate());
                dTime.setText("Time: " + bookingData.getTime());
                dService.setText("Service: " + bookingData.getServices());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayBooking.this, error.getCode(), Toast.LENGTH_SHORT).show();
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