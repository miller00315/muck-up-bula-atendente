package br.com.miller.farmaciaatendente.mainMenu.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.miller.farmaciaatendente.R;

public class SolicitationViewHolder extends RecyclerView.ViewHolder {

    private TextView solicitationId, solicitationDate, solicitationSend, solicitationReceive;
    private LinearLayout solicitationLayout;

    public SolicitationViewHolder(@NonNull View itemView) {
        super(itemView);

        solicitationId = itemView.findViewById(R.id.solicitation_id);
        solicitationDate = itemView.findViewById(R.id.solicitation_date);
        solicitationSend = itemView.findViewById(R.id.solicitation_send);
        solicitationReceive = itemView.findViewById(R.id.solicitation_receive);
        solicitationLayout = itemView.findViewById(R.id.solicitation_layout);
    }

    public TextView getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(TextView solicitationId) {
        this.solicitationId = solicitationId;
    }

    public TextView getSolicitationDate() {
        return solicitationDate;
    }

    public void setSolicitationDate(TextView solicitationDate) {
        this.solicitationDate = solicitationDate;
    }

    public TextView getSolicitationSend() {
        return solicitationSend;
    }

    public void setSolicitationSend(TextView solicitationSend) {
        this.solicitationSend = solicitationSend;
    }

    public TextView getSolicitationReceive() {
        return solicitationReceive;
    }

    public void setSolicitationReceive(TextView solicitationReceive) {
        this.solicitationReceive = solicitationReceive;
    }

    public LinearLayout getSolicitationLayout() {
        return solicitationLayout;
    }

    public void setSolicitationLayout(LinearLayout solicitationLayout) {
        this.solicitationLayout = solicitationLayout;
    }
}
