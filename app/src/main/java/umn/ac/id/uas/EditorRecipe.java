package umn.ac.id.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditorRecipe extends AppCompatActivity {
    private EditText foodName;
    private Button btnSave, btnCancle;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_recipe);
        foodName = findViewById(R.id.foodName);
        btnSave = findViewById(R.id.btn_save);
        btnCancle  =findViewById(R.id.btn_cancle);
        progressDialog = new ProgressDialog(EditorRecipe.this);
        progressDialog.setTitle("Loding");
        progressDialog.setMessage("Saving data..");

        btnSave.setOnClickListener(v->{
            if(foodName.getText().length()>0){
                saveData(foodName.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Please fill all the data column", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditorRecipe.this, UploadRecipe.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getStringExtra("id");
            //foodName rada sus, bisa ganti heading
            foodName.setText(intent.getStringExtra("heading"));
        }
    }

    private void saveData(String foodName) {
        Map<String, Object> foods = new HashMap<>();
        foods.put("heading", foodName);

        progressDialog.show();
        if(id!=null){
            db.collection("foods").document(id)
                    .set(foods)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Successfully loaded", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Filed to load", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            db.collection("foods")
                    .add(foods)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Data successfully added", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    });
        }
    }
}