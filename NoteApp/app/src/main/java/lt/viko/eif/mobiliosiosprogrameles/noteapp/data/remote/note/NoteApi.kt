package lt.viko.eif.mobiliosiosprogrameles.noteapp.data.remote.note

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {

    @GET("notes")
    suspend fun getNotes(): Response<NotesDto>

    @POST("notes")
    suspend fun setNotes(@Body notes: NotesDto)

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/api/"
    }
}