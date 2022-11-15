package umn.ac.id.uas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private ArrayList<Food> foodArrayList;
    private String[] foodHeading;
    private int[] foodImageResource;
    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        dataInitialize();
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(),foodArrayList);
        recyclerview.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        foodArrayList = new ArrayList<>();

        foodHeading = new String[]{
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
                getString(R.string.foodHead_1),
        };

        foodImageResource = new int[]{
                R.drawable.food1,
                R.drawable.food8,
                R.drawable.food2,
                R.drawable.food3,
                R.drawable.food4,
                R.drawable.food5,
                R.drawable.food6,
                R.drawable.food7,

        };

        for (int i=0; i<foodHeading.length;i++){
            Food food = new Food(foodHeading[i],foodImageResource[i]);
            foodArrayList.add(food);
        }
    }
}