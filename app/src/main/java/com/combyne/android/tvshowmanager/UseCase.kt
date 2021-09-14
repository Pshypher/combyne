package com.combyne.android.tvshowmanager

interface QueryUseCase<out T> {
    suspend fun query(cursor: String?): Triple<T, String?, Boolean>?
}

interface CreateUseCase<in T> {
    suspend fun create(t: T)
}

interface ValidateEntryUseCase<in T> {
    fun isValid(t: T): Boolean
}