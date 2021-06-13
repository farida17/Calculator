package com.farida.calculator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.farida.calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val viewModel: IMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.zero.setOnClickListener { onButtonClick(binding.zero) }
        binding.one.setOnClickListener { onButtonClick(binding.one) }
        binding.two.setOnClickListener { onButtonClick(binding.two) }
        binding.three.setOnClickListener { onButtonClick(binding.three) }
        binding.four.setOnClickListener { onButtonClick(binding.four) }
        binding.five.setOnClickListener { onButtonClick(binding.five) }
        binding.six.setOnClickListener { onButtonClick(binding.six) }
        binding.seven.setOnClickListener { onButtonClick(binding.seven) }
        binding.eight.setOnClickListener { onButtonClick(binding.eight) }
        binding.nine.setOnClickListener { onButtonClick(binding.nine) }
        binding.plus.setOnClickListener { onButtonClick(binding.plus) }
        binding.minus.setOnClickListener { onButtonClick(binding.minus) }
        binding.changeSign.setOnClickListener { onButtonClick(binding.changeSign) }
        binding.multiply.setOnClickListener { onButtonClick(binding.multiply) }
        binding.divide.setOnClickListener { onButtonClick(binding.divide) }
        binding.result.setOnClickListener { onButtonClick(binding.result) }
        binding.decimal.setOnClickListener { onButtonClick(binding.decimal) }
        binding.clearInput.setOnClickListener { onButtonClick(binding.clearInput) }
        binding.clearAll.setOnClickListener { onButtonClick(binding.clearAll) }
        binding.delete.setOnClickListener { onButtonClick(binding.delete) }
        viewModel.displayedValue.observe(this) {
            binding.display.text = it
        }
    }

    private fun onButtonClick(v: MaterialButton) {
        v.text.takeIf(CharSequence::isDigitsOnly)?.let {
            viewModel.onDigitPressed(it)
        } ?: onNonDigitPressed(v)
    }

    private fun onNonDigitPressed(v: MaterialButton) {
        with(viewModel) {
            when (v.id) {
                R.id.clear_input -> onClearInput()
                R.id.clear_all -> onClearAll()
                R.id.delete -> onDelete()
                R.id.divide -> onDivide()
                R.id.multiply -> onMultiply()
                R.id.minus -> onSubtract()
                R.id.plus -> onAdd()
                R.id.result -> onResult()
                R.id.change_sign -> onChangeSign()
                R.id.decimal -> onDecimalPoint()
            }
        }
    }
}