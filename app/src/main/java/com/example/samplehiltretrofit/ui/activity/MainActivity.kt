package com.example.samplehiltretrofit.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.samplehiltretrofit.databinding.ActivityMainBinding
import com.example.samplehiltretrofit.util.ResultResource
import com.example.samplehiltretrofit.viewmodel.CharacterListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val characterListViewModel by viewModels<CharacterListViewModel>()
    private val TAG = "MainActivity"
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.fetchBtn.setOnClickListener {
            startActivity(Intent(this, JetPackComposeListActivity::class.java))
        //fetchData()
        }
    }

    private fun fetchData() {
        mBinding.pbDog.visibility = View.VISIBLE
        characterListViewModel.fetchCharList()
        observeEventData()
    }

    private fun observeEventData() {
        lifecycleScope.launchWhenCreated {
            characterListViewModel.eventFlow.collect { resultResource ->
                when (resultResource) {
                    is CharacterListViewModel.Event.Success -> {
                        //mBinding.pbDog.visibility=View.GONE
                        resultResource.characterList.let {
                            Log.d(TAG, "observeEventData: Success")
                            Log.d(TAG, "observeEventData: ")
                            mBinding.txtResult.text =
                                "Characters Count is ${resultResource.characterList.results?.size}"
                        }
                    }
                    is CharacterListViewModel.Event.Failed -> {
                        resultResource.error.let {
                            Snackbar.make(
                                mBinding.root,
                                resultResource.error.toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                    is CharacterListViewModel.Event.Loading -> {
                        if (resultResource.loading)
                            mBinding.pbDog.visibility = View.VISIBLE
                        else
                            mBinding.pbDog.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun observeLiveData() {
        characterListViewModel._response.observe(this, Observer { response ->
            mBinding.pbDog.visibility = View.GONE
            when (response) {
                is ResultResource.SUCCESS -> {
                    response.data?.let {
                        Log.d(TAG, "fetchData: Success")
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        //Log.d(TAG, "fetchData: ${it.info.toString()}")
                        Log.d(TAG, "fetchData: ${it.results?.size}")
                        mBinding.txtResult.text = "Characters Count is ${it.results?.size}"
                    }
                }
                is ResultResource.ERROR -> {
                    response.message?.let {
                        Log.d(TAG, "fetchData: Error " + it)
                        Snackbar.make(mBinding.root, it, Snackbar.LENGTH_LONG).show()
                    }
                }
                is ResultResource.LOADING -> {

                }
            }
        })
    }
}