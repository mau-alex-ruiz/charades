package com.stradivarius.charades.ui.results

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.stradivarius.charades.R
import com.stradivarius.charades.databinding.ResultTextviewTemplateBinding
import com.stradivarius.charades.databinding.ResultsLayoutBinding
import com.stradivarius.charades.ui.main.MainFragment
import java.io.FileDescriptor

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

    var resultTimer: CountDownTimer? = null

    override fun onBackPressed() {
        super.onBackPressed()
        resultTimer?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boundLayout = DataBindingUtil.setContentView(this, R.layout.results_layout)
        intent.extras!!.getBundle(MainFragment.KEY_BUNDLE)!!.let { bundle ->
            resultsList = bundle.getCharSequenceArray(KEY_RESULTS_LIST)!!.toMutableList()
        }
        appearSoundfd = boundLayout.root.context.resources.openRawResourceFd(R.raw.result)
        configureTimer()
        val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                boundLayout.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                resultTimer?.start()
            }
        }
        boundLayout.root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }


    private fun configureTimer() {
        resultTimer = object : CountDownTimer((resultsList.size*500).toLong(), 500) {

            private var itemCount = 1
            private var correctCount = 0

            override fun onTick(millisUntilFinished: Long) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(
                    appearSoundfd.fileDescriptor,
                    appearSoundfd.startOffset,
                    appearSoundfd.length
                )
                mediaPlayer.prepare()
                ResultTextviewTemplateBinding.inflate(
                    LayoutInflater.from(this@ResultsActivity),
                    boundLayout.resultsGrid,
                    true
                ).apply {
                    resultTemplate.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> mediaPlayer.start() }
                    resultTemplate.layoutParams = GridLayout.LayoutParams().apply {
                        setGravity(Gravity.CENTER_HORIZONTAL or Gravity.FILL_HORIZONTAL)
                        columnSpec = GridLayout.spec(getColumn(itemCount))
                        rowSpec = GridLayout.spec(Math.ceil(itemCount.toDouble().div(2)).toInt())
                    }
                    resultTemplate.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                    val result = resultsList.removeAt(0).split(".")
                    if (result[0] == PREFIX_PASS) {
                        correctCount += 1
                        boundLayout.resultCounter.text = correctCount.toString()
                    }
                    resultTemplate.setTextColor(getColor(result[0]))
                    resultTemplate.text = result[1]
                    itemCount += 1
                }.root
            }

            override fun onFinish() {
                // no-op
            }
        }
    }

    private fun getColumn(count: Int): Int {
        return if (count % 2 != 0) 0 else 1
    }

    private fun shouldIncreaseTimer(string: String): Boolean  {
        return string == PREFIX_PASS
    }

    private fun getColor(string: String): Int {
        return if (string == PREFIX_PASS) {
            getColor(R.color.passGreen)
        } else {
            getColor(R.color.failRed)
        }
    }
}