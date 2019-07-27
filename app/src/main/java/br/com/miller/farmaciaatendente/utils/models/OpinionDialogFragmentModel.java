package br.com.miller.farmaciaatendente.utils.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.domain.Evaluate;
import br.com.miller.farmaciaatendente.utils.tasks.OpnionDialogFragmentTasks;

public class OpinionDialogFragmentModel {

    private FirebaseDatabase firebaseDatabase;
    private OpnionDialogFragmentTasks.Model model;

    public OpinionDialogFragmentModel(OpnionDialogFragmentTasks.Model model) {
        this.model = model;
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void getOpinions(String storeId, String city){

        firebaseDatabase.getReference()
                .child("evaluate")
                .child(city)
                .child(storeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            ArrayList<Evaluate> evaluates = new ArrayList<>();

                            for(DataSnapshot child: dataSnapshot.getChildren()){

                                evaluates.add(child.getValue(Evaluate.class));
                            }

                            model.onGetEvaluateSuccess(evaluates);

                        }else{
                            model.onGetEvaluateFailed();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        model.onGetEvaluateFailed();
                    }
                });
    }

}
