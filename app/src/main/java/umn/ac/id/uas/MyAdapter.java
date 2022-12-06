package umn.ac.id.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Food> foodArrayList;

    public MyAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Food food = foodArrayList.get(position);
        holder.foodHeading.setText(food.getHeading());
        Picasso.get().load(food.getFoodImage()).into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView foodImage;
        TextView foodHeading;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodHeading = itemView.findViewById(R.id.foodHeading);
        }
    }
}
