package umn.ac.id.ez_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
            startActivity(new Intent(Login.this, Regis.class));
        });

        //onclick btnLogin
        btnLogin.setOnClickListener(v -> {
            login();
        });
    }
    //firebase login logic
    private void login() {
        String email = loginEmail.getText().toString();
        String password = loginPass.getText().toString();

        if(TextUtils.isEmpty(email)) {
            loginEmail.setError("Email can't be empty");
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPass.setError("Password can't be empty");
            loginPass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, navbar.class));
                    } else {
                        Toast.makeText(Login.this, "Login Failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

//        mAuth.signInWithEmailAndPassword(email,password)
//            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Log.d("Login", "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
//                        reload();
//                    }else{
//                        Log.w("Login", "SignInWithEmail:failure", task.getException());
//                        Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
    }

//    private void reload() {
//        startActivity(new Intent(getApplicationContext(), navbar.class));
//    }

    //firebase onstart
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(Login.this, navbar.class));
        }
    }

}