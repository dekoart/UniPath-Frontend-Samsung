package com.example.unipathapp.models

data class UniversityFilterRequest(
    val city: String? = null,
    val name: String? = null,
    val direction: String? = null,
    val type: String? = null,
    val hasDormitory: Boolean? = null,
    val hasMilitary: Boolean? = null,
    val hasExchange: Boolean? = null
)