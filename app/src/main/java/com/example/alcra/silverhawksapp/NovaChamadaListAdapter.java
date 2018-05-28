package com.example.alcra.silverhawksapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Presenca;

import java.util.List;

import static com.example.alcra.silverhawksapp.entities.Presenca.F;
import static com.example.alcra.silverhawksapp.entities.Presenca.J;
import static com.example.alcra.silverhawksapp.entities.Presenca.P;

/**
 * Created by alcra on 27/05/2018.
 */

public class NovaChamadaListAdapter extends RecyclerView.Adapter<NovaChamadaListAdapter.ViewHolder> {

    public List<Presenca> chamadaList;

    public NovaChamadaListAdapter(List<Presenca> chamadaList) {

        this.chamadaList = chamadaList;

    }

    @Override
    public NovaChamadaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nova_chamada, parent, false);
        return new NovaChamadaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NovaChamadaListAdapter.ViewHolder holder, int position) {
        Presenca presenca = chamadaList.get(holder.getAdapterPosition());

        holder.nomeText.setText(presenca.getName());
        if (presenca.getTipo() != 0) {
            switch (presenca.getTipo()) {
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
        }

        holder.presencaRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(NovaChamadaListAdapter.class.getSimpleName(), "id: " + i);
                switch (i){
                    case R.id.rb_presente:
                        chamadaList.get(holder.getAdapterPosition()).setTipo(Presenca.P);
                        break;
                    case R.id.rb_justificado:
                        chamadaList.get(holder.getAdapterPosition()).setTipo(Presenca.J);
                        break;
                    case R.id.rb_falta:
                        chamadaList.get(holder.getAdapterPosition()).setTipo(Presenca.F);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chamadaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nomeText;
        public RadioGroup presencaRG;

        public ViewHolder(View itemView) {
            super(itemView);

            nomeText = itemView.findViewById(R.id.tv_nome);
            presencaRG = itemView.findViewById(R.id.rg_presenca);

        }
    }
}
