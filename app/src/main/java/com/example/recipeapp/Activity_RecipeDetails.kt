package com.example.recipeapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_details_page.*
import kotlinx.android.synthetic.main.activity_details_page.recipe_Ingredients
import java.io.ByteArrayOutputStream

class Activity_RecipeDetails : AppCompatActivity()
{
    private var globalId : Int ? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_page)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val myIntent    = intent
        val id          = myIntent.getIntExtra("id", 0)
        val title       = myIntent.getStringExtra("title")
        //val image     = myIntent.getByteArrayExtra("image")
        val ingredient  = myIntent.getStringExtra("ingredient")
        val steps       = myIntent.getStringExtra("steps")
        val category    = myIntent.getStringExtra("category")
        globalId        = id

        //recipeinfoImage.setImageBitmap(toBitmap(image))
        recipe_Title.setText(title)
        recipe_Ingredients.setText(ingredient)
        recipe_Steps.setText(steps)

        recipeDetail_Category.text = category
        this.title                 = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.customize_menu_actionbar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val id = item.itemId

        if(id == R.id.editIcon)
        {
            //val bitmap = (recipeinfoImage.getDrawable() as BitmapDrawable).getBitmap()
            //val stream = ByteArrayOutputStream()
            //bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

            val intent:Intent = Intent(this, Activity_AddRecipe::class.java)
            intent.putExtra("header", "Modify Recipe")
            intent.putExtra("id", globalId)
            intent.putExtra("title", recipe_Title.text.toString())
            //intent.putExtra("image", stream.toByteArray())
            intent.putExtra("ingredient", recipe_Ingredients.text.toString())
            intent.putExtra("steps", recipe_Steps.text.toString())
            intent.putExtra("category", recipeDetail_Category.text)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
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