package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.category.CategoryDao
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {
    override fun getAllCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        var categories = dao.getAllCategories().map { it.toCategory() };
        emit(Resource.Success(data = categories))
    }

    override suspend fun insertCategory(category: Category): Int {
        val rowId = dao.insertCategory(category.toCategoryEntity())
        return rowId.toInt()
    }

    override suspend fun insertCategoryIgnoreDupes(category: Category): Int {
        val rowId = dao.insertCategoryIgnoreDupes(category.toCategoryEntity())
        return rowId.toInt()
    }

    override suspend fun deleteCategory(id: Int) {
        dao.deleteCategory(id)
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return dao.getCategoryById(id)?.toCategory()
    }
}