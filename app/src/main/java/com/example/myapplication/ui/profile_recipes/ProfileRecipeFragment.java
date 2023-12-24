package com.example.myapplication.ui.profile_recipes;

import static com.example.myapplication.MainActivity.ID;
import static com.example.myapplication.MainActivity.LOGIN;
import static com.example.myapplication.MainActivity.AUTHORIZATION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ProfileRecipeFragment extends Fragment {

    private View view;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private String loginUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_recipe, container, false);
        Button buttonToChanges = view.findViewById(R.id.buttonToChangeProfile);
        TextView loginText = view.findViewById(R.id.textLogin);
        TextView buttonLogout = view.findViewById(R.id.buttonLogout);

        settings = getActivity().getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE);
        editor = settings.edit();

        loginUser = settings.getString(LOGIN,"");

        loginText.setText(loginUser);
        buttonToChanges.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_profile_to_changes_login_or_password));
        buttonLogout.setOnClickListener(view -> {

            editor.putInt(ID,0);
            editor.putString(MainActivity.LOGIN,"");
            editor.apply();

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}