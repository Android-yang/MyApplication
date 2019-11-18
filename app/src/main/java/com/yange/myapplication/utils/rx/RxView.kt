package com.yange.myapplication.utils.rx

import android.view.View
import io.reactivex.Observable

class RxView {
    companion object {
        fun clicks(view: View?): Observable<View> =
            Observable
                .create { emitter ->
                    view?.setOnClickListener { emitter.onNext(view) }
                    emitter.setCancellable { view?.setOnClickListener(null) }
                }
    }
}