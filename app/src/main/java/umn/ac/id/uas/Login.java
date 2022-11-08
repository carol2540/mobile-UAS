package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView email = (TextView) findViewById(R.id.login_email);
        TextView password = (TextView) findViewById(R.id.login_password);

        Button loginbtn = (Button) findViewById(R.id.login_btn2);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("test@email.com")&& password.getText().toString().equals("tai123")){
                    Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,navbar.class);
                    startActivity(intent);
                }else
                    Toast.makeText(Login.this, "Login Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
}