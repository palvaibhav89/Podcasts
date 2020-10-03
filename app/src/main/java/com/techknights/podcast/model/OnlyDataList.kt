package com.techknights.podcast.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class OnlyDataList<T>(
    @SerializedName("success")
    @Expose
    var success: Boolean? = false,

    @SerializedName("data")
    @Expose
    var data: @RawValue List<T>? = null
) : Parcelable