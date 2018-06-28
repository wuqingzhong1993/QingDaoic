package com.outsource.danding.qingdaoic.bean

data class TravelInfo(var persons:MutableList<String>, var city:String,var city_parent:String,
                      var city_town:String,var type:String, val type_code:String,val go_time:String,
                      val back_time:String,val jt_tools:String,var jt_tools_str:String,var fh_tools_str:String,
                      val content:String,val b_number:String,var w_number:String,val days:String,var zs_jd:String,
                      var sendCar:String,var remarks:String,var fare:String,var ht_money:String,var jt_money:String,
                      var zs_money:String,var hs_money:String,var qt_money:String,val all_money:String,val pers:String
                      ) {

}