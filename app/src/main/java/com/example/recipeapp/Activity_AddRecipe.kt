package com.example.recipeapp

import Fuctions_RecipeModelClass
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_add_recipe.recipe_Ingredients
import kotlinx.android.synthetic.main.activity_details_page.*
import java.io.ByteArrayOutputStream

class Activity_AddRecipe : AppCompatActivity(), OnItemSelectedListener
{
    private var globalId        : Int?      = null
    private var globalCategory  : String?   = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val myIntent    = intent
        val header      = myIntent.getStringExtra("header")
        val id          = myIntent.getIntExtra("id", 0)
        val title       = intent.extras?.getString("title")
        val image       = myIntent.getByteArrayExtra("image")
        val ingredient  = intent.extras?.getString("ingredient")
        val steps       = intent.extras?.getString("steps")
        val category    = myIntent.getStringExtra("category")
        this.title      = header
        globalId        = id
        globalCategory  = category

        addRecipe_Title.setText(title)

        if(image != null)
        {
            addRecipe_Image.setImageBitmap(toBitmap(image))
        }

        recipe_Ingredients.setText(ingredient)
        addRecipe_Steps.setText(steps)

        if (category != null)
        {
            if(category.isNotEmpty())
            {
                recipe_category.visibility = View.GONE
            }
        }

        //Button
        button_save.setOnClickListener{
            hasImage(addRecipe_Image, globalId!!)
        }

        //Image
        addRecipe_Image_button.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    //Permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                    //Show pop-up to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else
                {
                    //Permission already granted
                    pickImageFromGallery()
                }
            }
            else
            {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        //Spinner
        val NEW_SPINNER_ID  = 1
        var recipeArray     = ArrayAdapter.createFromResource(this, R.array.recipe, android.R.layout.simple_spinner_item)
        recipeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(recipe_category)
        {
            adapter = recipeArray
            setSelection(0, false)
            onItemSelectedListener  = this@Activity_AddRecipe
            prompt                  = "Select your Recipe"
            gravity                 = Gravity.CENTER
        }

        val spinner = Spinner(this)
        spinner.id  = NEW_SPINNER_ID
    }

    //Spinner
    override fun onNothingSelected(parent: AdapterView<*>?)
    {
    }

    //Spinner
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        /*
        when (view?.id)
        {
            1       -> Toast.makeText(this, "Spinner 2 Position:${position}", Toast.LENGTH_SHORT).show()
            else    -> Toast.makeText(this, "Spinner 1 Position:${position}", Toast.LENGTH_SHORT).show()
        }
        */
    }

    //New Activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            android.R.id.home ->
            {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //New Activity
    override fun finish()
    {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    //Image
    private fun pickImageFromGallery()
    {
        val intent  = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object
    {
        //image pick code
        private const val IMAGE_PICK_CODE = 100
        //Permission Code
        private const val PERMISSION_CODE = 101;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when(requestCode)
        {
            PERMISSION_CODE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Permission from popup granted
                    pickImageFromGallery()
                }
                else
                {
                    //Permission from popup denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            addRecipe_Image.setImageURI(data?.data)
        }
    }

    //method for saving records in database
    private fun addRecord()
    {
        val bitmap = (addRecipe_Image.drawable as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        var title                                               = addRecipe_Title.text.toString()
        var image                                               = stream.toByteArray()
        var ingredient                                          = recipe_Ingredients.text.toString()
        var step                                                = addRecipe_Steps.text.toString()
        var category                                            = recipe_category.selectedItem.toString()
        var functionsDatabaseHandler: Functions_DatabaseHandler = Functions_DatabaseHandler(this)

        val status = functionsDatabaseHandler.addRecipe(Fuctions_RecipeModelClass(0, title, image, ingredient, step, category))

        if (status > -1)
        {
            Toast.makeText(applicationContext, "Recipe has saved Successfully", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(applicationContext, "Recipe Failed to saved", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Method is used to show the custom update dialog.
     */
    private fun updateRecordDialog()
    {
        val getID: Int
        val getCategory: String
        val bitmap = (addRecipe_Image.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        getID                                                   = globalId as Int
        getCategory                                             = globalCategory as String
        var title                                               = addRecipe_Title.text.toString()
        var image                                               = stream.toByteArray()
        var ingredient                                          = recipe_Ingredients.text.toString()
        var step                                                = addRecipe_Steps.text.toString()
        var functionsDatabaseHandler: Functions_DatabaseHandler = Functions_DatabaseHandler(this)

        val status = functionsDatabaseHandler.updateRecipe(Fuctions_RecipeModelClass(getID, title, image, ingredient, step, getCategory))

        if (status > -1)
        {
            Toast.makeText(applicationContext, "Recipe has updated Successfully", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(applicationContext, "Recipe Failed to updated", Toast.LENGTH_LONG).show()
        }
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

    private fun hasImage(view: ImageView, id: Int): Boolean
    {
        val drawable = view.drawable
        var hasImage = drawable != null

        if (hasImage && drawable is BitmapDrawable)
        {
            hasImage        = drawable.bitmap != null
            val etMessage   = findViewById(R.id.addRecipe_Title) as EditText
            val msg: String = etMessage.text.toString()

            if(msg.trim().length > 0 )
            {
                if(id == 0)
                {
                    addRecord()
                    super.onBackPressed()
                }
                else
                {
                    updateRecordDialog()
                    val intent      = Intent(this, Activity_RecipeList::class.java)
                    intent.flags    = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra("Type", globalCategory)
                    startActivity(intent)
                }
            }
            else
            {
                Toast.makeText(applicationContext, "Please Insert Title", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(applicationContext, "Please Upload image", Toast.LENGTH_LONG).show()
        }
        return hasImage
    }
}