package com.huang.vitalitycampus.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huang.vitalitycampus.dao.StudentCheckDao;
import com.huang.vitalitycampus.dao.StudentDao;
import com.huang.vitalitycampus.db.AppDatabase;
import com.huang.vitalitycampus.model.Student;
import com.huang.vitalitycampus.model.StudentCheck;
import com.huang.vitalitycampus.utils.Dialog;
import com.huang.vitalitycampus.utils.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText studentIdET,studentNameET,studentPasswordET,telephoneET;
    public static final int TAKE_CAMERA = 101;
    ImageView imageView;
    Toolbar toolbar;
    String imagdate;
    String imagepath = "";
    StudentDao studentDao;
    StudentCheckDao studentCheckDao;
    Student student;
    StudentCheck studentCheck;
    long flag;
    private Uri imageUri;//原图保存地址
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();
        StatusBarUtil.transparencyBar(this);
        studentDao = AppDatabase.getDatabase(this).getStudentDao();
        studentCheckDao = AppDatabase.getDatabase(this).getStudentCheckDao();
        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.backGo).setOnClickListener(this);
        studentIdET = findViewById(R.id.studentId);
        studentNameET=findViewById(R.id.name);
        studentPasswordET=findViewById(R.id.password);
        telephoneET=findViewById(R.id.telephone);
        imageView=findViewById(R.id.photo);
        ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imagePath = new File(getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                imagdate = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
                File newFile = new File(imagePath, imagdate + ".jpg");
                imagepath=newFile.getPath();
                System.out.println("newFile.getPath()="+newFile.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于等于版本24（7.0）的场合
                    imageUri = FileProvider.getUriForFile(RegisterActivity.this, "com.huang.vitalitycampus.ui.activity.fileprovider", newFile);//此处的outputImage指定的路径要在file_paths.xml文件对应配置path指定路径的子路径
                } else {
                    //小于android 版本7.0（24）的场合
                    imageUri = Uri.fromFile(newFile);
                }
                //启动相机程序
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }

    private  void formatCheck() {
        Pattern p = Pattern.compile("^[0-9]{11}");
        Pattern p_phone = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(studentIdET.getText().toString());
        Matcher m_phone = p_phone.matcher(telephoneET.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                student = studentDao.findStudent(studentIdET.getText().toString());
            }
        });
        handleRegister(student, m, m_phone);
    }

    public void handleRegister(Object o,Matcher m,Matcher m_phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                studentCheck = studentCheckDao.findStudent(studentIdET.getText().toString());
            }
        }).start();
        if (o != null) {
            Dialog.show(RegisterActivity.this, "提示", R.mipmap.logo, "该用户已存在");
        } else if (!m.matches()) {
            Dialog.show(RegisterActivity.this, "提示", R.mipmap.logo, "输入账号格式错误");
        } else if (!m_phone.matches()) {
            Dialog.show(RegisterActivity.this, "提示", R.mipmap.logo, "输入电话格式错误");
        } else if (studentCheck == null) {
            Dialog.show(RegisterActivity.this, "提示", R.mipmap.logo, "该学号不存在！");
        } else {
                student = new Student();
                student.setAccount(studentIdET.getText().toString());
                student.setName(studentNameET.getText().toString());
                student.setTelephone(telephoneET.getText().toString());
                student.setPassword(studentPasswordET.getText().toString());
                student.setStatus(0);
                student.setHeadPic(imagepath);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flag = studentDao.insertStudent(student);
                    }
                }).start();
                if (flag > 0) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("studentId", student.getId());
                    intent.setClass(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Dialog.show(RegisterActivity.this, "提示", R.mipmap.logo, "注册失败");
                }
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset:
                studentIdET.setText("");
                studentNameET.setText("");
                telephoneET.setText("");
                studentPasswordET.setText("");
                break;
            case R.id.submit:
                formatCheck();
                break;
            case R.id.backGo:
                finish();
                break;

        }
    }
}