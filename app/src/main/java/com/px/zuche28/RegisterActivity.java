package com.px.zuche28;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.px.zuche28.Model.Account;
import com.px.zuche28.Model.Message;
import com.px.zuche28.Utils.MD5Utils;
import com.px.zuche28.global.GlobalConstants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends AppCompatActivity {
    //用户名，密码，再次输入的密码
    private TextView et_user_name, et_psw, et_psw_again, et_phone;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName, psw, pswAgain ,phone;
    private Button btn_register;//注册按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        init();
    }

    private void init() {
        et_user_name = findViewById(R.id.re_name);
        et_psw = findViewById(R.id.re_mm);
        et_psw_again = findViewById(R.id.re_qrmm);
        et_phone = findViewById(R.id.re_sjh);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;

                }
                /**
                 *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
                 */
//                else if (isExistUserName(userName)) {
//                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                else {
                    register(userName, psw, phone);
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把账号、密码和账号标识保存到sp里面
//                    /**
//                     * 保存账号和密码到SharedPreferences中
//                     */
//                    saveRegisterInfo(userName, psw);
//                    //注册成功后把账号传递到LoginActivity.java中
//                    // 返回值到loginActivity显示
//                    Intent data = new Intent();
//                    data.putExtra("userName", userName);
//                    setResult(RESULT_OK, data);
//                    //RESULT_OK为Activity系统常量，状态码为-1，
//                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值

                }
            }
        });
    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString() {
        try {
            userName = et_user_name.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            pswAgain = et_psw_again.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
        } catch (NullPointerException e) {
            Toast.makeText(RegisterActivity.this, "请填写信息", Toast.LENGTH_SHORT).show();
        }
    }

//    /**
//     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
//     */
//    private boolean isExistUserName(String userName) {
//        boolean has_userName = false;
//        //mode_private SharedPreferences sp = getSharedPreferences( );
//        // "loginInfo", MODE_PRIVATE
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取密码
//        String spPsw = sp.getString(userName, "");//传入用户名获取密码
//        //如果密码不为空则确实保存过这个用户名
//        if (!TextUtils.isEmpty(spPsw)) {
//            has_userName = true;
//        }
//        return has_userName;
//    }

//    /**
//     * 保存账号和密码到SharedPreferences中SharedPreferences
//     */
//    private void saveRegisterInfo(String userName, String psw) {
////        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
//        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
//        SharedPreferences.Editor editor = sp.edit();
//        //以用户名为key，密码为value保存在SharedPreferences中
//        //key,value,如键值对，editor.putString(用户名，密码）;
//        editor.putString(userName, psw);
//        //提交修改 editor.commit();
//        editor.commit();
//    }
//

    //    public void register(View view) {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
//
//
//    }
    private void register(String userName, String password, String phone) {
        RequestParams params = new RequestParams(GlobalConstants.REGISTER_URL);
        params.setAsJsonContent(true);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("password", password);
        params.addBodyParameter("phone", phone);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //成功
                Gson gson = new Gson();
                Message registBean = gson.fromJson(result, Message.class);

                //如果注册成功就返回登录页面
                Toast.makeText(RegisterActivity.this, registBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (registBean.isSuccess()) {
                    RegisterActivity.this.finish();
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

    public void backlogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
