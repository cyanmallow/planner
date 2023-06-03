package com.example.planner.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.planner.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        val isLogin: Boolean = auth.currentUser != null

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            //wait 2 seconds and then go to home or signin fargments
            if (isLogin)
                navControl.navigate(R.id.action_splashFragment_to_homeFragment)
            else
                navControl.navigate(R.id.action_splashFragment_to_signInFragment)

        }, 2000)
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        navControl = Navigation.findNavController(view)
    }
}