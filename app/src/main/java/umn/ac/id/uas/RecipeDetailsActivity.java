package umn.ac.id.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setTitle("Recipe Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}