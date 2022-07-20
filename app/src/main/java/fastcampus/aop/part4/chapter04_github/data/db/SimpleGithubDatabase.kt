package fastcampus.aop.part4.chapter04_github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import fastcampus.aop.part4.chapter04_github.data.dao.RepositoryDao
import fastcampus.aop.part4.chapter04_github.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao

}