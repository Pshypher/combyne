package com.combyne.android.tvshowmanager.network

data class Resource<T>(val status: Status, val data: T?, val code: Int?) {

    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {


        /**
         * Helper method to create a new state resource
         */
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        /**
         * Helper method to create an error state Resource.
         */
        fun <T> error(item: T? = null): Resource<T> {
            return Resource(Status.ERROR, item, null)
        }

        /**
         * Helper method to create a loading state Resource.
         */
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}