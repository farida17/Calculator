package com.farida.calculator

import androidx.lifecycle.LiveData

interface IMainViewModel {
    val displayedValue: LiveData<String>
    fun onDigitPressed(digit: CharSequence)
    fun onClearInput()
    fun onClearAll()
    fun onDelete()
    fun onDivide()
    fun onMultiply()
    fun onSubtract()
    fun onAdd()
    fun onResult()
    fun onChangeSign()
    fun onDecimalPoint()
}