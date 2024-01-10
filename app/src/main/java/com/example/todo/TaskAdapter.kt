package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskAdapter(private val context: Context, private val arrayList: java.util.ArrayList<Task>) : BaseAdapter() {
    private lateinit var nameTv: TextView
    private lateinit var descTv: TextView
    private lateinit var priorityTv: TextView
    private lateinit var statusTv: TextView
    private lateinit var dateTv: TextView
    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    /**
     * Adapter method to display list view
     *
     * **/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false)
        nameTv = convertView.findViewById(R.id.name)
        descTv = convertView.findViewById(R.id.description)
        priorityTv = convertView.findViewById(R.id.priority)
        dateTv = convertView.findViewById(R.id.date)
        statusTv = convertView.findViewById(R.id.status)
        nameTv.text = arrayList[position].name
        descTv.text = arrayList[position].description
        priorityTv.text = arrayList[position].priority
        dateTv.text = arrayList[position].date
        statusTv.text = arrayList[position].status
        return convertView
    }
}