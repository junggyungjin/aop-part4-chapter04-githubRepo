package fastcampus.aop.part4.chapter04_github.data.response

import fastcampus.aop.part4.chapter04_github.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)