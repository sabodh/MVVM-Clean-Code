package com.online.kotlinsample.presentation.ui
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import com.online.kotlinsample.R
import com.online.kotlinsample.databinding.ActivityMainBinding
import com.online.kotlinsample.databinding.ToolbarLayoutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var actionbarBinding: ToolbarLayoutBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomActionbar()

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }

    /**
     * Handling actionbar configurations
     */
    private fun setCustomActionbar() {
        actionbarBinding = ToolbarLayoutBinding.inflate(layoutInflater)
        actionbarBinding?.backMenu?.visibility = View.GONE
        actionbarBinding?.backMenu?.setOnClickListener {
            onSupportNavigateUp()
            setHeader(getString(R.string.app_name), Gravity.CENTER, View.GONE)
        }
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setBackgroundDrawable(AppCompatResources.getDrawable(baseContext, R.color.white))
            customView = actionbarBinding?.root
        }
        setHeader(getString(R.string.app_name), Gravity.CENTER, View.GONE)
    }
    fun setHeader(header: String, gravity: Int, visibility: Int) {
        actionbarBinding?.actionBarTitle?.text = header
        handleActionbar(gravity, visibility)
    }

    private fun handleActionbar(gravity: Int, visibility: Int) {
        actionbarBinding?.let {
            it.actionBarTitle.gravity = gravity
            it.backMenu.visibility = visibility
        }
    }

}