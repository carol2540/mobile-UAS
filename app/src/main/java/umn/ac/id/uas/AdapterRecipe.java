package umn.ac.id.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.MyHolderRecipe> {

    Context context;
    ArrayList<Food> foodArrayList;

    public AdapterRecipe(Context context, ArrayList<Food> foodArrayList){
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public MyHolderRecipe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_recipe,parent,false);
        return new MyHolderRecipe(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderRecipe holder, int position) {

        Food food = foodArrayList.get(position);
        holder.foodHeading.setText(food.heading);
        holder.foodImage.setImageResource(food.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyHolderRecipe extends RecyclerView.ViewHolder{

        ShapeableImageView foodImage;
        TextView foodHeading;

        public MyHolderRecipe(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.recipe_image);
            foodHeading = itemView.findViewById(R.id.recipe_image);
        }
    }
}
