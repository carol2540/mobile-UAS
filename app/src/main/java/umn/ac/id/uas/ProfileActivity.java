package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
//    private FirebaseUser firebaseUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
////        Button btnLogout = findViewById(R.id.btn_logout);
////        Button btnEditProfile = findViewById(R.id.btn_editProfile);
////        TextView textName = findViewById(R.id.profile_title);
////        TextView emailName = findViewById(R.id.email_profile);
//
//        Button btnLogout = findViewById(R.id.btn_logout);
//        Button btnEditProfile = findViewById(R.id.btn_editProfile);
//        TextView textName = findViewById(R.id.profile_title);
//        TextView emailName = findViewById(R.id.email_profile);
//
//        if(firebaseUser!=null){
//            textName.setText("Hi, " + firebaseUser.getDisplayName());
//            emailName.setText(firebaseUser.getEmail());
//        }else{
//            textName.setText("Login username failed");
//            emailName.setText("Login email failed");
//        }
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
}