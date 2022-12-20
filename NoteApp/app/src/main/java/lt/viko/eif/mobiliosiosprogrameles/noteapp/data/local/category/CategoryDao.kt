package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category

import androidx.room.*

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategoryIgnoreDupes(category: CategoryEntity): Long

    @Query("DELETE FROM category_table WHERE categoryId = :id")
    suspend fun deleteCategory(id: Int)

    @Query("SELECT * FROM category_table WHERE categoryId = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

}