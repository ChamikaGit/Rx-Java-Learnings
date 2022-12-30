package com.chami.myrxjavaapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityPublishSubjectBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class PublishSubjectActivity : AppCompatActivity() {
    private var _binding: ActivityPublishSubjectBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPublishSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Publish subject - emits all the items and other subsequent items at the time of subscription
         *
         */
        // publishSubjectDemo1()

       publishSubjectDemo2()


    }

    private fun publishSubjectDemo1() {

        val observable: Observable<String> = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val publishSubject: PublishSubject<String> = PublishSubject.create()
        observable.subscribe(publishSubject)

        publishSubject.subscribe(getFirstObserver())
        publishSubject.subscribe(getSecondObserver())
        publishSubject.subscribe(getThirdObserver())

    }

    private fun publishSubjectDemo2() {

        /** Async subject - rxjava subjects can act like both observers and observables because the subject class extend from observable class and implemented
         * from observer interface
         *
         */

        val publishSubject: PublishSubject<String> = PublishSubject.create()

        publishSubject.subscribe(getFirstObserver())

        publishSubject.onNext("JAVA")
        publishSubject.onNext("KOTLIN")
        publishSubject.onNext("XML")

        publishSubject.subscribe(getSecondObserver())

        publishSubject.onNext("JSON")
        publishSubject.onComplete()
        publishSubject.subscribe(getThirdObserver())

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