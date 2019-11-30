package com.px.zuche28;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DateTimeActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {


    private int years;
    private int monthOfYear;
    private int dayOfMonth;
    private int reservationFormId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.datetime_setting);
        DatePicker datePicker = findViewById(R.id.date_picker);

        //获取日历当前时间
        Calendar calendar = Calendar.getInstance();
        years = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(years, monthOfYear, dayOfMonth, this);
        System.out.println(years + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日!");
        //意图接收转发
        Intent intent = getIntent();
        reservationFormId = intent.getIntExtra("ReservationFormId", 0);
//        int ReservationId = intent.getIntExtra("ReservationId", 0);



    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        Toast.makeText(DateTimeActivity.this, "您选择的日期是：" + year + "年" + (month + 1) + "月" + day + "日!", Toast
                .LENGTH_SHORT).show();
        years = year;
        monthOfYear = month;
        dayOfMonth = day;
        System.out.println(years + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日!!!!!!!!!!!!!!!!!!");
    }


    public void next(View view) {
        //下一步数据转发
        finish();
        Intent intent = new Intent(this, TimeActivity.class);
        intent.putExtra("year", years);
        intent.putExtra("month", monthOfYear);
        intent.putExtra("day", dayOfMonth);
        intent.putExtra("ReservationFormId", reservationFormId);
        startActivity(intent);
    }

    public void finish(View view) {
        finish();
    }
}
