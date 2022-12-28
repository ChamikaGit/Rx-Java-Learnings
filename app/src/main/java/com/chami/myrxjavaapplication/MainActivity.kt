package com.chami.myrxjavaapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val TAG = "Main"
    private val name: String = "Hello to RxJava"
    lateinit var myObservable: Observable<String>

    //    lateinit var myObserver: Observer<String>
    lateinit var myDisposableObserver: DisposableObserver<String>

//    lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myObservable = Observable.just(name)
        //IO Scheduler()
        myObservable.subscribeOn(Schedulers.io())
        //Android Main thread or UI thread
        myObservable.observeOn(AndroidSchedulers.mainThread())

//        myObserver = object : Observer<String> {
//            override fun onSubscribe(d: Disposable) {
//                Log.d(TAG, "onSubscribe : invoked")
//                //init the disposable
//                disposable = d
//            }
//
//            override fun onNext(t: String) {
//                Log.d(TAG, "onNext() : invoked $t")
//                binding.tvName.text = t
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "onError() : invoked")
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "onComplete() : invoked")
//            }
//
//        }

        myDisposableObserver = object : DisposableObserver<String>() {
            override fun onNext(t: String) {
                Log.d(TAG, "onNext() : invoked $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError() : invoked")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete() : invoked")
            }

        }

        myObservable.subscribe(myDisposableObserver)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        //subscription will dispose and app will not crash or freeze
//        disposable.dispose()

        myDisposableObserver.dispose()
    }
}