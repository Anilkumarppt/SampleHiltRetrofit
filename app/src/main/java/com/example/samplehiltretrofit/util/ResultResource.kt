package com.example.samplehiltretrofit.util
/*
* Base Result Class */
sealed class ResultResource<T>(val data:T?=null,
                               val message:String?=null) {
    class SUCCESS<T>(data: T?):ResultResource<T>(data=data)
    class ERROR<T>(message: String?):ResultResource<T>(message = message)
    class LOADING<T>():ResultResource<T>()
}