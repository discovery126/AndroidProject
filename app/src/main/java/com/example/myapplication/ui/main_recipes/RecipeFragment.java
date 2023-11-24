package com.example.myapplication.ui.main_recipes;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DescriptionRecipe;
import com.example.myapplication.FavouriteRecipe;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    ArrayList<String> descriptionsArray = new ArrayList<>();
    ArrayList<String> componentsRecipeArray = new ArrayList<>();
    static ArrayList<Boolean> checked = new ArrayList<>();
    View root;

    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_page_recipe,container,false);

        if (checked.size()==0) {
            for (int i = 0; i < MainRecipesFragment.fullListRecipeModels.size(); i++) {
                checked.add(false);
            }
        }

        setDescriptionRecipe();
        setPageRecipe(root);
        addToCard();
        return root;
    }
    private void setDescriptionRecipe() {
        if (descriptionsArray.size()==0) {
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_CAESAR);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_OLIVIE);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_BORSCH);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_SOUP_WITH_MEATBALLS);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_UHA);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_LULA_KEBAB);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_POTATO_PIE);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_ANNA_PAVLOVA);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_NUTS_DESSERT);
            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_SELEDKA_POD_SHUBOI);
        }
        if (componentsRecipeArray.size()==0) {
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_CAESAR);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_OLIVIE);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_BORSCH);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SOUP_WITH_MEATBALLS);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_UHA);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_LULA_KEBAB);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_POTATO_PIE);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_ANNA_PAVLOVA);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_NUTS_DESSERT);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SELEDKA_POD_SHUBOI);
        }
    }
    private void setPageRecipe(View view) {
        TextView tittle = view.getRootView().findViewById(R.id.title);

        TextView authorRecipeInDescription = view.findViewById(R.id.authorRecipeInDescription);

        ImageView imageRecipeInRecipe = view.findViewById(R.id.imageRecipeInRecipe);

        ListView pageRecipeListView = view.getRootView().findViewById(R.id.pageRecipeListView);

        Button a = view.findViewById(R.id.componentButton);

        Button b = view.findViewById(R.id.cookingRecipeButton);

        int position = getArguments().getInt("pos");
        String[] componentsRecipe = componentsRecipeArray.get(position).split("\\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,componentsRecipe);
        pageRecipeListView.setAdapter(adapter);

        a.setOnClickListener(view1 -> {
            ArrayAdapter<String> adapter12 = new ArrayAdapter<>(view1.getContext(), android.R.layout.simple_list_item_1,componentsRecipe);
            pageRecipeListView.setAdapter(adapter12);
        });

        b.setOnClickListener(view12 -> {
            String[] cookingRecipe = descriptionsArray.get(position).split("\\n");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(view12.getContext(), android.R.layout.simple_list_item_1,cookingRecipe);
            pageRecipeListView.setAdapter(adapter1);
        });

        tittle.setText(getArguments().getString("tittleRecipe"));
        authorRecipeInDescription.setText("Автор: ".concat(getArguments().getString("authorRecipe")));
        imageRecipeInRecipe.setImageResource(getArguments().getInt("imageRecipe"));
    }
    public void addToCard() {
        CheckBox favouriteCheckBox= root.findViewById(R.id.favouriteCheckBox);
        id = getArguments().getInt("pos");
        favouriteCheckBox.setChecked(checked.get(id));
        favouriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (favouriteCheckBox.isChecked()) {
                    FavouriteRecipe.items_id.add(id);
                    Toast.makeText(getActivity(), "Добавлено", Toast.LENGTH_SHORT).show();
                } else {
                    FavouriteRecipe.items_id.remove(id);
                    Toast.makeText(getActivity(), "Удалено", Toast.LENGTH_SHORT).show();
                }
                checked.set(id, favouriteCheckBox.isChecked());

            }
        });

    }

}