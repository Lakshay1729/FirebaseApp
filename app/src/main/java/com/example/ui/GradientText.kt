package com.example.ui

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.icu.text.Transliterator
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView


class GradientTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context?) : super(context!!, null, -1) {}
    constructor(context: Context?,
                attrs: AttributeSet?) : super(context!!, attrs, -1) {
    }
    constructor(context: Context?,
                attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {
    }
    override fun onLayout(changed: Boolean,
                          left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.shader = LinearGradient(0.0F,0.0F,0.0F,height.toFloat(), Color.DKGRAY,Color.BLACK,Shader.TileMode.CLAMP)
        }
    }
}