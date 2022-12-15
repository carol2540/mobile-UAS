package umn.ac.id.uas.adapter;

import android.app.Dialog;
import android.content.Context;
import android.hardware.lights.LightState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import umn.ac.id.uas.MyAdapter;
import umn.ac.id.uas.R;
import umn.ac.id.uas.Upload;
import umn.ac.id.uas.model.Recipe;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    Context context;
    ArrayList<Upload> recipes;
    static Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public FoodAdapter(Context context, ArrayList<Upload> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipe, parent, false);
        return new MyViewHolder(itemView);
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

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodHeading;
        TextView foodName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodHeading = itemView.findViewById(R.id.foodName);

            foodName = itemView.findViewById(R.id.foodName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
