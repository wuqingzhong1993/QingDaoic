package com.outsource.danding.qingdaoic.bean

data class AuditHistoryInfo(val expendId:String, val expendType:String,
                            val number:String, val budgetAmount:String,
                            val createName:String, val createTime:String,
                            val recordId:String,val audit_type:String,val result:String,
                            val createId:String,val sumAmount:String) {

}