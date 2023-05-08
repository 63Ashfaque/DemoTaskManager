package com.example.demotaskmanager

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demotaskmanager.databinding.ActivityInsertTaskBinding
import com.example.demotaskmanager.roomdb.UserTask
import com.example.demotaskmanager.roomdb.UserTaskDataBase
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class InsertTaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityInsertTaskBinding
    private lateinit var dataBase: UserTaskDataBase
    private lateinit var item: UserTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_insert_task)

        //initialize dataBase var
        dataBase = UserTaskDataBase.getDataBase(this)

        mBinding.btnSave.setOnClickListener {


            insertUserTask(
                mBinding.edTitle.text.toString(),
                mBinding.edDescription.text.toString(),
                mBinding.edDeadline.text.toString(),
                mBinding.edCompletionStatus.text.toString()
            )
        }


        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()

        mBinding.edDeadline.setOnClickListener {
            materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
        }
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        // Handle the selected date
        materialDatePicker.addOnPositiveButtonClickListener { selectedDate ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate as Long
            val formattedDate = simpleDateFormat.format(calendar.time)
            mBinding.edDeadline.setText(formattedDate)
        }


        val type = arrayOf("Completed", "Doing", "Pending", "QA", "To be Discussed")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            type
        )
        mBinding.edCompletionStatus.setAdapter(adapter)
    }


    private fun insertUserTask(
        title: String,
        description: String,
        deadline: String,
        completionStatus: String
    ) {
        //BackGround Thread running
        GlobalScope.launch {
            val insert = dataBase.userTaskDao()
                .insertData(UserTask(0, title, description, deadline, completionStatus))

            if (insert > 0) {
                //when we run background that time we use withContext(dispatcher)
                withContext(Dispatchers.Main) {
                    Utils().showToast(applicationContext, "Insert One Record Successful")
                }
            } else {
                withContext(Dispatchers.Main) {
                    Utils().showToast(applicationContext, "Already present")
                }
            }
        }
    }

}