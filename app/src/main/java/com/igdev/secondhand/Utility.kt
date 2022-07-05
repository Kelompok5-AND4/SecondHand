package com.igdev.secondhand

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.text.DecimalFormat
import android.app.ActionBar
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.view.View
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

private fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(
    selectedImage: Uri,
    context: Context,
): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()

    return myFile
}

fun reduceImageSize(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun date(date: String): String {
    var kotlin = date
    kotlin = kotlin.drop(5)
    val month = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val day = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val hours = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val minute = kotlin.take(2)
    val year = date.take(4)

    return "$day-$month-$year, $hours:$minute"

}

val listCategory : MutableList<String> = ArrayList()
val listCategoryId : MutableList<Int> = ArrayList()
