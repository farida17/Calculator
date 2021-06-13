package com.farida.calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.MathContext

class MainViewModel : ViewModel(), IMainViewModel {

    override val displayedValue = MutableLiveData<String>()
    private var currentDisplayed: String
        get() = displayedValue.value.orEmpty()
        set(value) {
            displayedValue.value = if (value.isEmpty()) {
                "0"
            } else {
                value.replace('.', ',')
            }
        }

    private var pendingOperation: (() -> Unit)? = null

    override fun onDigitPressed(digit: CharSequence) {
        if (currentDisplayed == "0") {
            currentDisplayed = digit.toString()
        } else {
            currentDisplayed += digit
        }
    }

    override fun onClearInput() {
        currentDisplayed = ""
    }

    override fun onClearAll() {
        onClearInput()
        pendingOperation = null
    }

    override fun onDelete() {
        currentDisplayed = with(currentDisplayed) { take(length - 1) }
    }

    override fun onDivide() {
        arOperation { first, second ->
            if (second == BigDecimal.ZERO) {
                currentDisplayed = "Division by zero"
                null
            } else {
                first.divide(second, MathContext.DECIMAL128)
            }
        }
    }

    private inline fun arOperation(
        crossinline action: (first: BigDecimal, second: BigDecimal) -> BigDecimal?
    ) {
        pendingOperation?.invoke()
        val first = currentDisplayed.toBigDecimal()
        onClearInput()
        pendingOperation = {
            val second = currentDisplayed.toBigDecimal()
            action(first, second)?.let {
                currentDisplayed = it.stripTrailingZeros().toPlainString()
            }
        }
    }

    private fun String.toBigDecimal() = BigDecimal(this.replace(',', '.'))

    override fun onMultiply() {
        arOperation(BigDecimal::multiply)
    }

    override fun onSubtract() {
        arOperation(BigDecimal::subtract)
    }

    override fun onAdd() {
        arOperation(BigDecimal::add)
    }

    override fun onResult() {
        pendingOperation?.invoke()
        pendingOperation = null
    }

    override fun onChangeSign() {
        if (currentDisplayed != "0") {
            currentDisplayed = if (currentDisplayed[0] == '-') {
                currentDisplayed.takeLast(currentDisplayed.length - 1)
            } else {
                "-$currentDisplayed"
            }
        }
    }

    override fun onDecimalPoint() {
        if (!currentDisplayed.contains(',')) {
            currentDisplayed += ','
        }
    }
}