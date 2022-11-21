package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Regis extends AppCompatActivity {
    private EditText regisEmail, regisName, regisPass;
    private Button btnRegister, btnLogin;
//    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        regisEmail=findViewById(R.id.regis_email);
        regisName=findViewById(R.id.regis_username);
        regisPass=findViewById(R.id.regis_password2);
        btnLogin=findViewById(R.id.login_btn3);
        btnRegister=findViewById(R.id.regis_btn2);

        mAuth = FirebaseAuth.getInstance();
        //progress ststus
//        progressDialog = new ProgressDialog(Regis.this);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Silahkan Tunggu");
//        progressDialog.setCancelable(false);

        //onclick btnlogin finish
        btnLogin.setOnClickListener(v -> {
            finish();
        });
        btnRegister.setOnClickListener(v -> {
            if(regisEmail.getText().length()>0 && regisName.getText().length()>0 && regisPass.getText().length()>0){
                register(regisEmail.getText().toString(),regisName.getText().toString(), regisPass.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Please fill all the data column",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String email, String username, String password) {
//        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser!=null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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