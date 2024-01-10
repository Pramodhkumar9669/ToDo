package com.example.todo


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    val taskArray: ArrayList<Task> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val createTask = findViewById<Button>(R.id.create_task)
        val taskList = findViewById<ListView>(R.id.task_list)

        displayData()
        val taskAdapter = TaskAdapter(this,taskArray)
        taskList.adapter = taskAdapter


        taskList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemIdAtPosition(position)
            println("POS_" + selectedItem)
            val intent = Intent(this, UpdateTaskActivity::class.java)
            intent.putExtra("COLUMN_ID",(selectedItem+1).toString())
            startActivity(intent)
        }

        createTask.setOnClickListener {
            startActivity(Intent(this,CreateTaskActivity::class.java))
        }


    }

    /**
     * Displays each task in form of ListView
     *
     * **/
    private fun displayData(){
        val dbHandler = DatabaseUtility(this, null)
        val cursor = dbHandler.getAllTask()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    // on below line we are adding the data from
                    // cursor to our array list.
                    taskArray.add(
                        Task(
                            cursor.getString(1),
                            cursor.getString(3),
                            cursor.getString(2),
                            cursor.getString(5),
                            cursor.getString(4)
                        )
                    )
                } while (cursor.moveToNext())
                // moving our cursor to next.
            }
        }

        Log.i("TASK", taskArray.toString())
        cursor?.close()

    }
}