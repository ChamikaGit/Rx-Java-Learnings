package com.chami.myrxjavaapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val TAG = "Main"
    private val name: String = "Hello to RxJava"
    lateinit var myObservable: Observable<String>

    private lateinit var myDisposableObserver1: DisposableObserver<String>
    private lateinit var myDisposableObserver2: DisposableObserver<String>

    //composite disposable instance take all the observers clear at the one time it's much easier than other work
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myObservable = Observable.just(name)
        //IO Scheduler()
        myObservable.subscribeOn(Schedulers.io())
        //Android Main thread or UI thread
        myObservable.observeOn(AndroidSchedulers.mainThread())

        myDisposableObserver1 = object : DisposableObserver<String>() {
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

        myObservable.subscribe(myDisposableObserver1)
        compositeDisposable.add(myDisposableObserver1)

        myDisposableObserver2 = object : DisposableObserver<String>() {
            override fun onNext(t: String) {
                Log.d(TAG, "onNext() : invoked $t")
                Toast.makeText(applicationContext, t, Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError() : invoked")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete() : invoked")
            }

        }

        myObservable.subscribe(myDisposableObserver2)
        compositeDisposable.add(myDisposableObserver2)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        compositeDisposable.clear()

    }
}