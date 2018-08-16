package com.manwinder.nasapictureoftheday.utils

// Extension

//fun <T> LiveData<T>.blockingObserve(): T? {
//    var value: T? = null
//    val latch = java.util.concurrent.CountDownLatch(1)
//    val innerObserver = Observer<T> {
//        value = it
//        latch.countDown()
//    }
//    observeForever(innerObserver)
//    latch.await(2, java.util.concurrent.TimeUnit.SECONDS)
//    return value
//}