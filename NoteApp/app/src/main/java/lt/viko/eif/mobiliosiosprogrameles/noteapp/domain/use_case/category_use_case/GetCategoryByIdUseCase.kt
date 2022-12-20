package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.model.Category
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Int): Category? {
        return repository.getCategoryById(id)
    }
}