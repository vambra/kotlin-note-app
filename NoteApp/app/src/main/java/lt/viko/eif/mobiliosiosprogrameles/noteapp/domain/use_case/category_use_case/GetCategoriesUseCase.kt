package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return repository.getAllCategories()
    }
}