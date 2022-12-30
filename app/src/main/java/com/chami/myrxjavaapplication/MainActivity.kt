package com.chami.myrxjavaapplication

import android.content.Intent
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

        myDisposableObserver1 = object : DisposableObserver<String>() {
            override fun onNext(t: String) {
                Log.d(Companion.TAG, "onNext() : invoked $t")
                binding.tvName.text = t
            }

            override fun onError(e: Throwable) {
                Log.d(Companion.TAG, "onError() : invoked")
            }

            override fun onComplete() {
                Log.d(Companion.TAG, "onComplete() : invoked")
            }

        }

        compositeDisposable.add(myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myDisposableObserver1)
        )

        myDisposableObserver2 = object : DisposableObserver<String>() {
            override fun onNext(t: String) {
                Log.d(Companion.TAG, "onNext() : invoked $t")
                Toast.makeText(applicationContext, t, Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Log.d(Companion.TAG, "onError() : invoked")
            }

            override fun onComplete() {
                Log.d(Companion.TAG, "onComplete() : invoked")
            }

        }

        compositeDisposable.add(myObservable.subscribeWith(myDisposableObserver2))

        onclickListener()
    }

    private fun onclickListener() {
        binding.btnSubjectsAsync.setOnClickListener {
            startActivity(Intent(this@MainActivity,RxJavaSubjectsActivity::class.java))
        }

        binding.btnSubjectsBehavious.setOnClickListener {
            startActivity(Intent(this@MainActivity,BehaviorsSubjectActivity::class.java))
        }

        binding.btnSubjectsPublish.setOnClickListener {
            startActivity(Intent(this@MainActivity,PublishSubjectActivity::class.java))
        }

        binding.btnSubjectReplay.setOnClickListener {
            startActivity(Intent(this@MainActivity,ReplaySubjectActivity::class.java))

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        compositeDisposable.clear()

    }

    companion object {
        const val TAG = "Main"
        const val TAG2 = "Subject"

    }
}