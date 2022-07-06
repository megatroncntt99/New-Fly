package com.vannv.train.newsfly.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Author: vannv8@fpt.com.vn
 * Date: 04/07/2022
 */
@Parcelize
sealed class DataFragmentResult : Parcelable {
    object OnPanTiltCamera : DataFragmentResult()
    object OnNotPanTiltCamera : DataFragmentResult()
}