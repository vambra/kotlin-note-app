package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case

data class CategoryUseCases(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    val deleteCategoryByIdUseCase: DeleteCategoryByIdUseCase,
    val insertCategoryUseCase: InsertCategoryUseCase
)