package com.example.alcra.silverhawksapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alcra.silverhawksapp.entities.Atleta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcra on 27/05/2018.
 */

public class AtletaListAdapter extends RecyclerView.Adapter<AtletaListAdapter.ViewHolder> implements Filterable{

    private static List<Atleta> atletaList;
    private static List<Atleta> atletaListFiltered;
    private OnClick onClick;
    private Context context;

    public AtletaListAdapter(Context context, List<Atleta> atletaList) {
        this.atletaList = atletaList;
        this.atletaListFiltered = atletaList;
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
        final Atleta atleta = atletaListFiltered.get(holder.getAdapterPosition());

        holder.numberText.setText(atleta.getLastName()+" #"+atleta.getNumber());
        Glide.with(context).load(atleta.getPicURL()).into(holder.photoImageView);
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
        return atletaListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    atletaListFiltered = atletaList;
                } else {
                    List<Atleta> filteredList = new ArrayList<>();
                    for (Atleta athlete : atletaList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (athlete.getNameComp().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(athlete);
                        }
                    }

                    atletaListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = atletaListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                atletaListFiltered = (ArrayList<Atleta>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
