package com.afundacion.gestorfinanzasdesarrollointerfaces.Utils;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;

public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    TextView descriptionTextView, quantityTextView;

    public DataViewHolder(View v) {
        super(v);
        descriptionTextView = (TextView)v.findViewById(R.id.descripcionHistorial);
        quantityTextView = (TextView)v.findViewById(R.id.cantidadHistorial);
        v.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menuInfo is null
        menu.add(0, R.id.editar,
                Menu.NONE, "Editar");
        menu.add(0, R.id.eliminar,
                Menu.NONE, "Eliminar");
    }


}
