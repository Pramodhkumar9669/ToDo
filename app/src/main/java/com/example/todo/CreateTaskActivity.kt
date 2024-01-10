package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.widget.*
import java.util.logging.Level.INFO

class CreateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val taskNameView = findViewById<EditText>(R.id.task_name)
        val taskDescriptionView = findViewById<EditText>(R.id.task_description)
        val taskPriorityView = findViewById<Spinner>(R.id.priority)
        val taskDateEditText = findViewById<EditText>(R.id.due_date)
        val taskProgressView = findViewById<Spinner>(R.id.progress)

        val createTaskButton = findViewById<Button>(R.id.create_button)

        createTaskButton.setOnClickListener {
            val taskName = taskNameView.text.toString()
            val taskDescription = taskDescriptionView.text.toString()
            val taskDate = taskDateEditText.text.toString()
            val taskProgress = taskProgressView.selectedItem.toString()

            val priority = taskPriorityView.selectedItem.toString()
            val dbHandler = DatabaseUtility(this, null)
            val taskItem = Task(taskName,taskDescription,priority,taskDate,taskProgress)
            dbHandler.addTask(taskItem)

            // Navigates back to List of tasks Page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        // To Load priority dropdown with values
        val prioritySpinner: Spinner = findViewById(R.id.priority)
        ArrayAdapter.createFromResource(
            this,
            R.array.priority,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }

        // To Load ststus dropdown with values
        val progressSpinner: Spinner = findViewById(R.id.progress)
        ArrayAdapter.createFromResource(
            this,
            R.array.progress,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            progressSpinner.adapter = adapter
        }
    }
}