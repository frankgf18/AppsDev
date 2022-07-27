package com.example.appsdev.Home.Drawer

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDrawerBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawerFragment : BaseFragment<FragmentDrawerBinding>(FragmentDrawerBinding::inflate), NavigationView.OnNavigationItemSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.btn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navegador.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_item_uno -> binding.tvPrueba.text = "Uno"
            R.id.nav_item_dos -> binding.tvPrueba.text = "Dos"
            R.id.nav_item_tres -> binding.tvPrueba.text = "Tres"
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}