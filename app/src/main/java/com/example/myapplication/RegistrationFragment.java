package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends Fragment {


    private MainDb db;
    private boolean checked;

    public RegistrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        db = Room.databaseBuilder(getContext().getApplicationContext(), MainDb.class, "RecipeBD").build();

        TextView loginRegistration = view.findViewById(R.id.login_text_registration);
        TextView passwordRegistration = view.findViewById(R.id.password_text_registration);
        Button buttonRegistration = view.findViewById(R.id.button_registration);
        ImageView cancel = view.findViewById(R.id.cancelImage);

        buttonRegistration.setOnClickListener(view12 -> {
            checked = false;
            String login = loginRegistration.getText().toString();
            String pass = passwordRegistration.getText().toString();

            User user = new User(login,pass);
            System.out.println(user.getLogin() + " " + user.getPassword());
            Thread thread = new Thread(() -> {
                try {
                    db.getUserDao().addUser(user);
                    checked = true;
                } catch (SQLiteConstraintException e) {
                    e.printStackTrace();
                }

            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (checked) {
                Toast.makeText(getActivity(), "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(), "Такой пользователь уже существует", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
