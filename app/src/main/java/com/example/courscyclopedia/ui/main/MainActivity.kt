package com.example.courscyclopedia.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.courscyclopedia.R
import com.example.courscyclopedia.ui.users.fragments.FacultyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check that the activity is using the layout version with the fragment_container FrameLayout
        if (savedInstanceState == null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            // Otherwise, add the fragment to the 'fragmentContainer'
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FacultyFragment())
                .commit()
        }
    }
}
