package br.com.miller.farmaciaatendente.utils.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Evaluate;
import br.com.miller.farmaciaatendente.superClass.RecyclerItem;
import br.com.miller.farmaciaatendente.utils.viewHolders.OpinionUserViewHolder;

public class OpinionStoreRecyclerAdapter extends RecyclerItem {

    private static final String ID = OpinionStoreRecyclerAdapter.class.getName();
    private ArrayList<Evaluate> evaluates;
    private Context context;

    public static OpinionStoreRecyclerAdapter newInstance(){
        return new OpinionStoreRecyclerAdapter();
    }

    public void configure(RecyclerItem.OnAdapterInteract onAdapterInteract, Context context){

        this.listener = onAdapterInteract;
        this.context = context;
        evaluates = new ArrayList<>();
    }

    @Override
    public void showItem(int i) {

        Bundle bundle = new Bundle();

        bundle.putInt("type", 24);
        bundle.putString("opinionId", evaluates.get(i).getBuyId());

        listener.onAdapterInteract(bundle);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OpinionUserViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_user_opinion,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof OpinionUserViewHolder){

            OpinionUserViewHolder opinionUserViewHolder = (OpinionUserViewHolder) viewHolder;

            opinionUserViewHolder.getClassificatioStore().setText(String.valueOf(evaluates.get(i).getValue()));
            opinionUserViewHolder.getMessageUser().setText(evaluates.get(i).getMessage());
            opinionUserViewHolder.getNameUser().setText(evaluates.get(i).getUserName());
            opinionUserViewHolder.downloadImage("users", evaluates.get(i).getCity(), evaluates.get(i).getIdUser());
        }

    }

    public ArrayList<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(ArrayList<Evaluate> evaluates) {
        this.evaluates = evaluates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return evaluates != null ? evaluates.size() : 0;
    }
}
