package com.chami.myrxjavaapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityReplaySubjectBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.ReplaySubject

class ReplaySubjectActivity : AppCompatActivity() {
    private var _binding: ActivityReplaySubjectBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReplaySubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** replay subject - replay the data stream and it doesn't care when the subscriber subscribe.
         *
         * replay subject emit all the items of the observable without considering when the subscriber subscribed.
         *
         */
//         replaySubjectDemo1()

        replaySubjectDemo2()


    }

    private fun replaySubjectDemo1() {

        val observable: Observable<String> = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val replaySubject: ReplaySubject<String> = ReplaySubject.create()
        observable.subscribe(replaySubject)

        replaySubject.subscribe(getFirstObserver())
        replaySubject.subscribe(getSecondObserver())
        replaySubject.subscribe(getThirdObserver())

    }

    private fun replaySubjectDemo2() {

        /** Async subject - rxjava subjects can act like both observers and observables because the subject class extend from observable class and implemented
         * from observer interface
         *
         */

        val replaySubject: ReplaySubject<String> = ReplaySubject.create()

        replaySubject.subscribe(getFirstObserver())

        replaySubject.onNext("JAVA")
        replaySubject.onNext("KOTLIN")
        replaySubject.onNext("XML")

        replaySubject.subscribe(getSecondObserver())

        replaySubject.onNext("JSON")
        replaySubject.onComplete()
        replaySubject.subscribe(getThirdObserver())

    }


    private fun getFirstObserver(): Observer<String> {

        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(MainActivity.TAG2, "First Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(MainActivity.TAG2, "First Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(MainActivity.TAG2, "First Observer error")

            }

            override fun onComplete() {
                Log.d(MainActivity.TAG2, "First Observer completed")

            }

        }

        return observer

    }

    private fun getSecondObserver(): Observer<String> {

        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(MainActivity.TAG2, "Second Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(MainActivity.TAG2, "Second Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(MainActivity.TAG2, "Second Observer error")

            }

            override fun onComplete() {
                Log.d(MainActivity.TAG2, "Second Observer completed")

            }

        }

        return observer

    }

    private fun getThirdObserver(): Observer<String> {

        val observer: Observer<String> = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(MainActivity.TAG2, "Third Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(MainActivity.TAG2, "Third Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(MainActivity.TAG2, "Third Observer error")

            }

            override fun onComplete() {
                Log.d(MainActivity.TAG2, "Third Observer completed")

            }

        }

        return observer

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}