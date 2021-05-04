package com.recyclerviewapp

import Fuctions_RecipeModelClass
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Activity_RecipeDetails
import com.example.recipeapp.Activity_RecipeList
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.recycle_view_setting.view.*


class Function_ItemAdapter(val context: Context, val items: ArrayList<Fuctions_RecipeModelClass>) : RecyclerView.Adapter<Function_ItemAdapter.ViewHolder>()
{
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycle_view_setting,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item                                = items.get(position)
        holder.recyclerTitle.text               = item.title
        holder.recyclerImage.setImageBitmap(toBitmap(item.image))

        // Updating the background color according to the odd/even positions in list.
        if (position % 2 == 0)
        {
            holder.recyclerMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        else
        {
            holder.recyclerMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.recyclerdelete.setOnClickListener{
            if (context is Activity_RecipeList)
            {
                context.deleteRecordAlertDialog(item)
            }
        }

        holder.recyclerImage.setOnClickListener{
            val intent = Intent(context, Activity_RecipeDetails::class.java)
            intent.putExtra("Type", item.category)
            intent.putExtra("id", item.id)
            intent.putExtra("title", item.title)
            //intent.putExtra("image", item.image)
            intent.putExtra("ingredient", item.ingredient)
            intent.putExtra("steps", item.step)
            intent.putExtra("category", item.category)
            context.startActivity(intent)
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int
    {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        // Holds the TextView that will add each item to
        val recyclerMain    = view.recycleView_Main
        val recyclerImage   = view.recycleView_image
        val recyclerTitle   = view.recycleView_title
        val recyclerdelete  = view.recycleView_delete
    }

    fun toBitmap(byteArray: ByteArray?): Bitmap?
    {
        return if (byteArray == null)
        {
            null
        }
        else
        {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}