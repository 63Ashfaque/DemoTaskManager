package com.example.demotaskmanager.roomdb

import android.content.Context
import androidx.room.*


@Database(entities = [UserTask::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserTaskDataBase : RoomDatabase() {

     abstract fun userTaskDao(): UserTaskDAO

    companion object {

        @Volatile
        private var INSTANCE: UserTaskDataBase? = null
        fun getDataBase(context: Context): UserTaskDataBase {
            if (INSTANCE == null) {
                synchronized(this)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserTaskDataBase::class.java,
                        "UserTaskDataBase"
                    ).build()
                }

            }
            return INSTANCE!!
        }
    }
}