package umn.ac.id.ez_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;

    private TextView tvRecipeName, tvRecipeAuthor, tvRecipeDetails;
    private ImageView ivRecipe;
    private ArrayList<Upload> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setTitle("Recipe Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        tvRecipeName = findViewById(R.id.tv_recipe_name);
        tvRecipeAuthor = findViewById(R.id.tv_recipe_author);
        tvRecipeDetails = findViewById(R.id.tv_recipe);
        ivRecipe = findViewById(R.id.iv_recipe_image);

        Intent intent = getIntent();
        int position = intent.getIntExtra("pos", 0);

        db.collection("recipes").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  recipes.clear();
                  if(task.isSuccessful()){
                      for(QueryDocumentSnapshot document: task.getResult()){
                          Upload recipe = new Upload(document.getString("author"), document.getString("imageUrl"), document.getString("recipe"), document.getString("name"), document.getString("authorName"));
                          recipe.setId(document.getId());
                          recipes.add(recipe);
                      }

                      Picasso.get().load(recipes.get(position).getImageUrl()).into(ivRecipe);
                      tvRecipeName.setText(recipes.get(position).getName());
                      tvRecipeAuthor.setText(recipes.get(position).getAuthorName());
                      tvRecipeDetails.setText(recipes.get(position).getRecipe());
                  }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RecipeDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }
}