package com.dcplugin.cam

import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dcplugin.cam.databinding.ActivityTimerBinding

/** Full-screen countdown timer for stream breaks / intros. */
class TimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimerBinding
    private var countDownTimer: CountDownTimer? = null
    private var remainingMs = 5 * 60 * 1000L
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Timer / Countdown"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        updateDisplay()

        binding.setBtn.setOnClickListener {
            val minutes = binding.minutesInput.text.toString().toIntOrNull() ?: 5
            remainingMs = minutes * 60 * 1000L
            stopTimer()
            updateDisplay()
        }

        binding.startPauseBtn.setOnClickListener {
            if (running) pauseTimer() else startTimer()
        }

        binding.resetBtn.setOnClickListener {
            val minutes = binding.minutesInput.text.toString().toIntOrNull() ?: 5
            remainingMs = minutes * 60 * 1000L
            stopTimer()
            updateDisplay()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(remainingMs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMs = millisUntilFinished
                updateDisplay()
            }
            override fun onFinish() {
                remainingMs = 0
                running = false
                binding.startPauseBtn.text = "Start"
                binding.timerDisplay.text = "00:00"
                binding.timerDisplay.setTextColor(0xFFD32F2F.toInt())
            }
        }.start()
        running = true
        binding.startPauseBtn.text = "Pause"
        binding.timerDisplay.setTextColor(0xFFFFFFFF.toInt())
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        running = false
        binding.startPauseBtn.text = "Start"
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        running = false
        binding.startPauseBtn.text = "Start"
        binding.timerDisplay.setTextColor(0xFFFFFFFF.toInt())
    }

    private fun updateDisplay() {
        val totalSeconds = remainingMs / 1000
        val m = totalSeconds / 60
        val s = totalSeconds % 60
        binding.timerDisplay.text = String.format("%02d:%02d", m, s)
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
