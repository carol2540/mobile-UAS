package umn.ac.id.uas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    private FirebaseUser firebaseUser;
//    private TextView textName;

    Button btnLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogout=view.findViewById(R.id.btn_logout);
        TextView textName = (TextView) view.findViewById(R.id.profile_title);
        TextView emailName = (TextView) view.findViewById(R.id.email_profile);
        if(firebaseUser!=null){
            textName.setText(firebaseUser.getDisplayName());
            emailName.setText(firebaseUser.getEmail());
        }else{
            textName.setText("Login username failed");
            emailName.setText("Login email failed");
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}