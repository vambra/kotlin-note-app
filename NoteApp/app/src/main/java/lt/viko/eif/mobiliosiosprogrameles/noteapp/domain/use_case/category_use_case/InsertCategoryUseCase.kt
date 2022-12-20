package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case

import kotlinx.coroutines.flow.onEach
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.InvalidCategoryException
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(category: Category): Int? {
        if (category.name?.isBlank() == true) {
            throw InvalidCategoryException("empty name")
        }

        repository.getAllCategories().onEach { resource ->
            resource.data?.onEach { cat ->
                if (cat.name == category.name) {
                    throw InvalidCategoryException("duplicate name")
                }
            }
        }
        return repository.insertCategory(category)
    }
}