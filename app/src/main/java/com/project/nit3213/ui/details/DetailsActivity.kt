package com.project.nit3213.ui.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.databinding.ActivityDetailsBinding
import java.io.Serializable

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val EXTRA_ENTITY = "extra_entity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entity = intent.serializable<EntityItem>(EXTRA_ENTITY)

        entity?.let {
            binding.tvProperty1.text = it.property1 ?: "N/A"
            binding.tvProperty2.text = it.property2 ?: "N/A"
            binding.tvDescription.text = it.description ?: "No description available."
        }
    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializableExtra(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializableExtra(key) as? T
        }
    }
}
