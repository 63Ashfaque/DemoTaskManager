package com.example.demotaskmanager.roomdb

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "UserTaskTable", indices = [Index(value = ["title"], unique = true)])
data class UserTask(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String,
    val description:String,
    val deadline: String,
    val completionStatus:String
)
