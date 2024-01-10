package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class UpdateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        val prioritySpinner: Spinner = findViewById(R.id.priority)
        ArrayAdapter.createFromResource(
            this,
            R.array.priority,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }

        val progressSpinner: Spinner = findViewById(R.id.progress)
        ArrayAdapter.createFromResource(
            this,
            R.array.progress,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            progressSpinner.adapter = adapter
        }

        // Gets column id of selected task from view tasks page
        val COLUMN_ID = intent.getStringExtra("COLUMN_ID")

        val dbHandler = DatabaseUtility(this, null)
        val cursor = dbHandler.getTaskById(COLUMN_ID.toString())
        cursor?.moveToNext()
        val arrayPriority = getResources().getStringArray(R.array.priority)
        val arrayProgress = getResources().getStringArray(R.array.progress)

        val taskNameView = findViewById<EditText>(R.id.task_name)
        val taskDescriptionView = findViewById<EditText>(R.id.task_description)
        val taskPriorityView = findViewById<Spinner>(R.id.priority)
        val taskDateEditText = findViewById<EditText>(R.id.due_date)
        val taskProgressView = findViewById<Spinner>(R.id.progress)

        taskNameView.setText(cursor?.getString(1))
        taskDescriptionView.setText(cursor?.getString(3))
        taskPriorityView.setSelection(arrayPriority.indexOf(cursor?.getString(2)))
        taskDateEditText.setText(cursor?.getString(5))
        taskProgressView.setSelection(arrayProgress.indexOf(cursor?.getString(4)))

        val updateTaskButton = findViewById<Button>(R.id.create_button)

        updateTaskButton.setOnClickListener {
            val taskName = taskNameView.text.toString()
            val taskDescription = taskDescriptionView.text.toString()
            val taskDate = taskDateEditText.text.toString()
            val taskProgress = taskProgressView.selectedItem.toString()

            val priority = taskPriorityView.selectedItem.toString()
            val dbHandler = DatabaseUtility(this, null)
            val taskItem = Task(taskName,taskDescription,priority,taskDate,taskProgress)
            dbHandler.updateTask(taskItem)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val deleteTaskButton = findViewById<Button>(R.id.delete_button)

        deleteTaskButton.setOnClickListener {

            if (COLUMN_ID != null) {
                dbHandler.deleteTask(COLUMN_ID)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}