package com.dcplugin.cam

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dcplugin.cam.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    data class Module(val name: String, val badge: String, val target: Class<*>?)

    private val modules = listOf(
        Module("🎙️ Wireless Mic", "SOON", null),
        Module("📜 Teleprompter", "SOON", null),
        Module("💬 Chat Reader", "SOON", null),
        Module("📺 OBS Monitor", "SOON", null),
        Module("⏱️ Timer / Countdown", "READY", TimerActivity::class.java),
        Module("🔊 Soundboard", "READY", SoundboardActivity::class.java),
        Module("📊 Stream Stats", "READY", StatsActivity::class.java),
        Module("🎵 Media Controller", "SOON", null),
        Module("📋 Show Rundown", "SOON", null),
        Module("📡 Connection Hub", "SOON", null)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = resources.displayMetrics.widthPixels / 2 - 32
        for (m in modules) {
            val btn = Button(requireContext()).apply {
                text = "${m.name}\n[${m.badge}]"
                isAllCaps = false
                textSize = 13f
                setBackgroundColor(Color.parseColor(if (m.badge == "READY") "#2E4E32" else "#262626"))
                setTextColor(Color.WHITE)
                layoutParams = GridLayout.LayoutParams().apply {
                    this.width = width
                    this.height = 180
                    setGravity(Gravity.FILL)
                }
                setOnClickListener {
                    if (m.target != null) {
                        startActivity(Intent(requireContext(), m.target))
                    } else {
                        Toast.makeText(requireContext(), "${m.name} is coming in next release!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.moduleGrid.addView(btn)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
