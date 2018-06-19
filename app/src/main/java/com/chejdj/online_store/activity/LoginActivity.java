package com.chejdj.online_store.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login =  findViewById(R.id.login);
        Button register =  findViewById(R.id.register);
        username =  findViewById(R.id.username);
        password =  findViewById(R.id.secrect);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        preferences = getSharedPreferences("User", MODE_PRIVATE);
        if (preferences.getString("username", "").equals("") && !preferences.getBoolean("logout",false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (check(username.getText().toString(), password.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean check(String username, String password) {

        if (username.equals(preferences.getString("username", "")) &&
                password.equals(preferences.getString("secrect", ""))) {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("logout",false);
            editor.apply();
            return true;
        }
        return false;
    }
}
