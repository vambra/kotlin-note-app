package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case

import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Int) {
        return repository.deleteCategory(id)
    }
}