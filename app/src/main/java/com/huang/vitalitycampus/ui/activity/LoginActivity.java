package com.huang.vitalitycampus.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huang.vitalitycampus.dao.StudentCheckDao;
import com.huang.vitalitycampus.dao.StudentDao;
import com.huang.vitalitycampus.db.AppDatabase;
import com.huang.vitalitycampus.model.Student;
import com.huang.vitalitycampus.model.StudentCheck;
import com.huang.vitalitycampus.utils.Dialog;
import com.huang.vitalitycampus.utils.StatusBarUtil;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameET,passwordET;
    Button loginBt;
    TextView registerTV;
    SharedPreferences sp= null;
    SharedPreferences.Editor editor=null;
    Student student;
    Student student1;
    StudentDao studentDao;
    String username;
    String userPassword;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        StatusBarUtil.transparencyBar(this);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
        loginBt = findViewById(R.id.login);
        registerTV = findViewById(R.id.register);
        Intent intent = this.getIntent();
        String userid = intent.getStringExtra("userId");
        usernameET.setText(userid);
        sp = getSharedPreferences("userData", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.clear();
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentDao = AppDatabase.getDatabase(LoginActivity.this).getStudentDao();
                username = usernameET.getText().toString();
                userPassword = passwordET.getText().toString();
                if (username.equals("")) {
                    Dialog.show(LoginActivity.this, "提示", R.mipmap.logo,"登录名不能为空");
                } else if (userPassword.equals("")) {
                    Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "密码不能为空");
                }
                if (!username.startsWith("a")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            student = studentDao.findStudent(username);

                        }
                    }).start();
                    if (student == null) {
                        Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "该账号不存在！");
                    } else if (student.getStatus() == 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                student1 = studentDao.login(username, userPassword);
                            }
                        }).start();
                        if (student1 != null) {
                            Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                            editor.putString("userId", username);
                            editor.apply();
                            startActivity(intent);
                        } else {
                            Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "用户名和密码错误登录失败");
                        }
                    } else if (student.getStatus() == 1) {
                        Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "账号还在审查中不能登录");
                    } else {
                        Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "账号违法请联系管理员");
                    }
                } else if (username.startsWith("a")) {
                    /*DistributorDao distributorDao = new DistributorDao(LoginActivity.this);
                    Distributor distributor = distributorDao.findDistributor(username);
                    if (distributor == null) {
                        Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "该账号不存在！");
                    } else if (distributorDao.login(username, userPassword)) {
                            Intent intent = new Intent(LoginActivity.this, DistribMainActivity.class);
                            editor.putString("distributorId", username);
                            editor.apply();
                            startActivity(intent);
                    } else {
                        Dialog.show(LoginActivity.this, "提示", R.mipmap.logo, "用户名和密码错误登录失败");
                    }*/
                }
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}