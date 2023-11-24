package com.example.myapplication.ui.main_recipes;


import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.myapplication.DescriptionRecipe;
import com.example.myapplication.FavouriteRecipe;
import com.example.myapplication.ListenerInterface;
import com.example.myapplication.ListenerInterfaceCategory;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.model.CategoryModel;
import com.example.myapplication.model.RecipeModel;
import com.example.myapplication.adapter.RecipeAdapter;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainRecipesFragment extends Fragment implements ListenerInterface, ListenerInterfaceCategory {

    public  static ArrayList<RecipeModel> listRecipeModels = new ArrayList<>();
    public  static ArrayList<RecipeModel> fullListRecipeModels = new ArrayList<>();
    public  ArrayList<CategoryModel> listCategoriesModels = new ArrayList<>();
    View rootView;
    CategoryAdapter categoryAdapter;
    public static RecipeAdapter recipeAdapter;
    ArrayList<String> componentsRecipeArray = new ArrayList<>();
    ArrayList<String> numberComponent = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main_recipes, container,false);

        setUpCategoryModels(); // Создаёт ячейки каждой модели
        setUpRecipeModels(); // Создаёт ячейки каждого рецепта

        // mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// По одному в списке

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recipeAdapter = new RecipeAdapter(rootView.getContext(),listRecipeModels,this);
        mRecyclerView.setAdapter(recipeAdapter);

        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryAdapter = new CategoryAdapter(rootView.getContext(),listCategoriesModels,this);
        categoryRecyclerView.setAdapter(categoryAdapter);

        return rootView;
    }

    private void setUpRecipeModels() {
        if (listRecipeModels.size()==0) {
        String[] tittlesRecipe = getResources().getStringArray(R.array.tittle_recipes);
        String[] authorsRecipe = getResources().getStringArray(R.array.author_recipes);
        int[][] numberCategoryRecipe = new int[][] {{3},{3},{1,5},{1,5},{1,6},{2,5},{2,5},{4},{4},{3,6}};
        int[] images = new int[] {R.drawable.caesar,R.drawable.olivie,R.drawable.borsch,
                R.drawable.soup_with_meatballs,R.drawable.uha,R.drawable.lula_kebab,
                R.drawable.potato_pie,R.drawable.anna_pavlova,R.drawable.nuts_dessert,R.drawable.seledka_pod_shuboi};
            for (int i =0; i < tittlesRecipe.length;i++) {

                int[] currentNumbersCategoryRecipe = new int[numberCategoryRecipe[i].length];

                for (int j =0; j < numberCategoryRecipe[i].length; j++)
                        currentNumbersCategoryRecipe[j] = numberCategoryRecipe[i][j];

                listRecipeModels.add(new RecipeModel(tittlesRecipe[i],
                        authorsRecipe[i],
                        numberComponent.get(i).concat(" Ингредиентов"),
                        currentNumbersCategoryRecipe,
                        images[i]));
            }
            fullListRecipeModels.addAll(listRecipeModels); // Добавляем в общую категорию рецептов
        }

    }

    /*Инициализирует все категории*/
    private void setUpCategoryModels() {
        String[] categories = getResources().getStringArray(R.array.categories);
        if (listCategoriesModels.size()==0) {
            for (int i =0; i < categories.length;i++) {
                listCategoriesModels.add(new CategoryModel(i,categories[i])); // Номер категории, название категории
            }
        }
        countComponentRecipeArray();

    }
    /* Инициализирует все компоненты и считает их для главного фрагмента */
    private void countComponentRecipeArray() {
        if (componentsRecipeArray.size()==0) {
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_CAESAR);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_OLIVIE);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_BORSCH);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SOUP_WITH_MEATBALLS);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_UHA);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_LULA_KEBAB);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_POTATO_PIE);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_ANNA_PAVLOVA);
            componentsRecipeArray.add(DescriptionRecipe.DESCRIPTION_NUTS_DESSERT);
            componentsRecipeArray.add(DescriptionRecipe.DESCRIPTION_SELEDKA_POD_SHUBOI);

            for (int i =0; i < componentsRecipeArray.size();i++) {
                String[] st1 = componentsRecipeArray.get(i).split("\\n");
                numberComponent.add(String.valueOf(st1.length)); // Число компонентов для главного фрагмента
            }
        }
    }


    @Override
    public void onItemClicked(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("tittleRecipe",listRecipeModels.get(position).getTittleRecipe());
        bundle.putString("authorRecipe",listRecipeModels.get(position).getAuthorRecipe());
        bundle.putString("numberComponents",listRecipeModels.get(position).getComponentsRecipe());
        bundle.putInt("imageRecipe",listRecipeModels.get(position).getImageRecipe());
        bundle.putInt("pos",position);

        Navigation.findNavController(rootView).navigate(R.id.action_main_recipes_to_recipeFragment,bundle);
    }

    @Override
    public void onItemLongClicked(int position) {

    }

    @Override
    public void onItemClickedCategory(int category) {
        ArrayList<RecipeModel> filterRecipe = new ArrayList<>();
        if (category != 0) {
            for (RecipeModel recipe : fullListRecipeModels) {
                boolean flag = false;
                int[] currentCategoryRecipe = recipe.getCategory();

                for (int j : currentCategoryRecipe) {
                    if (j == category) {
                        flag = true;
                        break;
                    }

                }
                if (flag) {
                    filterRecipe.add(recipe);
                }
            }
        } else { // Если нажимают на кнопку Все
            filterRecipe.addAll(fullListRecipeModels);
        }
        listRecipeModels.clear();
        listRecipeModels.addAll(filterRecipe);

        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClickedCategory(int category) {

    }
}