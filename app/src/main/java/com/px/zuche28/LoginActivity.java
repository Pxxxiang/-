package com.px.zuche28;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.px.zuche28.Model.Message;
import com.px.zuche28.global.GlobalConstants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView name;
    private TextView password;
    private Button btnlogin;
    private EditText et_netName;
    private EditText et_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_page);

        btnlogin = findViewById(R.id.btn_login);
        name = findViewById(R.id.name);
        et_password = findViewById(R.id.password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String password  = et_password.getText().toString().trim();
                //判断输入的内容是否为phone
                boolean b = isPhoneNumber(userName);

                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "用户名/密码不能为空", Toast.LENGTH_SHORT).show();
                }
//                else if (!b) {
//                    Toast.makeText(LoginActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
//                }
                else if (password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "密码不能少于六位数", Toast.LENGTH_SHORT).show();
                } else {
                    login(userName, password);
                }
            }
        });

    }

    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

    //    public void login(View view) {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//    }
    private void login(String userName, String password) {
// public int id;
//        public String netName;
//        public String password;
//        public String permission;
//        public String phone;
//        public String userName;
        RequestParams params = new RequestParams(GlobalConstants.LOGIN_URL);
        params.setAsJsonContent(true);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("password", password);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //成功
                Gson gson = new Gson();
                Message loginBean = gson.fromJson(result, Message.class);
                Toast.makeText(LoginActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginBean.isSuccess()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
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

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
