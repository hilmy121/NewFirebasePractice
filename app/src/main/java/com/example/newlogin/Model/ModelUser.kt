package com.example.newlogin.Model

class ModelUser
    (
    var email:String,
     var password:String,
     var pekerjaan:String,
     var gaji:Int?){
    constructor():this("","","",0)
}