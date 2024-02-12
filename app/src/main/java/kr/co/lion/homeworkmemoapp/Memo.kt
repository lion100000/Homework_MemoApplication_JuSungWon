package kr.co.lion.homeworkmemoapp

import android.os.Parcel
import android.os.Parcelable

open class Memo():Parcelable {

    // 제목
    var title = ""

    // 내용
    var content = ""

    // 작성 날짜
    var writeDate = ""


    constructor(parcel: Parcel) : this() {
        title = parcel.readString()!!
        content = parcel.readString()!!
        writeDate = parcel.readString()!!
    }

    // Parcel에 프로퍼티의 값을 담아준다.
    fun addToParcel(parcel:Parcel){
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(writeDate)
    }

    // Parcel로 부터 데이터를 추출하여 프로퍼티에 담아준다.
    fun getFromParcel(parcel:Parcel){
        title = parcel.readString()!!
        content = parcel.readString()!!
        writeDate = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(writeDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Memo> {
        override fun createFromParcel(parcel: Parcel): Memo {
            return Memo(parcel)
        }

        override fun newArray(size: Int): Array<Memo?> {
            return arrayOfNulls(size)
        }
    }
}