package fr.paris8.unit_converter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.paris8.unit_converter.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activer le bouton retour dans la barre supérieure
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Récupérer l'historique depuis HistoryManager
        //val historyList = HistoryManager.getHistory()

        // Configurer RecyclerView - Récupérer l'historique depuis HistoryManager
        val historyList = HistoryManager.getHistory()

        val adapter = HistoryAdapter(historyList)
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = adapter

    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // Bouton retour
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
