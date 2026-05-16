package com.radio.app

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.radio.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StationAdapter
    private lateinit var favoritesManager: FavoritesManager
    private lateinit var itemTouchHelper: ItemTouchHelper
    private var allStations = StationList.stations
    private var showingFavorites = false
    private var showingCustom = false
    private var currentPlayingUrl: String? = null
    private var customCounter = 0

    private val stateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == RadioService.BROADCAST_STATE) {
                val state = intent.getIntExtra(RadioService.EXTRA_STATE, -1)
                updateStatus(state)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoritesManager = FavoritesManager(this)
        checkNotificationPermission()
        loadCustomStations()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (adapter.selectionMode) {
                    exitSelectionMode()
                } else {
                    finish()
                }
            }
        })
        setupAdapter()
        setupTabs()
        setupCustomUrl()
        setupPlayStop()
        setupSelectionBar()
        showFavorites()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter().apply { addAction(RadioService.BROADCAST_STATE) }
        LocalBroadcastManager.getInstance(this).registerReceiver(stateReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(stateReceiver)
    }

    private fun loadCustomStations() {
        allStations = StationList.stations + favoritesManager.getCustomStations()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
            }
        }
    }

    private fun setupAdapter() {
        adapter = StationAdapter(
            onPlay = { station ->
                playStation(station.url)
                binding.tvNowPlayingStation.text = station.name
            },
            onFavoriteToggle = { station ->
                if (station.category == "Personalizadas" && favoritesManager.isFavorite(station.id)) {
                    favoritesManager.removeCustomStation(station.id)
                } else {
                    favoritesManager.toggleFavorite(station.id)
                }
                loadCustomStations()
                adapter.setFavorites(favoritesManager.getFavoriteIds())
                refreshCurrentList()
            },
            onStartDrag = { holder -> itemTouchHelper.startDrag(holder) }
        )
        adapter.setFavorites(favoritesManager.getFavoriteIds())
        adapter.onSelectionChanged = { count ->
            binding.tvSelectionCount.text = if (count > 0) "$count seleccionadas" else ""
            binding.btnDeleteSelected.isEnabled = count > 0
            binding.btnDeleteSelected.alpha = if (count > 0) 1f else 0.5f
        }
        adapter.onSelectionModeEntered = {
            enterSelectionMode()
        }
        binding.rvStations.layoutManager = LinearLayoutManager(this)
        binding.rvStations.adapter = adapter

        itemTouchHelper = StationAdapter.createItemTouchHelper { from, to ->
            val list = currentList.toMutableList()
            val item = list.removeAt(from)
            list.add(to, item)
            adapter.submitList(list)
            val orderedIds = list.map { it.id }
            favoritesManager.setFavoriteOrder(orderedIds)
        }
        itemTouchHelper.attachToRecyclerView(binding.rvStations)
    }

    private val currentList: List<RadioStation>
        get() = adapter.currentList

    private fun setupTabs() {
        binding.tabAll.setOnClickListener {
            if (adapter.selectionMode) exitSelectionMode()
            showAllStations()
            updateTabSelection(0)
        }
        binding.tabFavorites.setOnClickListener {
            if (adapter.selectionMode) exitSelectionMode()
            showFavorites()
            updateTabSelection(1)
        }
        binding.tabCustom.setOnClickListener {
            if (adapter.selectionMode) exitSelectionMode()
            showCustomPanel()
            updateTabSelection(2)
        }
    }

    private fun setupSelectionBar() {
        binding.btnDeleteSelected.setOnClickListener {
            deleteSelected()
        }
        binding.btnDoneSelection.setOnClickListener {
            exitSelectionMode()
        }
    }

    private fun enterSelectionMode() {
        binding.selectionBar.visibility = android.view.View.VISIBLE
        binding.tabBar.visibility = android.view.View.GONE
    }

    private fun exitSelectionMode() {
        adapter.exitSelectionMode()
        binding.selectionBar.visibility = android.view.View.GONE
        binding.tabBar.visibility = android.view.View.VISIBLE
    }

    private fun deleteSelected() {
        val selected = adapter.getSelectedStations()
        if (selected.isEmpty()) return
        val count = selected.size

        android.app.AlertDialog.Builder(this)
            .setTitle("Eliminar $count emisora${if (count > 1) "s" else ""}")
            .setMessage("Se eliminarán las emisoras seleccionadas.")
            .setPositiveButton("Eliminar") { _, _ ->
                for (station in selected) {
                    if (station.category == "Personalizadas") {
                        favoritesManager.removeCustomStation(station.id)
                    } else if (showingFavorites) {
                        favoritesManager.removeFavorite(station.id)
                    } else {
                        favoritesManager.hideStation(station.id)
                    }
                }
                loadCustomStations()
                adapter.setFavorites(favoritesManager.getFavoriteIds())
                exitSelectionMode()
                refreshCurrentList()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateTabSelection(index: Int) {
        val tabs = listOf(binding.tabAll, binding.tabFavorites, binding.tabCustom)
        tabs.forEachIndexed { i, tv ->
            tv.setTextColor(if (i == index) getColor(R.color.primary) else getColor(R.color.text_secondary))
        }
    }

    private fun setupCustomUrl() {
        binding.btnPlayUrl.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                binding.tvNowPlayingStation.text = "URL directa"
                playStation(url)
            }
        }
        binding.btnSaveUrl.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                showSaveDialog(url)
            }
        }
    }

    private fun updateSavedUrls() {
        val saved = favoritesManager.getCustomStations()
        binding.tvSavedUrls.visibility = if (saved.isNotEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        binding.tvSavedUrls.text = "Guardadas: ${saved.joinToString(", ") { it.name }}"
    }

    private fun showSaveDialog(url: String) {
        val input = android.widget.EditText(this).apply {
            setHint("Nombre de la emisora")
            setText("Mi radio ${++customCounter}")
            setSingleLine(true)
        }
        android.app.AlertDialog.Builder(this)
            .setTitle("Guardar emisora")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->
                val name = input.text.toString().trim().ifEmpty { "Mi radio $customCounter" }
                if (favoritesManager.addCustomStation(name, url)) {
                    loadCustomStations()
                    adapter.setFavorites(favoritesManager.getFavoriteIds())
                    updateSavedUrls()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun setupPlayStop() {
        binding.btnPlayStop.setOnClickListener {
            if (currentPlayingUrl != null) {
                stopRadio()
            }
        }
    }

    private fun refreshCurrentList() {
        when {
            showingFavorites -> showFavorites()
            showingCustom -> showCustomPanel()
            else -> showAllStations()
        }
    }

    private fun showAllStations() {
        showingFavorites = false
        showingCustom = false
        loadCustomStations()
        val hidden = favoritesManager.getHiddenIds()
        adapter.submitList(allStations.filter { it.id !in hidden })
        adapter.setFavorites(favoritesManager.getFavoriteIds())
        binding.rvStations.visibility = android.view.View.VISIBLE
        binding.customPanel.visibility = android.view.View.GONE
    }

    private fun showFavorites() {
        showingFavorites = true
        showingCustom = false
        loadCustomStations()
        val favIds = favoritesManager.getFavoriteIdsOrdered()
        val favStations = allStations.filter { it.id in favIds }
        val sorted = favStations.sortedBy { favIds.indexOf(it.id) }
        adapter.submitList(sorted)
        adapter.setFavorites(favIds.toSet())
        binding.rvStations.visibility = android.view.View.VISIBLE
        binding.customPanel.visibility = android.view.View.GONE
    }

    private fun showCustomPanel() {
        showingFavorites = false
        showingCustom = true
        binding.rvStations.visibility = android.view.View.GONE
        binding.customPanel.visibility = android.view.View.VISIBLE
        updateSavedUrls()
    }

    private fun playStation(url: String) {
        currentPlayingUrl = url
        binding.btnPlayStop.setImageResource(android.R.drawable.ic_media_pause)
        val intent = Intent(this, RadioService::class.java).apply {
            action = RadioService.ACTION_PLAY
            putExtra(RadioService.EXTRA_URL, url)
        }
        ContextCompat.startForegroundService(this, intent)
    }

    private fun stopRadio() {
        currentPlayingUrl = null
        binding.btnPlayStop.setImageResource(android.R.drawable.ic_media_play)
        binding.tvStatus.setText(R.string.disconnected)
        binding.tvNowPlayingStation.text = "Selecciona una emisora"
        val intent = Intent(this, RadioService::class.java).apply {
            action = RadioService.ACTION_STOP
        }
        startService(intent)
    }

    private fun updateStatus(state: Int) {
        runOnUiThread {
            when (state) {
                RadioService.STATE_IDLE -> {
                    binding.tvStatus.setText(R.string.disconnected)
                }
                RadioService.STATE_CONNECTING -> {
                    binding.tvStatus.setText(R.string.connecting)
                }
                RadioService.STATE_PLAYING -> {
                    binding.tvStatus.text = "Sonando ahora"
                }
                RadioService.STATE_BUFFERING -> {
                    binding.tvStatus.setText(R.string.buffering)
                }
            }
        }
    }
}
