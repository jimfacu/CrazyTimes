package com.example.crazytimes.Core.Network

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class ChangeIconBitMap {

    companion object {
         @SuppressLint("ResourceType")
         fun bitmapDescriptorFromVector(
            context: Context,
            @DrawableRes vectorDrawableResourceId: Int
        ): BitmapDescriptor? {
            val background =
                ContextCompat.getDrawable(context,vectorDrawableResourceId)
            background!!.setBounds(
                0,
                0,
                background.intrinsicWidth,
                background.intrinsicHeight
            )

            val bitmap = Bitmap.createBitmap(
                background.intrinsicWidth,
                background.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            background.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}