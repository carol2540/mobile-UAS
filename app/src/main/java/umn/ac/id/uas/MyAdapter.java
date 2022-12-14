package umn.ac.id.uas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Upload> recipes;

    public MyAdapter(Context context, ArrayList<Upload> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Upload recipe = recipes.get(position);
        holder.foodHeading.setText(recipe.getName());
        Log.d("IMAGEURL", recipe.getImageUrl());
        Picasso.get().load(recipe.getImageUrl()).into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodHeading;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodHeading = itemView.findViewById(R.id.foodHeading);
        }
    }
}
