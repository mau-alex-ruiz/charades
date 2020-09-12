package com.stradivarius.charades.ui.game.container

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.stradivarius.charades.R

class ItemContainerViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val mediaPlayer = MediaPlayer()
    private val correctSoundfd = resources.openRawResourceFd(R.raw.correct)
    private val incorrectSoundfd = resources.openRawResourceFd(R.raw.incorrect)

    fun handlePass() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(
                correctSoundfd.fileDescriptor,
        correctSoundfd.startOffset,
        correctSoundfd.length)
        mediaPlayer.prepare()
        mediaPlayer.start()
        visibility = View.GONE
    }

    fun handleFail() {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(
            incorrectSoundfd.fileDescriptor,
            incorrectSoundfd.startOffset,
            incorrectSoundfd.length)
        mediaPlayer.prepare()
        mediaPlayer.start()
        visibility = View.GONE
    }

    fun next() {
        setCurrentItem(currentItem + 1, false)
        visibility = View.VISIBLE
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

}