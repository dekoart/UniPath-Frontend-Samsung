package com.example.unipathapp.models

data class UniversityFilterRequest(
    val city: String?,
    val type: String?,
    val hasDormitory: Boolean?,
    val hasMilitary: Boolean?,
    val hasExchange: Boolean?
)