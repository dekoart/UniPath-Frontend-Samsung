package com.example.unipathapp.models

data class ScoresRequest(
    val userId: Long,
    val scores: List<SubjectScore>
)

data class SubjectScore(
    val subjectId: Long,
    val score: Int?,
)