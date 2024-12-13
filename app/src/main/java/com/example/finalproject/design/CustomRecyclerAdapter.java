package com.example.finalproject.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.ListArrayItem;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyHolderView> {

    private final Context context;
    public final ArrayList<ListArrayItem> listArrayItems;
    private final LayoutInflater inflater;

    public CustomRecyclerAdapter(Context context, ArrayList<ListArrayItem> listArrayItems) {
        this.context = context;
        this.listArrayItems = listArrayItems;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        ListArrayItem listArrayItem = listArrayItems.get(position);
        //holder.imageView.setImageResource(listArrayItem.getImagen());
        holder.textView.setText(listArrayItem.getUsuario());
        holder.puntos.setText(String.valueOf(listArrayItem.getPuntos()));
        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return listArrayItems.size();
    }


    //return index of array
    public ListArrayItem getItemAtIndex(int i) {
        if (i >= 0 && i < listArrayItems.size()) {
            return listArrayItems.get(i);
        } else {
            return null;
        }
    }

    //añadir un item mas al recyclerview
    public void addItem(ListArrayItem newItem) {
        listArrayItems.add(newItem);
        notifyItemInserted(listArrayItems.size() - 1);
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        //private final ImageView imageView;
        private final TextView textView;
        private final TextView puntos;
        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.imagen_item_rv);
            textView = itemView.findViewById(R.id.usuario_item_rv);
            puntos = itemView.findViewById(R.id.puntos_item_rv);
        }

        public void setListener(int posicion) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListArrayItem item = listArrayItems.get(posicion);
                    Toast.makeText(context, "Usuario: " + item.getUsuario(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
