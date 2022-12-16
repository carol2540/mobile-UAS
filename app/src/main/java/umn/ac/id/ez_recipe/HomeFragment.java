package umn.ac.id.ez_recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    View view;
    ArrayList<Upload> recipes;
    RecyclerView recyclerview;
    FirebaseFirestore db;
//    ProgressDialog progressDialog;

//    @Override
//    public void onCreate(Bundle saveInstanceState) {
//        super.onCreate(saveInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        recipes  = new ArrayList<Upload>();

//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("data fatching...");
//        progressDialog.show();

        db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()){
                        recipes.add(document.toObject(Upload.class));
                    }
                    recyclerview = view.findViewById(R.id.recyclerview);
                    recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerview.setHasFixedSize(true);
                    MyAdapter myAdapter = new MyAdapter(getActivity(),recipes);
                    recyclerview.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                }
            }
        });
        return view;
    }

//    private void dataInitialize() {
//
//        db.collection("foods").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//                                recipes.add(document.toObject(Food.class));
//                            }
//
//                        }
//                    }
//                });
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null){
//                            if (progressDialog.isShowing())
//                                progressDialog.dismiss();
//                            Log.e("Firestore error", error.getMessage());
//                            return;
//                        }
//
//                        for (DocumentChange dc : value.getDocumentChanges()){
//                            if (dc.getType() == DocumentChange.Type.ADDED){
//
//                                foodArrayList.add(dc.getDocument().toObject(Food.class));
//
//                            }
//
//                            myAdapter.notifyDataSetChanged();
//                            if (progressDialog.isShowing())
//                                progressDialog.dismiss();
//                        }
//
//
//                    }
//                });
    }
