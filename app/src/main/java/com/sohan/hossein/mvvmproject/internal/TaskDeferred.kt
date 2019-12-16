package com.sohan.hossein.mvvmproject.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import java.lang.Exception

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener {result ->
        deferred.complete(result)

    }

    this.addOnFailureListener {exception ->
        deferred.completeExceptionally(exception)

    }

    return deferred
}