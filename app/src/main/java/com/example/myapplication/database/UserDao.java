package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void addUser(User user);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateUser(User user);
    @Delete
    public void deleteUser(User user);
    @Query("select * from users")
    public List<User> getAllUser();
    @Query("select * from users where login == :login")
    public User getUser(String login);
//    @Query("update users set login=:login,password=:password where user_id =5")
//    public void updateUser(User user);
    @Query("select user_id from users where login == :login")
    public int getUserId(String login);

}
