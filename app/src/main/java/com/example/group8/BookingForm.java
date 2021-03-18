package com.example.group8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingForm extends AppCompatActivity {

    TextView tvTime, tvDate, tvServices;
    EditText etUserName, etPhoneNumber;
    Spinner spinnerServices;
    int tHour, tMinute;
    Button submit;
    DatePickerDialog.OnDateSetListener setListener;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String[] services;
    String number, name, time, date, serviceText; //database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etUserName = findViewById(R.id.etUserName);
        tvServices = findViewById(R.id.tv_services);

        tvTime = findViewById(R.id.tv_timePicker);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        BookingForm.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //panggil hour and minute
                                tHour = hourOfDay;
                                tMinute = minute;
                                //simpan jam dan minit dalam string
                                String time = tHour + ":" + tMinute;
                                //panggil 24hour format
                                SimpleDateFormat f24hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24hours.parse(time);
                                    //panggil 24hours time format
                                    SimpleDateFormat f12hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    //set pilihan time di tv
                                    tvTime.setText(f12hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                //display previous selected time
                timePickerDialog.updateTime(tHour,tMinute);
                //show dialog
                timePickerDialog.show();
            }
        });

        tvDate = findViewById(R.id.tv_datePicker);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BookingForm.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                tvDate.setText(date);
            }
        };

        //spinner for services
        services = getResources().getStringArray(R.array.services);
        spinnerServices = findViewById(R.id.sp_services);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, services);
        spinnerServices.setAdapter(adapter);
        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();

                if (index == 0){
                    Toast.makeText(getBaseContext(), "Please select a service", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "You have selected " + services[index], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Booking Info").child(firebaseAuth.getUid());
        UserBooking userBooking = new UserBooking();

        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){

                    userBooking.setName(etUserName.getText().toString().trim());
                    userBooking.setPhoneNumber(etPhoneNumber.getText().toString().trim());
                    userBooking.setDate(tvDate.getText().toString().trim());
                    userBooking.setTime(tvTime.getText().toString().trim());
                    userBooking.setServices(spinnerServices.getSelectedItem().toString().trim());

                    databaseReference.setValue(userBooking);

                    Toast.makeText(BookingForm.this, "Booking Details has been sent!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(BookingForm.this, SecondActivity.class));
                }else {
                    Toast.makeText(BookingForm.this, "Error in sending Booking Details. Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Boolean validate(){
        boolean result = false;

        name = etUserName.getText().toString();
        number = etPhoneNumber.getText().toString();
        date = tvDate.getText().toString();
        time = tvTime.getText().toString();

        if(name.isEmpty() || number.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
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