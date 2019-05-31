package com.outsource.danding.qingdaoic.bean


data class AuditByOperator(val reimbId:String, val expendId:String,val expendType:String,
                           val internalId:String,val internalName:String,val applyDeptId:String,
                        val applyDeptName:String,val  taskId:String,val reason:String,
                           val sumNum:Int,val sumAmount:Double,val createId:String,
                           val createName:String,val createTime:String,val isEdit:String,
                           val id:String,val state:String
                           ) {

}