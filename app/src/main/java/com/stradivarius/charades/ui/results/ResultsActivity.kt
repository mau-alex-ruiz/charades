package com.stradivarius.charades.ui.results

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.stradivarius.charades.R
import com.stradivarius.charades.databinding.ResultRowBinding
import com.stradivarius.charades.databinding.ResultsLayoutBinding
import com.stradivarius.charades.ui.main.MainFragment
import kotlin.math.ceil
import kotlin.math.floor

class ResultsActivity : FragmentActivity() {

    companion object {
        const val KEY_RESULTS_LIST = "resultsList"
        const val PREFIX_PASS = "PASS"
        const val PREFIX_FAIL = "FAIL"
    }

    private lateinit var boundLayout: ResultsLayoutBinding
    private lateinit var resultsList: MutableList<CharSequence>

    private val mediaPlayer = MediaPlayer()
    private lateinit var appearSoundfd: AssetFileDescriptor

    var resultCounter: CountDownTimer? = null

    override fun onBackPressed() {
        super.onBackPressed()
        resultCounter?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boundLayout = DataBindingUtil.setContentView(this, R.layout.results_layout)
        intent.extras!!.getBundle(MainFragment.KEY_BUNDLE)!!.let { bundle ->
            resultsList = bundle.getCharSequenceArray(KEY_RESULTS_LIST)!!.toMutableList()
        }
        appearSoundfd = boundLayout.root.context.resources.openRawResourceFd(R.raw.result)
        configureTimer()
        createGrid()
        val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                boundLayout.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                resultCounter?.start()
            }
        }
        boundLayout.root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    private fun createGrid() {
        val numRows = ceil(resultsList.size.toDouble() / 2).toInt()
        for (i in 0 until numRows) {
            ResultRowBinding.inflate(LayoutInflater.from(this)).also {
                it.root.tag = i
                it.resultText1.tag = i*2
                it.resultText2.tag = i*2 + 1
                boundLayout.resultsGrid.addView(it.root)
            }
        }
    }


    private fun configureTimer() {
        resultCounter = object : CountDownTimer((resultsList.size*500).toLong(), 500) {

            private var itemCount = 0
            private var correctCount = 0

            override fun onTick(millisUntilFinished: Long) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(
                    appearSoundfd.fileDescriptor,
                    appearSoundfd.startOffset,
                    appearSoundfd.length
                )
                mediaPlayer.prepare()
                val (passOrFail, item) = resultsList[itemCount].split(".")
                if (passOrFail == PREFIX_PASS) {
                    correctCount += 1
                    boundLayout.resultCounter.text = correctCount.toString()
                }
                val numRow = floor(itemCount.toDouble() / 2).toInt()
                boundLayout.resultsGrid.children.toList().find { it.tag == numRow }.apply {
                    with(this as LinearLayout) {
                        visibility = View.VISIBLE
                        children.elementAt(itemCount % 2).apply {
                            with(this as TextView) {
                                setTextColor(getColor(passOrFail))
                                text = item
                                setOnClickListener {
                                    AlertDialog.Builder(context)
                                        .setMessage(item)
                                        .create()
                                        .show()
                                }
                            }
                        }
                    }
                }
                itemCount += 1
            }

            override fun onFinish() {
                // no-op
            }
        }
    }

    private fun getColor(string: String): Int {
        return if (string == PREFIX_PASS) {
            getColor(R.color.passGreen)
        } else {
            getColor(R.color.failRed)
        }
    }
}