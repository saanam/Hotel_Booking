package com.e.hotel_booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Spinner spinnerlocation, spinnerroom;
    Button btncalculate;
    EditText etadult, etchild, etroom;
    TextView tvcidate, tvcodate, result;
    Boolean date1, date2;
    String type, hotellocation, hotelroom, cidate, codate, noadult, nochild, noroom, novat, noservice, noroomprice, totoalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spinnerlocation = findViewById(R.id.spinnerlocation);
        this.spinnerroom = findViewById(R.id.spinnerroom);
        this.btncalculate = findViewById(R.id.btncalculate);
        this.etadult = findViewById(R.id.etadult);
        this.etchild = findViewById(R.id.etchild);
        this.etroom = findViewById(R.id.etroom);
        this.tvcidate = findViewById(R.id.tvcidate);
        this.tvcodate = findViewById(R.id.tvcodate);
        this.result = findViewById(R.id.tvresult);

        final String location[] = {"KTM", "PKH", "NPL"};
        String room[] = {"Normal", "AC", "Delux"};
        ArrayAdapter adapterlocation = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, location);
        ArrayAdapter adapterroom = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, room);
        spinnerlocation.setAdapter(adapterlocation);
        spinnerroom.setAdapter(adapterroom);

        tvcidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadDatepicker();
                date1 = true;
            }
        });

        tvcodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatepicker();
                date2 = true;
            }
        });

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    type = String.valueOf(spinnerroom.getSelectedItem());
                    int room, roomprice = 0;
                    double total, vat, service;

                    room = Integer.parseInt(etroom.getText().toString());
                    String ci = tvcidate.getText().toString();
                    String co = tvcodate.getText().toString();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                        Date d1 = simpleDateFormat.parse(ci);
                        Date d2 = simpleDateFormat.parse(co);
                        long diff = d2.getTime() - d1.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        int days = (int) diffDays;

                    if (type == "Normal") {
                        roomprice = 2000;
                    } else if (type == "AC") {
                        roomprice = 3000;
                    } else if (type == "Delux") {
                        roomprice = 4000;
                    }
                    vat = roomprice * 0.13;
                    service = roomprice * 0.10;
                    total = room * ((roomprice * days) + vat + service);

                    hotellocation = spinnerlocation.getSelectedItem().toString();
                    hotelroom = spinnerroom.getSelectedItem().toString();
                    cidate = tvcidate.getText().toString();
                    codate = tvcodate.getText().toString();
                    noadult = etadult.getText().toString();
                    nochild = etchild.getText().toString();
                    noroom = etroom.getText().toString();
                    novat = String.valueOf(vat);
                    noservice = String.valueOf(service);
                    noroomprice = String.valueOf(roomprice);
                    totoalprice = String.valueOf(total);

                    result.setText("Location:\t" + hotellocation + "\nRoom type:\t" + hotelroom + "\nDate check in:\t" + cidate
                            + "\nDate check out:\t" + codate + "\nNumber of adult:\t" + noadult + "\nNumber of childer:\t" + nochild + "\nTotal room:\t" + noroom
                            + "\nRoom per price:\t" + noroomprice + "\nVAT:\t" + novat + "\nService charge:\t" + noservice + "\nTotal:\t" + totoalprice);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void loadDatepicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) this, year, month, day);
        datepickerdialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =  year + "/" + month + "/" + dayOfMonth;
        if (date1 == true) {
            tvcidate.setText(date);
            date1 = false;
        } else if (date2 == true) {
            tvcodate.setText(date);
            date2 = false;
        }
    }

}
