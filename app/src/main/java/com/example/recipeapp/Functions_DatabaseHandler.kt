package com.example.recipeapp

import Fuctions_RecipeModelClass
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class Functions_DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        private const val DATABASE_VERSION  = 1
        private const val DATABASE_NAME     = "RecipeApp"
        private const val TABLE_CONTACTS    = "RecipeApp"

        private const val KEY_ID            = "id"
        private const val KEY_TITLE         = "title"
        private const val KEY_IMAGE         = "image"
        private const val KEY_INGREDIENTS   = "ingredients"
        private const val KEY_STEPS         = "steps"
        private const val KEY_CATEGORY      = "category"
    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        val CREATE_RECIPE_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " BLOB,"
                + KEY_INGREDIENTS + " TEXT,"
                + KEY_STEPS + " TEXT,"
                + KEY_CATEGORY + " TEXT)")
        db?.execSQL(CREATE_RECIPE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    /**
     * Function to insert data
     */
    fun addRecipe(rcp: Fuctions_RecipeModelClass): Long
    {
        val db              = this.writableDatabase
        val contentValues   = ContentValues()

        contentValues.put(KEY_TITLE, rcp.title)
        contentValues.put(KEY_IMAGE, rcp.image)
        contentValues.put(KEY_INGREDIENTS, rcp.ingredient)
        contentValues.put(KEY_STEPS, rcp.step)
        contentValues.put(KEY_CATEGORY, rcp.category)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    /**
     * Method to read the records from database in form of ArrayList
     */
    fun viewRecipe(intent: String?): ArrayList<Fuctions_RecipeModelClass>
    {
        val empList: ArrayList<Fuctions_RecipeModelClass> = ArrayList<Fuctions_RecipeModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_CATEGORY = '$intent'"
        val db          = this.readableDatabase

        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id          : Int
        var title       : String
        var image       : ByteArray
        var ingredients : String
        var steps       : String
        var category    : String

        if (cursor.moveToFirst())
        {
            do
            {
                id          = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title       = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                image       = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))
                ingredients = cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS))
                steps       = cursor.getString(cursor.getColumnIndex(KEY_STEPS))
                category    = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY))
                val emp     = Fuctions_RecipeModelClass(id = id, title = title, image = image, ingredient = ingredients, step = steps, category = category)

                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    /**
     * Method to read the records from database in form of ArrayList
     */
    fun viewRecipes(intent: String?): ArrayList<Fuctions_RecipeModelClass>
    {
        val empList: ArrayList<Fuctions_RecipeModelClass> = ArrayList<Fuctions_RecipeModelClass>()

        // Query to select all the records from the table.
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS WHERE $KEY_CATEGORY = '$intent'"
        val db          = this.readableDatabase

        // Cursor is used to read the record one by one. Add them to data model class.
        var cursor: Cursor? = null

        try
        {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id          : Int
        var title       : String
        var image       : ByteArray
        var ingredients : String
        var steps       : String
        var category    : String

        if (cursor.moveToFirst())
        {
            do
            {
                id          = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                title       = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                image       = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE))
                ingredients = cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS))
                steps       = cursor.getString(cursor.getColumnIndex(KEY_STEPS))
                category    = cursor.getString(cursor.getColumnIndex(KEY_CATEGORY))
                val emp     = Fuctions_RecipeModelClass(id = id, title = title, image = image, ingredient = ingredients, step = steps, category = category)

                empList.add(emp)

            } while (cursor.moveToNext())
        }
        return empList
    }

    /**
     * Function to update record
     */
    fun updateRecipe(rcp: Fuctions_RecipeModelClass): Int
    {
        val db              = this.writableDatabase
        val contentValues   = ContentValues()

        contentValues.put(KEY_TITLE, rcp.title)
        contentValues.put(KEY_IMAGE, rcp.image)
        contentValues.put(KEY_INGREDIENTS, rcp.ingredient)
        contentValues.put(KEY_STEPS, rcp.step)
        contentValues.put(KEY_CATEGORY, rcp.category)

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + rcp.id, null)

        // Closing database connection
        db.close()
        return success
    }


    /**
     * Function to delete record
     */
    fun deleteEmployee(rcp: Fuctions_RecipeModelClass): Int
    {
        val db              = this.writableDatabase
        val contentValues   = ContentValues()

        contentValues.put(KEY_ID, rcp.id)

        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + rcp.id, null)

        db.close()
        return success
    }
}