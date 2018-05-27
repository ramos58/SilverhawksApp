package com.example.alcra.silverhawksapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Presenca;
import com.example.alcra.silverhawksapp.entities.Users;

import java.util.List;

import static com.example.alcra.silverhawksapp.entities.Presenca.F;
import static com.example.alcra.silverhawksapp.entities.Presenca.J;
import static com.example.alcra.silverhawksapp.entities.Presenca.P;

/**
 * Created by alcra on 27/05/2018.
 */

public class ChamadaListAdapter extends RecyclerView.Adapter<ChamadaListAdapter.ViewHolder>{

    public List<Presenca> chamadaList;

    public ChamadaListAdapter(List<Presenca> chamadaList){

        this.chamadaList = chamadaList;

    }

    @Override
    public ChamadaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamada, parent, false);
        return new ChamadaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChamadaListAdapter.ViewHolder holder, int position) {
        final Presenca presenca = chamadaList.get(position);

        holder.nomeText.setText(presenca.getNome());
        switch (presenca.getTipo()){
            case P:
                holder.presencaRG.check(R.id.rb_presente);
                break;
            case J:
                holder.presencaRG.check(R.id.rb_justificado);
                break;
            case F:
                holder.presencaRG.check(R.id.rb_falta);
                break;
        }

        holder.presencaRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(ChamadaListAdapter.class.getSimpleName(),"id: "+i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chamadaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeText;
        public RadioGroup presencaRG;

        public ViewHolder(View itemView) {
            super(itemView);

            nomeText = itemView.findViewById(R.id.tv_nome);
            presencaRG = itemView.findViewById(R.id.rg_presenca);

        }
    }
}
