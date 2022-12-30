package com.chami.myrxjavaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chami.myrxjavaapplication.MainActivity.Companion.TAG2
import com.chami.myrxjavaapplication.databinding.ActivityMainBinding
import com.chami.myrxjavaapplication.databinding.ActivityRxJavaSubjectsBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject

class RxJavaSubjectsActivity : AppCompatActivity() {

    private var _binding: ActivityRxJavaSubjectsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRxJavaSubjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Async subject - only emits the last value(item) of the observable
         *
         */
        asyncSubjectDemo1()




    }

    private fun asyncSubjectDemo1(){

        val observable :  Observable<String> = Observable.just("JAVA","KOTLIN","XML","JSON")
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        val asyncSubject : AsyncSubject<String> = AsyncSubject.create()
        //async subject act like pipe between observer and observables
        observable.subscribe(asyncSubject)

        asyncSubject.subscribe(getFirstObserver())
        asyncSubject.subscribe(getSecondObserver())
        asyncSubject.subscribe(getThirdObserver())

    }



    private fun getFirstObserver() : Observer<String>{

        val observer :Observer<String> = object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG2, "First Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(TAG2, "First Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG2, "First Observer error")

            }

            override fun onComplete() {
                Log.d(TAG2, "First Observer completed")

            }

        }

        return observer

    }

    private fun getSecondObserver() : Observer<String>{

        val observer :Observer<String> = object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG2, "Second Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(TAG2, "Second Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG2, "Second Observer error")

            }

            override fun onComplete() {
                Log.d(TAG2, "Second Observer completed")

            }

        }

        return observer

    }

    private fun getThirdObserver() : Observer<String>{

        val observer :Observer<String> = object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG2, "Third Observer Subscribe")
            }

            override fun onNext(t: String) {
                Log.d(TAG2, "Third Observer received $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG2, "Third Observer error")

            }

            override fun onComplete() {
                Log.d(TAG2, "Third Observer completed")

            }

        }

        return observer

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}