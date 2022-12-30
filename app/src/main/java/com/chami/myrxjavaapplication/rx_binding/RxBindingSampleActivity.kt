package com.chami.myrxjavaapplication.rx_binding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chami.myrxjavaapplication.databinding.ActivityRxBindingBinding
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

class RxBindingSampleActivity : AppCompatActivity() {

    private var _binding: ActivityRxBindingBinding? = null
    private val binding: ActivityRxBindingBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRxBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val disposable: Disposable = binding.etName.textChanges().subscribe { char ->

            binding.tvName.text = char.toString()

        }

        val disposable2 : Disposable = binding.btnClear.clicks().subscribe {

                binding.etName.setText("")
                binding.tvName.text = ""
            
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}