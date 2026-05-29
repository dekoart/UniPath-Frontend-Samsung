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
    val minBudgetScore: Int?,
    val phone: String? = null,
    val email: String? = null,
    val website: String? = null,
    val budgetPlaces: Int? = null,
    val paidPlaces: Int? = null,
    val pricePerYear: Int? = null,
    val admissionPhone: String? = null,
    val admissionEmail: String? = null,
    val admissionWebsite: String? = null,
    val admissionHours: String? = null
) : Serializable