package fr.paris8.unit_converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import fr.paris8.unit_converter.databinding.ActivityUnitConverterBinding

class UnitConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activer le bouton de retour dans la barre supérieure
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up dropdowns
        val units = resources.getStringArray(R.array.unit_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)

        binding.sourceUnitSpinner.adapter = adapter
        binding.targetUnitSpinner.adapter = adapter

        // Écouter les changements dans le champ de saisie
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateConversion()
            }
        })

        // Si nécessaire, décommentez ces lignes pour écouter les changements dans les spinners
        // binding.sourceUnitSpinner.setOnItemSelectedListener { _, _, _, _ -> calculateConversion() }
        // binding.targetUnitSpinner.setOnItemSelectedListener { _, _, _, _ -> calculateConversion() }
    }

    private fun calculateConversion() {
        val input = binding.inputEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sourceUnit = binding.sourceUnitSpinner.selectedItem.toString()
        val targetUnit = binding.targetUnitSpinner.selectedItem.toString()

        val conversionRate = getConversionRate(sourceUnit, targetUnit)
        val result = input * conversionRate

        binding.resultTextView.text = result.toString()

        // Ajouter l'action à l'historique partagé
        val action = "$input $sourceUnit -> $result $targetUnit"
        HistoryManager.addHistoryEntry("Unit Conversion: $action")
    }

    private fun getConversionRate(source: String, target: String): Double {
        // Taux de conversion en mètres
        val rates = mapOf(
            "Meters" to 1.0,
            "Kilometers" to 1000.0,
            "Miles" to 1609.34,
            "Inches" to 0.0254,
            "Yards" to 0.9144
        )
        return (rates[source] ?: 1.0) / (rates[target] ?: 1.0)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // Identifiant du bouton "Retour"
                finish() // Termine cette activité et revient à l'écran précédent
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
