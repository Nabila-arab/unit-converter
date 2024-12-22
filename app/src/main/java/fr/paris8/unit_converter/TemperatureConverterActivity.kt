package fr.paris8.unit_converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import fr.paris8.unit_converter.databinding.ActivityTemperatureConverterBinding


class TemperatureConverterActivity : AppCompatActivity(){


    private lateinit var binding: ActivityTemperatureConverterBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemperatureConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activer le bouton de retour dans la barre supérieure
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up dropdowns
        val units = resources.getStringArray(R.array.temperature_units)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)

        binding.sourceTemperatureSpinner.adapter = adapter
        binding.targetTemperatureSpinner.adapter = adapter


        // Listen for input changes
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateConversion()
            }
        })

        //binding.sourceUnitSpinner.setOnItemSelectedListener { _, _, _, _ -> calculateConversion() }
        //binding.targetUnitSpinner.setOnItemSelectedListener { _, _, _, _ -> calculateConversion() }
    }

    private fun calculateConversion() {
        val input = binding.inputEditText.text.toString().toDoubleOrNull() ?: 0.0
        val sourceUnit = binding.sourceTemperatureSpinner.selectedItem.toString()
        val targetUnit = binding.targetTemperatureSpinner.selectedItem.toString()

        val conversionRate = getConversionRate(sourceUnit, targetUnit)
        val result = input * conversionRate

        binding.resultTextView.text = result.toString()

        // Ajouter l'action à l'historique partagé
        val action = "$input $sourceUnit -> $result $targetUnit"
        HistoryManager.addHistoryEntry("Unit Conversion: $action")
    }


    private fun getConversionRate(source: String, target: String): Double {
        val conversionFunctions = mapOf< Pair<String, String>, (Double) -> Double>(
            "Celsius" to "Fahrenheit" to { value -> (value * 9 / 5) + 32 },
            "Fahrenheit" to "Celsius" to { value -> (value - 32) * 5 / 9 },
            "Celsius" to "Kelvin" to { value -> value + 273.15 },
            "Kelvin" to "Celsius" to { value -> value - 273.15 },
            "Fahrenheit" to "Kelvin" to { value -> ((value - 32) * 5 / 9) + 273.15 },
            "Kelvin" to "Fahrenheit" to { value -> ((value - 273.15) * 9 / 5) + 32 }
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