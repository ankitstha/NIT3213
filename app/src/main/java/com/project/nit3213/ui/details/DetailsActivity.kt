package com.project.nit3213.ui.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.databinding.ActivityDetailsBinding
import java.io.Serializable

// Activity displaying detailed attributes of a selected entity
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val EXTRA_ENTITY = "extra_entity" // Intent extra key for passing EntityItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve EntityItem passed via intent extra
        val entity = intent.serializable<EntityItem>(EXTRA_ENTITY)

        // Bind entity properties to UI text views
        entity?.let {
            binding.tvProperty1.text = it.property1 ?: "N/A"
            binding.tvProperty2.text = it.property2 ?: "N/A"
            binding.tvDescription.text = it.description ?: "No description available."
        }
    }

    // Extension function handling getSerializableExtra compatibility across Android versions
    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtra(key, T::class.java) // Android 13+ type-safe API
        } else {
            @Suppress("DEPRECATION")
            getSerializableExtra(key) as? T           // Legacy API for Android 12 and below
        }
    }
}

