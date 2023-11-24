package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ListenerInterface;
import com.example.myapplication.ListenerInterfaceCategory;
import com.example.myapplication.R;
import com.example.myapplication.model.CategoryModel;
import com.example.myapplication.ui.main_recipes.MainRecipesFragment;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    Context context;
    ArrayList<CategoryModel> categories;
    ListenerInterfaceCategory listener;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categories,ListenerInterfaceCategory listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_category,parent,false);
        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.categoryTitle.setText(categories.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Передаёт номер категории рецепта
                listener.onItemClickedCategory(categories.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTitle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.item_category);
        }

    }

}
