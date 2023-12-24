package com.example.myapplication.ui.main_recipes;


import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.DescriptionRecipe;
import com.example.myapplication.ListenerInterface;
import com.example.myapplication.ListenerInterfaceCategory;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.CategoryModel;
import com.example.myapplication.database.RecipeModel;
import com.example.myapplication.adapter.RecipeAdapter;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.database.Recipe_Categories;
import com.example.myapplication.database.Recipe_CategoriesDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainRecipesFragment extends Fragment implements ListenerInterface, ListenerInterfaceCategory {

    private View rootView;
    private ArrayList<String> componentsRecipeArray = new ArrayList<>();
    private ArrayList<String> descriptionsArray = new ArrayList<>();
    private ArrayList<Integer> numberComponent = new ArrayList<>();
    private MainDb db;
    private RecipeAdapter recipeAdapter;
    private int countRecipe;
    private int countCategories;
    private List<RecipeModel> fullListRecipeModels = new ArrayList<>();
    private List<CategoryModel> listCategoriesModels = new ArrayList<>();
    private List<RecipeModel> listRecipeModels = new ArrayList<>();
    private List<RecipeModel> recipeModels = new ArrayList<>();
    private RecipeModel currentRecipeModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main_recipes, container,false);

        db = Room.databaseBuilder(rootView.getContext(), MainDb.class, "RecipeBD").build();

        try {
            setUpCategoryModels();// Создаёт ячейки каждой модели
            setUpRecipeModels(); // Создаёт ячейки каждого рецепта
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// По одному в списке
        Thread thread = new Thread(() -> {
            listCategoriesModels = db.getCategoryDao().getAllCategories();
            fullListRecipeModels = db.getRecipeDao().getAllRecipe();
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listRecipeModels.addAll(fullListRecipeModels);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recipeAdapter = new RecipeAdapter(rootView.getContext(),listRecipeModels,this);
        mRecyclerView.setAdapter(recipeAdapter);

        CategoryAdapter categoryAdapter;

        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryAdapter = new CategoryAdapter(rootView.getContext(),listCategoriesModels,this);
        categoryRecyclerView.setAdapter(categoryAdapter);

        return rootView;
    }

    private void setUpRecipeModels() throws InterruptedException {
        Thread thread = new Thread(() ->
                countRecipe = db.getRecipeDao().getCountRecipe());
        thread.start();
        thread.join();


        if (countRecipe == 0) {
            String[] tittlesRecipe = getResources().getStringArray(R.array.tittle_recipes);
            String[] authorsRecipe = getResources().getStringArray(R.array.author_recipes);
            int[][] numberCategoryRecipe = new int[][] {{3},{3},{1,5},{1,5},{1,6},{2,5},{2,5},{4},{4},{3,6}};
            int[] images = new int[] {R.drawable.caesar,R.drawable.olivie,R.drawable.borsch,
                    R.drawable.soup_with_meatballs,R.drawable.uha,R.drawable.lula_kebab,
                    R.drawable.potato_pie,R.drawable.anna_pavlova,R.drawable.nuts_dessert,R.drawable.seledka_pod_shuboi};
            for (int i =0; i < tittlesRecipe.length;i++) {

                List<Integer> categoryRecipe = new ArrayList<>();
                for (int j =0; j < numberCategoryRecipe[i].length; j++) {
                    categoryRecipe.add(numberCategoryRecipe[i][j]);
                }

                RecipeModel recipeModel = new RecipeModel(
                        tittlesRecipe[i],
                        authorsRecipe[i],
                        numberComponent.get(i),
                        componentsRecipeArray.get(i),
                        descriptionsArray.get(i),
                        images[i]
                );

                int finalI = i;

                Thread thread1 = new Thread(() -> {
                    db.getRecipeDao().addRecipe(recipeModel);
                    int recipeId = db.getRecipeDao().getRecipeId(tittlesRecipe[finalI]);
                    for (int f =0; f < categoryRecipe.size();f++) {
                        Recipe_Categories recipeCategories = new Recipe_Categories(categoryRecipe.get(f),recipeId);
                        db.getRecipCategDao().addRecipeCategories(recipeCategories);
                    }
                });

                thread1.start();
                thread1.join();
            }
            //fullListRecipeModels.addAll(listRecipeModels); // Добавляем в общую категорию рецептов
        }

    }

    /*Инициализирует все категории*/
    private void setUpCategoryModels() throws InterruptedException {
        Thread thread = new Thread(() ->
                countCategories = db.getCategoryDao().getCountCategory());
        thread.start();
        thread.join();
        if (countCategories == 0) {
            String[] categories = getResources().getStringArray(R.array.categories);
            for (String category : categories) {
                CategoryModel categoryModel = new CategoryModel(category);
                Thread thread1 = new Thread(() ->
                        db.getCategoryDao().addCategory(categoryModel));
                thread1.start();
                thread1.join();
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
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_NUTS_DESSERT);
            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SELEDKA_POD_SHUBOI);
            for (int i = 0; i < componentsRecipeArray.size(); i++) {
                String[] st1 = componentsRecipeArray.get(i).split("\\n");
                numberComponent.add(st1.length); // Число компонентов для главного фрагмента
            }
        }

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
    }


    @Override
    public void onItemClicked(int position) {
        Thread thread = new Thread(() -> currentRecipeModel =
                db.getRecipeDao().getRecipe(listRecipeModels.get(position).getIdRecipe()));

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", currentRecipeModel.getIdRecipe());

        Navigation.findNavController(rootView).navigate(R.id.action_main_recipes_to_recipeFragment,bundle);
    }

    @Override
    public void onItemLongClicked(int position) {

    }

    @Override
    public void onItemClickedCategory(int category) {
        ArrayList<RecipeModel> filterRecipe = new ArrayList<>();
        if (category != 0) {

            Thread thread = new Thread(() ->
                    recipeModels = db.getRecipCategDao().getRecipeByCategoryId(category));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            filterRecipe.addAll(recipeModels);
        } else { // Если нажимают на кнопку Все
            filterRecipe.addAll(fullListRecipeModels); // Если нажимают на кнопку Все
        }
        listRecipeModels.clear();
        listRecipeModels.addAll(filterRecipe);

        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClickedCategory(int category) {

    }
}