package fastcampus.aop.part4.chapter04_github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import fastcampus.aop.part4.chapter04_github.data.db.DataBaseProvider
import fastcampus.aop.part4.chapter04_github.data.entity.GithubOwner
import fastcampus.aop.part4.chapter04_github.data.entity.GithubRepoEntity
import fastcampus.aop.part4.chapter04_github.databinding.ActivityMainBinding
import fastcampus.aop.part4.chapter04_github.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {

    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val repositoryDao by lazy { DataBaseProvider.provideDB(applicationContext).repositoryDao() }

    private lateinit var adapter: RepositoryRecyclerAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initAdapter()

//        launch {
//            addMockData()
//            val githubRepositories = loadGithubRepositories()
//            withContext(coroutineContext) {
//                Log.e("repositories", githubRepositories.toString())
//            }
//        }
    }

    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews()= with(binding) {
        searchButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        launch(coroutineContext) {
            loadLikedRepositoryList()
        }
    }

    private suspend fun loadLikedRepositoryList() = withContext(Dispatchers.IO) {
        val repoList = repositoryDao.getHistory()
        withContext(Dispatchers.Main) {
            setData(repoList)
        }
    }

    private fun setData(githubRepositoryList: List<GithubRepoEntity>) = with(binding) {
        if (githubRepositoryList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        }else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setRepositoryList(githubRepositoryList) {
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        }
    }

    private suspend fun addMockData() = withContext(Dispatchers.IO) {
        val mockData = (0 until 10).map {
            GithubRepoEntity(
                name = "repo $it",
                fullName = "name $it",
                owner = GithubOwner(
                    "login",
                    "avatarUrl"
                ),
                description = null,
                language = null,
                updatedAt = Date().toString(),
                stargazersCount = it
            )
        }
        repositoryDao.insertAll(mockData)
    }


}