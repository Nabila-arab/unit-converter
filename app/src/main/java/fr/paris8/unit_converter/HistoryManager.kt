package fr.paris8.unit_converter

object HistoryManager {

    // Liste partagée pour enregistrer l'historique des conversions
    val historyList = mutableListOf<String>()

    // Fonction pour ajouter un élément à l'historique
    fun addHistoryEntry(entry: String) {
        historyList.add(entry)
    }

    // Fonction pour récupérer tout l'historique
    fun getHistory(): List<String> {
        return historyList
    }

    // (Optionnel) Fonction pour effacer l'historique
    fun clearHistory() {
        historyList.clear()
    }
}