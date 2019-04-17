package com.example.sales_partner.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Assembly;


import java.util.List;
@Dao
public interface AssemblyDao {

    @Query("SELECT * FROM assemblies")
    List<Assembly> getAll();

    @Query("SELECT * FROM assemblies WHERE description LIKE :desc ORDER BY description ASC")
    List<Assembly> findByDescription(String desc);

    @Insert
    void insertAll(Assembly assembly);

    @Update
    void update(Assembly... assembly);

    @Delete
    void delete(Assembly... assembly);
}
