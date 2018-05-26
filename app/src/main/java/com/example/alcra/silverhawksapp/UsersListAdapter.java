package com.example.alcra.silverhawksapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Users;

import java.util.List;

/**
 * Created by alcra on 15/05/2018.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder>{

    public List<Users> usersList;

    public UsersListAdapter(List<Users> usersList){

        this.usersList = usersList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nomeText.setText(usersList.get(position).getNome());
        holder.numeroText.setText(usersList.get(position).getNumero());
        holder.posicaoText.setText(usersList.get(position).getPosicao());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nomeText;
        public TextView numeroText;
        public TextView posicaoText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nomeText = mView.findViewById(R.id.tvNomeList);
            numeroText = mView.findViewById(R.id.tvNumeroList);
            posicaoText = mView.findViewById(R.id.tvPosicaoList);

        }
    }
}
