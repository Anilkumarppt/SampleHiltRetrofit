package com.example.samplehiltretrofit.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.samplehiltretrofit.R
import com.example.samplehiltretrofit.databinding.ActivityMainBinding
import com.example.samplehiltretrofit.util.ResultResource
import com.example.samplehiltretrofit.viewmodel.CharacterListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val characterListViewModel by viewModels<CharacterListViewModel> ()
    private  val TAG = "MainActivity"
    lateinit var mBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.fetchBtn.setOnClickListener {
            fetchData()
        }

    }
    private fun fetchData(){
        mBinding.pbDog.visibility= View.VISIBLE
        characterListViewModel.fetchCharList()
        characterListViewModel._response.observe(this, Observer { response->
           mBinding.pbDog.visibility=View.GONE
            when(response){
               is ResultResource.SUCCESS ->{
                   response.data?.let {
                       Log.d(TAG, "fetchData: Success")
                       Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                       Log.d(TAG, "fetchData: ${it.info.toString()}")
                   }
               }
               is ResultResource.ERROR ->{
                   response.message?.let {
                       Log.d(TAG, "fetchData: Error "+it)
                       Snackbar.make(mBinding.root,it,Snackbar.LENGTH_LONG).show()
                   }
               }
               is ResultResource.LOADING->
               {
                   
               }
           }
        })
    }
}