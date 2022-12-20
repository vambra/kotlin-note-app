package lt.viko.eif.mobiliosiosprogrameles.noteapp.domain.use_case.note_use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val uploadNotesToApiUseCase: UploadNotesToApiUseCase
)