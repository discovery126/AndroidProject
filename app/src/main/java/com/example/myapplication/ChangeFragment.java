package com.example.myapplication;

import static com.example.myapplication.MainActivity.AUTHORIZATION;
import static com.example.myapplication.MainActivity.LOGIN;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.User;

public class ChangeFragment extends Fragment {

    private View view;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private MainDb db;
    private User user;
    boolean boolChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_profile,container,false);

        db = Room.databaseBuilder(view.getContext(), MainDb.class, "RecipeBD").build();

        settings = getActivity().getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE);
        editor = settings.edit();
        String currentLogin = settings.getString(LOGIN,"");

        Thread thread = new Thread(() -> {
            try {
                user = db.getUserDao().getUser(currentLogin);
            }
            catch (NullPointerException e)  {
                e.printStackTrace();
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageView buttonCancel = view.findViewById(R.id.imageCancelProfile);

        EditText textLogin = view.findViewById(R.id.textChangeLogin);

        EditText oldPass = view.findViewById(R.id.textOldPassword);
        EditText newPass = view.findViewById(R.id.textNewPassword);

        textLogin.setText(user.getLogin());

        Button buttonChangeLogin = view.findViewById(R.id.buttonChangeLogin);
        Button buttonChangePass = view.findViewById(R.id.buttonChangePassword);

        buttonChangeLogin.setOnClickListener(view -> {
            boolChange = false;

            String curLogin = textLogin.getText().toString().replaceAll(" ", "");

            if (curLogin.length()==0) {
                Toast.makeText(getActivity(), "Ваш логин не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!curLogin.matches("[A-Za-z]+[A-Za-z\\d]+")) {
                Toast.makeText(getActivity(), "Ваш логин не соответствует шаблону[Буквы(латиница),цифры] или[Буквы(латиница)]", Toast.LENGTH_SHORT).show();
                return;
            }

            Thread thread1 = new Thread(() -> {
                user.setLogin(curLogin);
                try {
                    db.getUserDao().updateUser(user);
                    editor.putString(LOGIN,user.getLogin());
                    editor.apply();
                    boolChange = true;
                }
                catch (SQLiteConstraintException e) {
                    Toast.makeText(getActivity(), "Такой логин уже существует", Toast.LENGTH_SHORT).show();
                    return;
                }
            });


            thread1.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (boolChange)
                Toast.makeText(getActivity(), "Логин изменён", Toast.LENGTH_SHORT).show();


            //Navigation.findNavController(view).navigate(R.id.action_changes_login_or_password_to_profile)
        });
        buttonChangePass.setOnClickListener(view -> {
            boolChange = false;
            String stringOldPass = oldPass.getText().toString().replaceAll(" ", "");
            String stringNewPass = newPass.getText().toString().replaceAll(" ", "");


            if (stringOldPass.length()==0) {
                Toast.makeText(getActivity(), "Ваш старый пароль не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (stringNewPass.length()==0) {
                Toast.makeText(getActivity(), "Ваш новый пароль не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!stringOldPass.equals(user.getPassword())) {
                Toast.makeText(getActivity(), "Неправильный старый пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (stringNewPass.equals(stringOldPass)) {
                Toast.makeText(getActivity(), "Ваш новый пароль не должен равен старому", Toast.LENGTH_SHORT).show();
                return;
            }


            Thread thread1 = new Thread(() -> {
                user.setPassword(stringNewPass);
                db.getUserDao().updateUser(user);
                boolChange = true;
            });


            thread1.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (boolChange)
                Toast.makeText(getActivity(), "Пароль изменён", Toast.LENGTH_SHORT).show();
            
            //Navigation.findNavController(view).navigate(R.id.action_changes_login_or_password_to_profile)
        });

        buttonCancel.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_changes_login_or_password_to_profile));



        return view;
    }
}
