package com.combyne.android.tvshowmanager

interface QueryUseCase<out T> {
    suspend fun query(cursor: String?): Triple<T, String?, Boolean>?
}

interface CreateUseCase<in T, out V> {
    suspend fun post(t: T): V
}

interface ValidateEntryUseCase<in T> {
    fun isValid(t: T): Boolean
}