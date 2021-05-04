import android.graphics.Bitmap
import java.sql.Blob

//creating a Data Model Class
class Fuctions_RecipeModelClass(val id: Int, val title: String, val image: ByteArray?, val ingredient: String, val step: String, val category: String)