package fr.paris8.unit_converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.paris8.unit_converter.R

class HistoryAdapter(private val historyList: List<String>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    // ViewHolder pour gérer une ligne de l'historique
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyTextView: TextView = itemView.findViewById(R.id.historyItemText)
    }

    // Crée une nouvelle vue pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    // Lie les données à la vue pour chaque élément
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val entry = historyList[position]
        holder.historyTextView.text = entry
    }

    // Retourne le nombre d'éléments dans la liste
    override fun getItemCount(): Int {
        return historyList.size
    }
}
