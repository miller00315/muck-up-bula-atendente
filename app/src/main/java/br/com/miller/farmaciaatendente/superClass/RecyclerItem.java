package br.com.miller.farmaciaatendente.superClass;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public abstract  class RecyclerItem  extends RecyclerView.Adapter{

        protected OnAdapterInteract listener;

        public abstract void showItem(int i);

        public interface OnAdapterInteract{

            void onAdapterInteract(Bundle bundle);
        }


}
