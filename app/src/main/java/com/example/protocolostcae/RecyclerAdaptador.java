package com.example.protocolostcae;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerAdaptador extends RecyclerView.Adapter<RecyclerAdaptador.ViewHolder> {

    Context c;
    private ArrayList<IndexBusqueda> listaIndexBusqueda;
    private OnItemClickListener listener;

    //Constructor que recibe parametros y los agrega a los de esta clase
    public RecyclerAdaptador(ArrayList<IndexBusqueda> lista, OnItemClickListener listener) {
        this.listaIndexBusqueda = lista;
        this.listener = listener;

    }

    //ViewHolder obtenemos la vista de nuestro adaptador
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        c = parent.getContext();
        //Retornamos la vista
        return viewHolder;
    }

    //En este metodo recibimos la vista y la posicion del elemento de nuestro recycler
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //retornamos la posicion actual
        holder.bind(listaIndexBusqueda.get(position), position, listener, holder.fav);

    }

    @Override
    public int getItemCount() {
        return listaIndexBusqueda.size();
    }

    public void setFilter(ArrayList<IndexBusqueda> listaIndexBusqueda) {
        this.listaIndexBusqueda = new ArrayList<>();
        this.listaIndexBusqueda.addAll(listaIndexBusqueda);
        notifyDataSetChanged();
    }

    //Con interface podemos utilizar metodos y variables de manera polimorfica
    public interface OnItemClickListener {
        //metodo onclick donde retornaremos la posicion de cada elemento de la lista
        void onItemClick(int posicion);

        void onFavClick(int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtnota;
        ImageView fav;

        public ViewHolder(View itemView) {
            super(itemView);
            //llamamos el texview del xml
            txtnota = (TextView) itemView.findViewById(R.id.txtNota);
            fav = (ImageView) itemView.findViewById(R.id.fav);
        }

        public void bind(final IndexBusqueda nota, final int posicion, final OnItemClickListener listener, final ImageView fav) {
            //Agregamos la nota a nuestro texview del adaptador
            txtnota.setText(nota.getNota());

            if (!Favoritos.getInstance().esFavorito(posicion)) {
                fav.setImageResource(R.drawable.fav_border);
            } else {
                fav.setImageResource(R.drawable.fav_black);
            }

            System.out.println(nota.getNota());


            //Metodo onclic cuando nosotros precionemos un elemento se retornara la posicion
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(posicion);

                }
            });

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavClick(posicion);

                    if (Favoritos.getInstance().esFavorito(posicion)) {
                        Favoritos.getInstance().deleteFav(posicion);
                        fav.setImageResource(R.drawable.fav_border);
                        Toast.makeText(c, "Se ha quitado de Favoritos", Toast.LENGTH_SHORT).show();

                    } else {
                        Favoritos.getInstance().addFav(posicion);
                        fav.setImageResource(R.drawable.fav_black);
                        Toast.makeText(c, "Se ha añadido a Favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}