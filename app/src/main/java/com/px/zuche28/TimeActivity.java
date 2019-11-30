package com.px.zuche28;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.px.zuche28.Model.Message;
import com.px.zuche28.Model.ReservationForm;
import com.px.zuche28.Utils.Tools;
import com.px.zuche28.global.GlobalConstants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Calendar;

public class TimeActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {
    private int year;
    private int month;
    private int day;
    private int hourOfDay;
    private int minute;
    private String time;
    private int reservationFormId;
    private TextView phone;
    private String tx_phone;
    private Button yuyue;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_setting);
        phone = findViewById(R.id.phone);
        yuyue = findViewById(R.id.order);
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //从意图获取消息
        intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);

        day = intent.getIntExtra("day", 0);
        reservationFormId = intent.getIntExtra("ReservationFormId",0);
        System.out.println("re=123131="+reservationFormId);
        System.out.println(year + "-" + (month + 1) + "-" + day + "******************** ");

        Toast.makeText(TimeActivity.this, "您选择的日期是：" + year + "-" + (month + 1) + "-" + day + " ", Toast
                .LENGTH_SHORT).show();
        if (month<=10){
            time = year + "-0" + (month + 1) + "-" + day + " "+hourOfDay + ":" + (minute + 1);
        }else {
            time = year + "-" + (month + 1) + "-" + day + " "+hourOfDay + ":" + (minute + 1);
        }

        //设置按钮监听
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("time:"+time);
                getEditString();
                if (TextUtils.isEmpty(tx_phone)){
                    Toast.makeText(TimeActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (time==null){
                    Toast.makeText(TimeActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    order(time,reservationFormId);
                }
            }
        });

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(TimeActivity.this, "您选择的日期是：" + hourOfDay + ":" + (minute + 1) , Toast
                .LENGTH_SHORT).show();

    }

    //预约点击事件
    private void order(String times, int reservationFormId) {
//        time = year + "-" + (month + 1) + "-" + day + " "+hourOfDay + ":" + (minute + 1);
        long date = Tools.getTime(times);
        System.out.println("dadadada = "+date);
//        reservationForm.setReservationTime(data);
//        reservationForm.setReservationFormId(reservationFormId);
        System.out.println("re=="+reservationFormId);
        System.out.println("times: "+times);

        RequestParams params = new RequestParams(GlobalConstants.RESERVATIONORDER_URL);
        params.setAsJsonContent(true);
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setReservationFormId(reservationFormId);
        reservationForm.setReservationTime(date);
        params.setBodyContent(new Gson().toJson(reservationForm));
//        params.addBodyParameter("reservationFormId", reservationFormId);
//        params.addBodyParameter("reservationTime", date);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Message registBean = gson.fromJson(result, Message.class);

                //如果预约成功就返回首页
                Toast.makeText(TimeActivity.this, registBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (registBean.isSuccess()) {
                    TimeActivity.this.finish();
                    intent.setAction("action.refreshMain");
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
    public void finish(View view) {
        finish();
    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString() {
        try {
           tx_phone = phone.getText().toString().trim();
        } catch (NullPointerException e) {
            Toast.makeText(TimeActivity.this, "请填写信息", Toast.LENGTH_SHORT).show();
        }
    }


}
