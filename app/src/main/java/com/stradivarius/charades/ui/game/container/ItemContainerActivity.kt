package com.stradivarius.charades.ui.game.container

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Surface.ROTATION_270
import android.view.Surface.ROTATION_90
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.stradivarius.charades.R
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.databinding.ItemContainerLayoutBinding
import com.stradivarius.charades.ui.common.BaseFragmentActivity
import com.stradivarius.charades.ui.common.BaseViewModel
import com.stradivarius.charades.ui.common.BaseViewModelFragment
import com.stradivarius.charades.ui.main.MainFragment
import com.stradivarius.charades.ui.results.ResultsActivity
import com.stradivarius.charades.ui.utils.Constants
import com.stradivarius.charades.ui.utils.GameTimer
import com.stradivarius.charades.ui.utils.formatForDisplay
import com.stradivarius.charades.ui.utils.toRadians

class ItemContainerActivity : FragmentActivity(), SensorEventListener {

    companion object {
        const val KEY_BUNDLE = "bundle"
        const val KEY_ITEM_LIST = "itemList"
    }

    private lateinit var sensorManager: SensorManager
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val rotationMatrixAdjusted = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private var hasRoundStarted = false
    private var listenForTilt = false
    private var waitForUpright = false

    private lateinit var boundLayout: ItemContainerLayoutBinding
    private lateinit var itemArray: Array<CharSequence>

    private val updateTime: (Long) -> Unit = { timeRemaining ->
        boundLayout.timer.text = timeRemaining.formatForDisplay()
    }

    private val resultsList: MutableList<CharSequence> = mutableListOf()

    private val countDownTimer: GameTimer = GameTimer(
        timerLength = Constants.THREE_SECONDS,
        interval = Constants.INTERVAL_ONE_SECOND,
        onTickBlock = updateTime
    ) {
        boundLayout.apply {
            textOverlay.visibility = View.GONE
            itemViewpager.adapter = ItemPagerAdapter(
                supportFragmentManager,
                itemArray
            )
        }
        listenForTilt = true
        gameTimer.start()
    }

    private val gameTimer: GameTimer = GameTimer(6000, Constants.INTERVAL_ONE_SECOND, updateTime) {
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra(KEY_BUNDLE, Bundle().apply {
                putCharSequenceArray(ResultsActivity.KEY_RESULTS_LIST, resultsList.toTypedArray())
            })
        }
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        boundLayout = DataBindingUtil.setContentView(this, R.layout.item_container_layout)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        itemArray = intent.extras!!
            .getBundle(MainFragment.KEY_BUNDLE)!!
            .getCharSequenceArray(KEY_ITEM_LIST)!!.toList().shuffled().toTypedArray()
        setupViews()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gameTimer.cancel()
        countDownTimer.cancel()
    }

    private fun setupViews() {
        boundLayout.backButton.setOnClickListener { onBackPressed() }
        setOverlayText(R.string.place_on_forehead)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED, false)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD, false)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER_UNCALIBRATED ->
                System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
            Sensor.TYPE_MAGNETIC_FIELD ->
                System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }
        if (!hasRoundStarted) {
            startRoundIfCorrectOrientation()
        } else if (waitForUpright) {
            waitForUpright()
        } else if (listenForTilt) {
            handleTilt()
        }
    }

    private fun waitForUpright() {
        if (isUpright()) {
            gameTimer.resume()
            boundLayout.apply {
                itemViewpager.next()
                viewpagerContainer.setBackgroundColor(getColor(R.color.appThemeBlue))
                textOverlay.visibility = View.GONE
            }
            waitForUpright = false
            listenForTilt = true
        }
    }

    private fun handleTilt() {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        SensorManager.getOrientation(rotationMatrix, orientationAngles)
        boundLayout.apply {
            if (orientationAngles[1] < 10.toRadians()
                && orientationAngles[1] > (-10).toRadians()
                && orientationAngles[2] > 130.toRadians()) {
                gameTimer.pause()
                resultsList.add("${ResultsActivity.PREFIX_PASS}.${itemArray[itemViewpager.currentItem]}")
                itemViewpager.handlePass()
                viewpagerContainer.setBackgroundColor(getColor(R.color.passGreen))
                setOverlayText(R.string.pass)
                waitForUpright = true
                listenForTilt = false

            } else if (orientationAngles[1] < 10.toRadians()
                && orientationAngles[1] > (-10).toRadians()
                && orientationAngles[2] < 45.toRadians()) {
                gameTimer.pause()
                resultsList.add("${ResultsActivity.PREFIX_FAIL}.${itemArray[itemViewpager.currentItem]}")
                itemViewpager.handleFail()
                viewpagerContainer.setBackgroundColor(getColor(R.color.failRed))
                setOverlayText(R.string.fail)
                waitForUpright = true
                listenForTilt = false
            }
        }
    }

    private fun startRoundIfCorrectOrientation() {
        if (isUpright()) {
            hasRoundStarted = true
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
            setOverlayText(R.string.get_ready)
            countDownTimer.start()
        }
    }

    private fun isUpright(): Boolean {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        when (windowManager.defaultDisplay.rotation) {
            ROTATION_90 -> SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, rotationMatrixAdjusted)

            ROTATION_270 -> SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, rotationMatrixAdjusted)
        }
        SensorManager.getOrientation(rotationMatrixAdjusted, orientationAngles)

        return orientationAngles[1] > (-100).toRadians() && orientationAngles[1] < (-80).toRadians()
    }

    private fun setOverlayText(@StringRes stringId: Int) {
        boundLayout.textOverlay.apply {
            text = getString(stringId)
            visibility = View.VISIBLE
        }
    }

    private fun makeFullScreen() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        actionBar?.hide()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }
}