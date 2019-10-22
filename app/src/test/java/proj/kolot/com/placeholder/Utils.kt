package proj.kolot.com.placeholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.simpleObserve(): T? {
    val observer = Observer<T> {
    }
    observeForever(observer)
    return value
}