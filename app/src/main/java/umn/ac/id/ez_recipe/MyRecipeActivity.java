package umn.ac.id.ez_recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import umn.ac.id.ez_recipe.adapter.FoodAdapter;

public class MyRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Upload> list =new ArrayList<>();
    private FoodAdapter foodAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe);
        getSupportActionBar().setTitle("Upload Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_add);
        btnAdd = findViewById(R.id.btn_add);

        progressDialog = new ProgressDialog(MyRecipeActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching Data...");
        foodAdapter = new FoodAdapter(getApplicationContext(), list);
        foodAdapter.setDialog(new FoodAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MyRecipeActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditorRecipe.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("name", list.get(pos).getName());
                                intent.putExtra("recipe", list.get(pos).getRecipe());
                                intent.putExtra("imageUrl", list.get(pos).getImageUrl());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(foodAdapter);

        btnAdd.setOnClickListener(v ->{
            startActivity(new Intent(this, UploadRecipeDetails.class));
        });
//        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        progressDialog.show();
        db.collection("recipes")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    list.clear();
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document :task.getResult()){
                            Upload recipe = new Upload(document.getString("author"), document.getString("imageUrl"), document.getString("recipe"), document.getString("name"), document.getString("authorName"));
                            recipe.setId(document.getId());
                            list.add(recipe);
                        }
                        foodAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getApplicationContext(), "Data failed fetching", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
    }

    private void deleteData(String id){
        progressDialog.show();
        db.collection("recipes").document(id)
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Data filed to delete", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Data successfully delete", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    getData();
                    Intent intent = new Intent( MyRecipeActivity.this, navbar.class);
                    startActivity(intent);
                }
            });

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        Intent intent =  new Intent(MyRecipeActivity.this, navbar.class);
//        startActivity(intent);
//        return super.onSupportNavigateUp();
//    }
}