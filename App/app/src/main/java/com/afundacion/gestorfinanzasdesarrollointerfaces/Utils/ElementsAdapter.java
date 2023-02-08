package com.afundacion.gestorfinanzasdesarrollointerfaces.Utils;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;

import java.util.List;

public class ElementsAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private List<Transactions> data;
    private int position;

    public ElementsAdapter(List<Transactions> data){
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId(int position) { return data.get(position).getId(); }

    public String getDescripcion(int position) { return data.get(position).getDescripcionHistorial(); }

    public String getCantidad(int position) { return data.get(position).getCantidadHistorial(); }

    public String getType(int position) { return data.get(position).getTipoTransaccion(); }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View elementView = inflater.inflate(R.layout.elements_history, parent, false);
        DataViewHolder elementViewHolder = new DataViewHolder(elementView);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position){
        Transactions dataForElement = this.data.get(position);
        holder.descriptionTextView.setText(dataForElement.getDescripcionHistorial());
        holder.quantityTextView.setText(dataForElement.getCantidadHistorial());

        if (dataForElement.getTipoTransaccion().equalsIgnoreCase("Ingreso")) {
            holder.quantityTextView.setTextColor(Color.parseColor("#4CAF50"));
        }else if (dataForElement.getTipoTransaccion().equalsIgnoreCase("Gasto")) {
            holder.quantityTextView.setTextColor(Color.parseColor("#D30E00"));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }
}
