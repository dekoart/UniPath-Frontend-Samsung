package com.example.unipathapp.models

data class ScoresResponse(
    val userId: Long,
    val scores: List<SavedScore>
)

data class SavedScore(
    val subjectId: Long,
    val subjectName: String,
    val score: Int?,
)