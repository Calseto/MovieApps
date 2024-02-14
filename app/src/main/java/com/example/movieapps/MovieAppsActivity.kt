package com.example.movieapps

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.dompekid.base.BaseFragment
import com.example.movieapps.databinding.ActivityMovieAppsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieAppsActivity : AppCompatActivity() {
    lateinit var binding:ActivityMovieAppsBinding

    //Harus Dipelajari Lebih Lanjut
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMovieAppsBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        CoroutineScope(Dispatchers.Main).launch {
            var isConnected = isInternetAvailable(this@MovieAppsActivity)
            while (isActive) {
                val newConnectionState = isInternetAvailable(this@MovieAppsActivity)
                delay(1000)
                if(isConnected!=newConnectionState){
                    isConnected = newConnectionState
                    if (isConnected){
                        Toast.makeText(this@MovieAppsActivity, "Internet Tehubung Kembali", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@MovieAppsActivity, "Internet Terputus", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        findNavController(binding.activityFragmentFrame.id)
        setupSwipeToReload()

    }

    private fun setupSwipeToReload(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            val navHostFragment = supportFragmentManager.findFragmentById(binding.activityFragmentFrame.id)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentFragment is BaseFragment<*>) {
                currentFragment.reload()
            }
            binding.swipeRefreshLayout.isRefreshing=false
        }
    }
}