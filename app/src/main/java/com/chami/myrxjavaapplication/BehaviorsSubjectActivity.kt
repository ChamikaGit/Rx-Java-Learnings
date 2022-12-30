package com.chami.myrxjavaapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityBehaviousSubjectBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject

class BehaviorsSubjectActivity : AppCompatActivity() {
    private var _binding: ActivityBehaviousSubjectBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBehaviousSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Behaviors subject - emits the most recent value and all other subsequent values
         *
         */
       // behaviourSubjectDemo1()

        behaviourSubjectDemo2()


    }

    private fun behaviourSubjectDemo1() {

        val observable: Observable<String> = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val behaviorSubject: BehaviorSubject<String> = BehaviorSubject.create()
        //async subject act like pipe between observer and observables
        observable.subscribe(behaviorSubject)

        behaviorSubject.subscribe(getFirstObserver())
        behaviorSubject.subscribe(getSecondObserver())
        behaviorSubject.subscribe(getThirdObserver())

    }

    private fun behaviourSubjectDemo2() {

        /** Async subject - rxjava subjects can act like both observers and observables because the subject class extend from observable class and implemented
         * from observer interface
         *
         */

        val behaviorSubject: BehaviorSubject<String> = BehaviorSubject.create()

        behaviorSubject.subscribe(getFirstObserver())

        behaviorSubject.onNext("JAVA")
        behaviorSubject.onNext("KOTLIN")
        behaviorSubject.onNext("XML")

        behaviorSubject.subscribe(getSecondObserver())

        behaviorSubject.onNext("JSON")
        behaviorSubject.onComplete()
        behaviorSubject.subscribe(getThirdObserver())

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