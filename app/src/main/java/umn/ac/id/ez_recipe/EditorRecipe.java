package umn.ac.id.ez_recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditorRecipe extends AppCompatActivity {
    private EditText foodName, foodRecipe;
    private ImageView foodImage;
    private Button btnSave, btnCancle;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_recipe);
        foodName = findViewById(R.id.foodName);
        foodRecipe = findViewById(R.id.foodRecipe);
        foodImage = findViewById(R.id.foodImage);
        btnSave = findViewById(R.id.btn_save);
        btnCancle  =findViewById(R.id.btn_cancle);

        progressDialog = new ProgressDialog(EditorRecipe.this);
        progressDialog.setTitle("Loding");
        progressDialog.setMessage("Saving data..");

        foodImage.setOnClickListener(v ->{
            selecImage();
        });

        btnSave.setOnClickListener(v->{
            if(foodName.getText().length()>0 && foodRecipe.getText().length()>0){
                upload(foodName.getText().toString(), foodRecipe.getText().toString());
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
            foodName.setText(intent.getStringExtra("name"));
            foodRecipe.setText(intent.getStringExtra("recipe"));
            Picasso.get().load(intent.getStringExtra("imageUrl")).into(foodImage);
        }
    }

    private void selecImage(){
        final CharSequence[] items = {"Take photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditorRecipe.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog,item)->{
            if(items[item].equals("Take photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            }else if (items[item].equals("Choose from Library")){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 20);
            }else if (items[item].equals("Cancel")){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20 && resultCode == RESULT_OK && data !=null){
            final Uri path = data.getData();
            Thread thread = new Thread(()->{
                try{
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    foodImage.post(()->{
                        foodImage.setImageBitmap(bitmap);
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        if (requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(()->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                foodImage.post(()->{
                    foodImage.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    private void upload (String foodName, String foodRecipe){
        progressDialog.show();
        foodImage.setDrawingCacheEnabled(true);
        foodImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) foodImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //Upload
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("uploads").child(new Date().getTime()+".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata()!=null){
                    if(taskSnapshot.getMetadata().getReference()!=null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.getResult()!=null) {
                                    saveData(foodName, foodRecipe, task.getResult().toString());
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String foodName, String foodRecipe, String foodImage) {
        Map<String, Object> recipes = new HashMap<>();
        recipes.put("name", foodName);
        recipes.put("recipe", foodRecipe);
        recipes.put("imageUrl", foodImage);

        progressDialog.show();
        if(id!=null){
            db.collection("recipes").document(id)
                    .set(recipes)
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
            db.collection("recipes")
                    .add(recipes)
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