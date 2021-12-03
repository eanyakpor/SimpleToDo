package com.example.simpletodo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this is how the files are connected together setContentView()function
        setContentView(R.layout.activity_main)
        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }
        // 1. Let's detect when the user clicks on the add button
        //findViewById<Button>(R.id.button).setOnClickListener {
        // code executed whenever user clicks on the button
            //Log.i("Emi", "User Clicked on button")
        //}

        loadItems()
        // look up recycler view on layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // create adapter passing the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // attach the adapter to the recyclerView to populate items
        recyclerView.adapter = adapter
        //set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Our first tasks is to enter a button and input field so that the user can enter a task
        // and add it to the list
        // get a refrence on the button
        // and than set on click listener
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            //2. Add the string to our list of tasks, listOfTasks
            listOfTasks.add(userInputtedTask)
            // must notify the adapter to what we added to our list
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. reset text field
            inputTextField.setText("")

            saveItems()
        }
    }
    // saving this data by reading and writing from a specific file
    //save the data that user has inputted

    // get the file we need
    fun getDataFile() : File {

        // every line is going to represent a specific tasks in our list of tasks
        return File(filesDir, "data.txt")
    }
    // load the data by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
       catch(ioException: IOException) {
           ioException.printStackTrace()
       }
    }
    // need method to save all our items
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch(ioException: IOException) {

            ioException.printStackTrace()
        }

    }

}