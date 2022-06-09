package com.example.samplehiltretrofit.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Info(@SerializedName("count")
                @Expose
                val count:Int,
                @SerializedName("pages")
                val page:Int,
                @SerializedName("next")
                val next:String,
                @SerializedName("prev")
                val prev:String?)
