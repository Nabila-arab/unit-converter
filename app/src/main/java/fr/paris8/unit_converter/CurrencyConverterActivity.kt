package fr.paris8.unit_converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import fr.paris8.unit_converter.databinding.ActivityCurrencyConverterBinding

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activer le bouton de retour dans la barre supérieure
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val units = resources.getStringArray(R.array.currency_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)

        binding.sourceCurrencySpinner.adapter = adapter
        binding.targetCurrencySpinner.adapter = adapter

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
        val sourceCurrency = binding.sourceCurrencySpinner.selectedItem.toString()
        val targetCurrency = binding.targetCurrencySpinner.selectedItem.toString()

        val conversionRate = getConversionRate(sourceCurrency, targetCurrency)
        val result = input * conversionRate

        binding.resultTextView.text = result.toString()

        // Ajouter l'action à l'historique partagé
        val action = "$input $sourceCurrency -> $result $targetCurrency"
        HistoryManager.addHistoryEntry("Currency Conversion: $action")
    }


    private fun getConversionRate(source: String, target: String): Double {
        val rates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "GBP" to 0.75
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