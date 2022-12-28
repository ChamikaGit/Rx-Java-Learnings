package com.chami.myrxjavaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chami.myrxjavaapplication.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding  get() =  _binding!!

    private val TAG = "Main"
    private val name : String = "Hello to RxJava"
    lateinit var myObservable : Observable<String>
    lateinit var myObserver: Observer<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myObservable = Observable.just(name)
        //IO Scheduler()
        myObservable.subscribeOn(Schedulers.io())
        //Android Main thread or UI thread
        myObservable.observeOn(AndroidSchedulers.mainThread())

        myObserver = object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe : invoked")
            }

            override fun onNext(t: String) {
                Log.d(TAG, "onNext() : invoked $t")
                binding.tvName.text = t
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError() : invoked")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete() : invoked")
            }

        }

        myObservable.subscribe(myObserver)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}