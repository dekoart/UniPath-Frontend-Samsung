package com.example.unipathapp.models

import java.io.Serializable

data class ProgramFilterRequest(
    val city: String?,
    val category: String?,
    val form: String?,
    val type: String?,
    val hasBudget: Boolean?,
    val hasDormitory: Boolean?,
    val hasMilitary: Boolean?,
    val hasExchange: Boolean?
)

data class ProgramResponse(
    val id: Long,
    val name: String,
    val universityName: String,
    val city: String,
    val address: String,
    val category: String,
    val form: String,
    val type: String,
    val budget: Int?,
    val paid: Int?,
    val price: Int?,
    val hasDormitory: Boolean?,
    val hasMilitary: Boolean?,
    val hasExchange: Boolean?,
    val logoUrl: String?,
    val budgetScore: Int?,
    val paidScore: Int?
) : Serializable