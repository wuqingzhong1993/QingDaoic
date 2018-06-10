package com.outsource.danding.qingdaoic.bean

import com.google.gson.annotations.SerializedName



data class AuditWaiting(val content:String,val type:String,val todo:String,val tableId:String,val b_state:String,
                        val taskId:String,val title:String,val price:String) {

}