package br.com.miller.farmaciaatendente.recoveryPassword.models;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import br.com.miller.farmaciaatendente.recoveryPassword.tasks.RecoveryPasswordTasks;

public class RecoveryPasswordModel {

    private RecoveryPasswordTasks.Model model;
    private FirebaseAuth firebaseAuth;

    public RecoveryPasswordModel(RecoveryPasswordTasks.Model model) {
        this.model = model;
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void resetPassword(String email){

        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) { model.onRecoverySuccess(); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) { model.onRecoveryFailed(e); }
        });
    }
}
