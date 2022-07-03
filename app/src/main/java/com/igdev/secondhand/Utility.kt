package com.igdev.secondhand

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun setFullScreen (window: Window){
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun lightStatusBar(window: Window, isLight: Boolean = true, isLightNav: Boolean = true){
    val wic = WindowInsetsControllerCompat (window, window.decorView)
/*    wic.isAppearanceLightStatusBars = isLight*/
    wic.isAppearanceLightNavigationBars = isLightNav
}
fun rupiah(number:Int): String{
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp "
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(number).dropLast(3)
}