package com.example.alcra.silverhawksapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Atleta;
import com.example.alcra.silverhawksapp.entities.Chamada;

import java.util.List;

/**
 * Created by alcra on 27/05/2018.
 */

public class AtletaListAdapter extends RecyclerView.Adapter<AtletaListAdapter.ViewHolder> {

    private List<Atleta> atletaList;
    private OnClick onClick;

    public AtletaListAdapter(List<Atleta> atletaList) {
        this.atletaList = atletaList;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public AtletaListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atleta, parent, false);
        return new AtletaListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AtletaListAdapter.ViewHolder holder, int position) {
//        final Atleta atleta = atletaList.get(holder.getAdapterPosition());
//
//        holder.numberText.setText(atleta.toString());
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
        return atletaList.size();
    }

    public interface OnClick{
        void click(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView numberText;
        public ImageView photoImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            numberText = itemView.findViewById(R.id.tv_number);
            photoImageView = itemView.findViewById(R.id.iv_item_atleta);
        }
    }
}
