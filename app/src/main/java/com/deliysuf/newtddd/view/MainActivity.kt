package com.deliysuf.newtddd.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deliysuf.newtddd.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
     lateinit var artFragmentFactory: ArtFragmentFActory

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=artFragmentFactory

        setContentView(R.layout.activity_main)

    }
}