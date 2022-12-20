package lt.viko.eif.mobiliosiosprogrameles.noteapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.local.NoteDatabase
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note.NoteApi
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.repository.CategoryRepositoryImpl
import lt.viko.eif.mobiliosiosprogrameles.noteapp.data.repository.NoteRepositoryImpl
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.CategoryRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.repository.NoteRepository
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.category_use_case.*
import lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteApi(): NoteApi {
        return Retrofit.Builder()
            .baseUrl(NoteApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDatabase,
        api: NoteApi
    ): NoteRepository {
        return NoteRepositoryImpl(db.noteDao, db.categoryDao, api)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            uploadNotesToApiUseCase = UploadNotesToApiUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        db: NoteDatabase
    ): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategoriesUseCase = GetCategoriesUseCase(repository),
            deleteCategoryByIdUseCase = DeleteCategoryByIdUseCase(repository),
            insertCategoryUseCase = InsertCategoryUseCase(repository),
            getCategoryByIdUseCase = GetCategoryByIdUseCase(repository)
        )
    }
}