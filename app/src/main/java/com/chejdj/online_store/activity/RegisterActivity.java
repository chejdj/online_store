package com.chejdj.online_store.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chejdj.online_store.R;

/**
 * <pre>
 *     author : chejdj
 *     e-mail : yangyang.zhu96@gmail.com
 *     time   : 2018/06/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Button register =  findViewById(R.id.register);
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.secrect);
        email =  findViewById(R.id.email);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (username.getText().toString() .equals("") || password.getText().toString().equals("") || email.getText().toString().equals("")) {
            Toast.makeText(this, "填写信息完整", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("register", username.getText().toString());
            stroe_Information(username.getText().toString(), password.getText().toString(), email.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void stroe_Information(String username, String password, String email) {
        SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("secrect", password);
        editor.putString("email", email);
        editor.putBoolean("logout", false);
        editor.apply();//使用commit的效率更低
    }
}