package fr.paris8.unit_converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import fr.paris8.unit_converter.databinding.ActivityWeightConverterBinding

class WeightConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeightConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activer le bouton de retour dans la barre supérieure
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up dropdowns
        val units = listOf("Gram", "Kilogram", "Tonne", "Milligram", "Microgram", "Decigram")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)

        binding.sourceWeightSpinner.adapter = adapter
        binding.targetWeighteSpinner.adapter = adapter

        // Listen for input changes
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateConversion()
            }
        })
    }

    private fun calculateConversion() {
        val input = binding.inputEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sourceUnit = binding.sourceWeightSpinner.selectedItem.toString()
        val targetUnit = binding.targetWeighteSpinner.selectedItem.toString()

        val conversionRate = getConversionRate(sourceUnit, targetUnit)
        val result = input * conversionRate

        binding.resultTextView.text = result.toString()

        // Ajouter l'action à l'historique partagé
        val action = "$input $sourceUnit -> $result $targetUnit"
        HistoryManager.addHistoryEntry("Unit Conversion: $action")
    }

    private fun getConversionRate(source: String, target: String): Double {
        val conversionFunctions = mapOf< Pair<String, String>, (Double) -> Double>(
            // Weight conversions
            "Gram" to "Kilogram" to { value -> value / 1000 },
            "Kilogram" to "Gram" to { value -> value * 1000 },
            "Gram" to "Tonne" to { value -> value / 1_000_000 },
            "Tonne" to "Gram" to { value -> value * 1_000_000 },
            "Gram" to "Milligram" to { value -> value * 1000 },
            "Milligram" to "Gram" to { value -> value / 1000 },
            "Gram" to "Microgram" to { value -> value * 1_000_000 },
            "Microgram" to "Gram" to { value -> value / 1_000_000 },
            "Kilogram" to "Tonne" to { value -> value / 1000 },
            "Tonne" to "Kilogram" to { value -> value * 1000 },
            "Milligram" to "Microgram" to { value -> value * 1000 },
            "Microgram" to "Milligram" to { value -> value / 1000 },
            "Gram" to "Decigram" to { value -> value * 10 },
            "Decigram" to "Gram" to { value -> value / 10 },
            "Kilogram" to "Decigram" to { value -> value * 10_000 },
            "Decigram" to "Kilogram" to { value -> value / 10_000 },
            "Decigram" to "Milligram" to { value -> value * 100 },
            "Milligram" to "Decigram" to { value -> value / 100 },

            // Volume conversions (if needed)
            "Gallon" to "Liter" to { value -> value * 3.78541 },
            "Liter" to "Gallon" to { value -> value / 3.78541 }
        )

        val identityFunction: (Double) -> Double = { it }

        return { value: Double ->
            conversionFunctions[source to target]?.invoke(value) ?: identityFunction(value)
        }(1.0)
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
