package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category

interface CategoryRepository {
    fun getAllCategories(): Flow<Resource<List<Category>>>
    suspend fun insertCategory(category: Category): Int
    suspend fun insertCategoryIgnoreDupes(category: Category): Int
    suspend fun deleteCategory(id: Int)
    suspend fun getCategoryById(id: Int): Category?
}