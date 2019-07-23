package br.com.miller.farmaciaatendente.mainMenu.adapters.arrayAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.farmaciaatendente.R;
import br.com.miller.farmaciaatendente.domain.Departament;

public class SpinnnerArrayAdapterDepartament extends ArrayAdapter<Departament> {

    private ArrayList<Departament> departaments;
    private Context context;

    public SpinnnerArrayAdapterDepartament(Context context, ArrayList<Departament> departaments) {
        super(context, 0, departaments);

        this.departaments = departaments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_view,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.departament_title);

        textView.setText(departaments.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_view,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.departament_title);

        textView.setText(departaments.get(position).getTitle());

        return convertView;
    }

    public ArrayList<Departament> getDepartaments() {
        return departaments;
    }

    public void setDepartaments(ArrayList<Departament> departaments) {
        this.departaments = departaments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return departaments != null ? departaments.size() : 0;
    }
}
