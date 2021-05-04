package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()

        main_addNewRecipe.setOnClickListener{
            val intent = Intent(this, Activity_AddRecipe::class.java)
            intent.putExtra("header", "Add New Recipe")
            startActivity(intent)
        }

        mainBurgerImage.setOnClickListener{
            val recipeType  = "Burger"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainFishImage.setOnClickListener{
            val recipeType  = "Fish"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainBreakfastImage.setOnClickListener{
            val recipeType  = "Breakfast"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainJuiceImage.setOnClickListener{
            val recipeType  = "Juice"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainPizzaImage.setOnClickListener{
            val recipeType  = "Pizza"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainCakeImage.setOnClickListener{
            val recipeType  = "Cake"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainMeatImage.setOnClickListener{
            val recipeType  = "Meat"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainBreadImage.setOnClickListener{
            val recipeType  = "Bread"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainNoodleImage.setOnClickListener{
            val recipeType  = "Noodle"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainSeafoodImage.setOnClickListener{
            val recipeType  = "Seafood"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainWesternImage.setOnClickListener{
            val recipeType  = "Western"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainDessertImage.setOnClickListener{
            val recipeType  = "Dessert"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainBarbequeImage.setOnClickListener{
            val recipeType  = "Barbeque"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }

        mainCroissantImage.setOnClickListener{
            val recipeType  = "Croissant"
            val intent      = Intent(this, Activity_RecipeList::class.java)

            intent.putExtra("Type", recipeType)
            startActivity(intent)
        }
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.customize_menu_actionbar, menu)

        val manager     = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem  = menu?.findItem(R.id.editIcon)
        val searchView  = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                return false
            }
        })
        return true
    }
    */
}