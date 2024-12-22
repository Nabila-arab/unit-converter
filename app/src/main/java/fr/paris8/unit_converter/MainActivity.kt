package fr.paris8.unit_converter


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.paris8.unit_converter.databinding.ActivityMainBinding
import fr.paris8.unit_converter.ui.theme.UnitconverterTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to Unit Converter
        binding.unitConverterButton.setOnClickListener {
            val intent = Intent(this, UnitConverterActivity::class.java)
            startActivity(intent)
        }


        // Navigate to Temperature Converter
        binding.temperatureConverterButton.setOnClickListener {
            val intent = Intent(this, TemperatureConverterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Weight Converter
        binding.weightConverterButton.setOnClickListener {
            val intent = Intent(this, WeightConverterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Currency Converter
        binding.currencyConverterButton.setOnClickListener {
            val intent = Intent(this, CurrencyConverterActivity::class.java)
            startActivity(intent)
        }

        // Navigate to History
        binding.historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }


    }
}