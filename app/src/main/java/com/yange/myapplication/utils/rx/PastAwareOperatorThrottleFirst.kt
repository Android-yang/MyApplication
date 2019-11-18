package com.yange.myapplication.utils.rx

import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Operator that acts like rx.ThrottleFirst but also covers scenario when user changes time to the past
 */
class PastAwareOperatorThrottleFirst<T>(windowDuration: Long, unit: TimeUnit) : ObservableOperator<T, T> {

    internal val timeInMilliseconds: Long = unit.toMillis(windowDuration)
    internal val scheduler: Scheduler = Schedulers.computation()

    @Throws(Exception::class)
    override fun apply(@NonNull child: Observer<in T>): Observer<in T> {
        return object : Observer<T> {

            private var lastOnNext: Long = 0

            override fun onNext(v: T) {
                val now = scheduler.now(TimeUnit.MILLISECONDS)
                if (lastOnNext == 0L || now - lastOnNext >= timeInMilliseconds || lastOnNext >= now) {
                    lastOnNext = now
                    child.onNext(v)
                }
            }

            override fun onSubscribe(@NonNull d: Disposable) = child.onSubscribe(d)
            override fun onComplete() = child.onComplete()
            override fun onError(e: Throwable) = child.onError(e)
        }
    }
}
