package com.outsource.danding.qingdaoic.bean

data class AuditTravel(val persons:String, val city:String, val city_parent:String, val city_town:String, val type:String, val type_code:String,
                       val go_time:String, val back_time:String, val jt_tools:String, val fh_tools:String,
                       val jt_tools_str:String, val fh_tools_str:String, val content:String,
                       val b_number:String, val w_number:String, val days:String, val zs_jd:String,
                       val sendCar:String, val remarks:String, val fare:String, val ht_money:String,
                       val jt_money:String, val zs_money:String, val hs_money:String, val qt_money:String,
                       val all_money:String, val isUserCar:String) {

}