package com.example.alcra.silverhawksapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Chamada;
import com.example.alcra.silverhawksapp.entities.Presenca;

import java.util.List;

import static com.example.alcra.silverhawksapp.entities.Presenca.F;
import static com.example.alcra.silverhawksapp.entities.Presenca.J;
import static com.example.alcra.silverhawksapp.entities.Presenca.P;

/**
 * Created by alcra on 27/05/2018.
 */

public class ChamadaListAdapter extends RecyclerView.Adapter<ChamadaListAdapter.ViewHolder> {

    private List<Chamada> chamadaList;
    private OnClick onClick;

    public ChamadaListAdapter(List<Chamada> chamadaList) {
        this.chamadaList = chamadaList;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public ChamadaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamada, parent, false);
        return new ChamadaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChamadaListAdapter.ViewHolder holder, int position) {
        final Chamada chamada = chamadaList.get(holder.getAdapterPosition());

        holder.dataText.setText(chamada.getDate());
        holder.localText.setText(chamada.getLocal());
        holder.tipoText.setText(chamada.getTipo().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClick != null){
                    onClick.click(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chamadaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dataText;
        public TextView localText;
        public TextView tipoText;

        public ViewHolder(View itemView) {
            super(itemView);

            dataText = itemView.findViewById(R.id.tv_data);
            localText = itemView.findViewById(R.id.tv_local);
            tipoText = itemView.findViewById(R.id.tv_tipo);
        }
    }

    public interface OnClick{
        void click(int position);
    }
}
