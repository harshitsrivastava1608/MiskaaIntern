package com.example.miskaatest.Offline;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert(SaveData saveData);

    @Delete
    void delete(SaveData saveData);

    @Delete
    void Reset(List<SaveData> saveData);

    @Query("SELECT * FROM table_name")
    List<SaveData> getAll();
}
