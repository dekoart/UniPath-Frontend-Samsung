package com.example.unipathapp.models

import java.io.Serializable

data class UniversityResponse(
    val id: Long,
    val name: String,
    val city: String,
    val address: String,
    val logo: String?,
    val type: String,
    val hasDormitory: Boolean?,
    val hasMilitary: Boolean?,
    val hasExchange: Boolean?,
    val programsCount: Int?,
    val minBudgetScore: Int?
) : Serializable