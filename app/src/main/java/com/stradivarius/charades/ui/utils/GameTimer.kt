package com.stradivarius.charades.ui.utils

import android.os.CountDownTimer

class GameTimer(
    timerLength: Long,
    private val interval: Long,
    private val onTickBlock: ((Long) -> Unit)? = null,
    private val onFinishBlock: (() -> Unit)? = null
) : CountDownTimer(timerLength, interval) {

    private var timeRemaining: Long = timerLength
    private var isFirst: Boolean = true

    override fun onTick(millisUntilFinished: Long) {
        if (!isFirst) {
            timeRemaining -= interval
        }
        isFirst = false
        onTickBlock?.invoke(timeRemaining)
        if (timeRemaining <= 0) {
            onFinish()
            cancel()
        }
    }

    override fun onFinish() {
        onFinishBlock?.invoke()
    }

    fun pause() {
        cancel()
    }

    fun resume() {
        start()
    }

}