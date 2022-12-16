package umn.ac.id.ez_recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UploadRecipeDetails extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button btnChooseImage;
    private Button btnUpload;
    private EditText etTitle;
    private EditText etRecipeDetails;
    private ImageView ivImage;
    private ProgressBar pbProgress;

    private Uri imageURI;

    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestoreRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe_details);
        getSupportActionBar().setTitle("Upload Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnUpload = findViewById(R.id.btn_upload);
        etTitle = findViewById(R.id.et_image_title);
        etRecipeDetails = findViewById(R.id.et_recipe_details);
        ivImage = findViewById(R.id.iv_image);
        pbProgress = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mFirestoreRef = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadRecipe();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();

            Picasso.get().load(imageURI).into(ivImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadRecipe() {
        if(etTitle.getText().toString().isEmpty()){
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(etRecipeDetails.getText().toString().isEmpty()){
            Toast.makeText(this, "Recipe is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if(imageURI != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageURI));

            fileReference.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if(taskSnapshot.getMetadata() != null) {
                            if(taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                pbProgress.setProgress(0);
                                            }
                                        }, 1000);

                                        Toast.makeText(UploadRecipeDetails.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                        Upload upload = new Upload(mUser.getUid(), uri.toString(), etRecipeDetails.getText().toString(), etTitle.getText().toString().trim(), mUser.getDisplayName());
                                        Map<String, Object> recipe = new HashMap<>();
                                        recipe.put("author", upload.getAuthorId());
                                        recipe.put("imageUrl", upload.getImageUrl());
                                        recipe.put("name", upload.getName());
                                        recipe.put("recipe", upload.getRecipe());
                                        recipe.put("authorName", upload.getAuthorName());

                                        mFirestoreRef.collection("recipes")
                                            .add(recipe)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(UploadRecipeDetails.this, "Recipe uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(UploadRecipeDetails.this, navbar.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(UploadRecipeDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                    }
                                });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadRecipeDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pbProgress.setProgress((int) progress);
                    }
                });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}