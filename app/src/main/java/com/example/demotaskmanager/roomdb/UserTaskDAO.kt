package com.example.demotaskmanager.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserTaskDAO {

    //
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(userTask:UserTask):Long

    @Update
    suspend fun updateData(userTask:UserTask):Int

    @Delete
    suspend fun deleteData(userTask:UserTask):Int

    @Query("SELECT * FROM UserTaskTable")
    fun getContact(): LiveData<List<UserTask>>

    @Query("SELECT * FROM UserTaskTable ORDER BY id DESC")
    fun getContactDesc():LiveData<List<UserTask>>

}