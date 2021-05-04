package com.example.recipeapp

import Fuctions_RecipeModelClass
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.recyclerviewapp.Function_ItemAdapter
import kotlinx.android.synthetic.main.activity_recipe_list.*

class Activity_RecipeList : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var intent          = intent
        val actionBar       = supportActionBar
        actionBar!!.title   = intent.getStringExtra("Type")
        actionBar.setDisplayHomeAsUpEnabled(true)

        //View Record
        setupListofDataIntoRecyclerView()

        // Set the LayoutManager that this RecyclerView will use.
        recipelist_recycleView_items.layoutManager = LinearLayoutManager(this)

        // Adapter class is initialized and list is passed in the param.
        val itemAdapter = Function_ItemAdapter(this, getItemsList())

        // adapter instance is set to the recyclerview to inflate the items.
        recipelist_recycleView_items.adapter = itemAdapter
    }

    //Display
    /**
     * Function is used to get the Items List which is added in the database table.
     */
    private fun getItemsList(): ArrayList<Fuctions_RecipeModelClass>
    {
        //creating the instance of DatabaseHandler class
        val functionsDatabaseHandler: Functions_DatabaseHandler = Functions_DatabaseHandler(this)

        //calling the viewEmployee method of DatabaseHandler class to read the records
        val empList: ArrayList<Fuctions_RecipeModelClass> = functionsDatabaseHandler.viewRecipe(intent.getStringExtra("Type"))
        return empList
    }

    /**
     * Function is used to show the list on UI of inserted data.
     */
    private fun setupListofDataIntoRecyclerView()
    {
        if (getItemsList().size > 0)
        {
            recipelist_recycleView_items.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility          = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            recipelist_recycleView_items.layoutManager = LinearLayoutManager(this)

            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = Function_ItemAdapter(this, getItemsList())

            // adapter instance is set to the recyclerview to inflate the items.
            recipelist_recycleView_items.adapter = itemAdapter
        }
        else
        {
            recipelist_recycleView_items.visibility = View.GONE
            tvNoRecordsAvailable.visibility         = View.VISIBLE
        }
    }

    /**
     * Method is used to show the delete alert dialog.
     */
    fun deleteRecordAlertDialog(rcp: Fuctions_RecipeModelClass)
    {
        val builder = AlertDialog.Builder(this)

        //set title for alert dialog
        builder.setTitle("Delete Record")

        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${rcp.title}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->

            //creating the instance of DatabaseHandler class
            val functionsDatabaseHandler: Functions_DatabaseHandler = Functions_DatabaseHandler(this)

            //calling the deleteEmployee method of DatabaseHandler class to delete record
            val status = functionsDatabaseHandler.deleteEmployee(Fuctions_RecipeModelClass(rcp.id, "", null, "", "", ""))

            if (status > -1)
            {
                Toast.makeText(applicationContext, "Record deleted successfully.", Toast.LENGTH_LONG).show()
                setupListofDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }

        //performing negative action
        builder.setNegativeButton("No")
        {
            dialogInterface, which -> dialogInterface.dismiss() // Dialog will be dismissed
        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}