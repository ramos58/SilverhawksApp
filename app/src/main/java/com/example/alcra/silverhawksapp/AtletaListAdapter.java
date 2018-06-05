package com.example.alcra.silverhawksapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alcra.silverhawksapp.entities.Atleta;

import java.util.List;

/**
 * Created by alcra on 27/05/2018.
 */

public class AtletaListAdapter extends RecyclerView.Adapter<AtletaListAdapter.ViewHolder> {

    private List<Atleta> atletaList;
    private OnClick onClick;
    private Context context;

    public AtletaListAdapter(Context context, List<Atleta> atletaList) {
        this.atletaList = atletaList;
        this.context = context;
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
        final Atleta atleta = atletaList.get(holder.getAdapterPosition());

        holder.numberText.setText(atleta.getNumber());
//        Glide.with(context).load(atleta.getPicURL()).into(holder.photoImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClick != null){
                    onClick.click(atleta);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return atletaList.size();
    }

    public interface OnClick{
        void click(Atleta atleta);
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
