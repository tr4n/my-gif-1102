package com.example.mygif1102

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.mygif1102.fragments.*
import com.example.mygif1102.model.GifMessage
import com.example.mygif1102.model.MessageData
import com.example.mygif1102.model.TitleMessage
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        Fresco.initialize(this)

        // loadFragment(R.id.frameNavbar, NavBarFragment())
        loadFragment(R.id.frameContent, HomeFragment())
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> loadFragment(R.id.frameContent, HomeFragment())
                R.id.item_explore -> loadFragment(R.id.frameContent, ExploreFragment())
                R.id.item_favorite -> loadFragment(R.id.frameContent, FavoriteFragment())
                R.id.item_user -> loadFragment(R.id.frameContent, UserFragment())
                else -> false
            }
        }
    }

    override fun onFragmentInteraction(message: MessageData) {
        val bundle = Bundle()
        // if (message.type == Constants.MESSAGE_TYPE_TITLE) {
        if (message is TitleMessage) {
            bundle.putParcelable(Constants.EXTRA_TITLE, message)
            val navBarFragment = NavBarFragment()
            navBarFragment.arguments = bundle
            loadFragment(R.id.frameNavbar, navBarFragment)
        } else if (message is GifMessage) {
            bundle.putParcelable(Constants.EXTRA_GIF, message)
            val detailFragment = DetailFragment()
            detailFragment.arguments = bundle
            loadFragmentBackStack(R.id.constraintMain, detailFragment)
        }
    }

    private fun loadFragment(id: Int, fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
        return true
    }

    private fun loadFragmentBackStack(id: Int, fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().add(id, fragment).addToBackStack(null).commit()
        return true
    }
}
