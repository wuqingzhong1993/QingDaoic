package com.outsource.danding.qingdaoic.bean

import android.os.Parcel
import android.os.Parcelable


data class Asset(val id:String, val assetsNum:String,val assetsName:String,
                 val amount:Int,val price:String,val unit:String,val version:String,
                 val createName_sys:String, val createTime_sys:String,val remark:String,
                 val gainDate:String,val markFileDate:String,val postingDate:String,
                 val assetsState:String,val gainWay:String,val typeOfValue:String,
                 val typeId_str:String,val useDeptId:String,val storePlace:String,
                 val usePerson:String,val typeOfValue_str:String):Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(),parcel.readString(),parcel.readString(),
            parcel.readString(),parcel.readString(),parcel.readString(),
            parcel.readString(),parcel.readString(),parcel.readString(),
            parcel.readString(),parcel.readString()
            ) {}

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(assetsNum)
        dest?.writeString(assetsName)
        dest?.writeInt(amount)
        dest?.writeString(price)
        dest?.writeString(unit)
        dest?.writeString(version)

        dest?.writeString(createName_sys)
        dest?.writeString(createTime_sys)
        dest?.writeString(remark)

        dest?.writeString(gainDate)
        dest?.writeString(markFileDate)
        dest?.writeString(postingDate)

        dest?.writeString(assetsState)
        dest?.writeString(gainWay)
        dest?.writeString(typeOfValue)
        dest?.writeString(typeId_str)
        dest?.writeString(useDeptId)
        dest?.writeString(storePlace)

        dest?.writeString(usePerson)
        dest?.writeString(typeOfValue_str)

    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Asset> {
        override fun createFromParcel(parcel: Parcel): Asset {
            return Asset(parcel)
        }

        override fun newArray(size: Int): Array<Asset?> {
            return arrayOfNulls(size)
        }
    }

}