package fastcampus.aop.part4.chapter04_github.data.db

import android.content.Context
import androidx.room.Room

object DataBaseProvider {

    private const val DB_NAME = "github_repository_app.db"

    fun provideDB(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        SimpleGithubDatabase::class.java, DB_NAME
    ).build()

}