package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.awt.font.TextAttribute;

public class Login extends AppCompatActivity {

    private EditText loginEmail, loginPass;
    private Button btnLogin, btnRegis;
//    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn2);
        btnRegis = findViewById(R.id.regis_btn);

        mAuth = FirebaseAuth.getInstance();
        //progress ststus
//        progressDialog = new ProgressDialog(Login.this);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Silahkan Tunggu");
//        progressDialog.setCancelable(false);

        //onclick btnRegis
        btnRegis.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Regis.class));
        });

        //onclick btnLogin
        btnLogin.setOnClickListener(v -> {
            if(loginEmail.getText().length()>0 && loginPass.getText().length()>0){
                login(loginEmail.getText().toString(), loginPass.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(),"Please input your email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //firebase login logic
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    if(task.getResult().getUser()!=null){
                        reload();
                    }else{
                        Toast.makeText(getApplicationContext(), "Loginfailed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Loginfailed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), navbar.class));
    }

    //firebase onstart
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

}