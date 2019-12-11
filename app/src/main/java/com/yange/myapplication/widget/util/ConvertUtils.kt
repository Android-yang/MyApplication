package com.yange.myapplication.widget.util

import android.content.Context
import android.support.v4.util.Pair
import android.util.TypedValue

import java.math.BigDecimal
import java.math.RoundingMode

object ConvertUtils {

    private const val FEET_TO_INCHES_COEF = 12
    private const val METERS_TO_FEET_COEF = 3.2808f
    private const val INCHES_TO_FEET_COEF = 1.0 / 12.0
    private const val METERS_TO_MILES_COEF = 0.000621371f
    private const val METERS_TO_INCHES_COEF = METERS_TO_FEET_COEF * FEET_TO_INCHES_COEF
    private const val KILO_TO_POUND_COEF = 2.20462f
    private const val KILO_TO_TONS_COEF = 0.001f
    private const val POUND_TO_TONS = 1.0 / 2204.6

    private const val DEFAULT_FLOAT_SCALE = 2

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Double =
            TypedValue
                    .applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                            context.resources.displayMetrics)
                    .toDouble()

    /**
     * return the minimum of width and height in dpi
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int = Math.min(context.resources.displayMetrics.widthPixels / context.resources.displayMetrics.density,
            context.resources.displayMetrics.heightPixels / context.resources.displayMetrics.density).toInt()

    @JvmStatic
    fun inchesToFeetAndInches(inches: Int): Pair<Int, Int> =
            Pair.create(inches / FEET_TO_INCHES_COEF, inches % FEET_TO_INCHES_COEF)

    @JvmStatic
    fun metersToFeetAndInches(meters: Float): Pair<Int, Int> =
            with(metersToInchesRounded(meters)) {
                inchesToFeetAndInches(this)
            }

    @JvmOverloads
    @JvmStatic
    fun inchesToFeetScaled(inches: Int, scale: Int = DEFAULT_FLOAT_SCALE): Float =
            BigDecimal.valueOf((inches * INCHES_TO_FEET_COEF).toDouble())
                    .setScale(scale, RoundingMode.HALF_UP)
                    .toFloat()


    @JvmStatic
    fun poundsToKilos(pounds: Int) = pounds / KILO_TO_POUND_COEF

    @JvmStatic
    fun kilosToPounds(kilos: Float) = kilos * KILO_TO_POUND_COEF

    @JvmStatic
    fun kilosToPoundsRounded(kilos: Float) = Math.round(kilosToPounds(kilos))

    @JvmStatic
    fun poundsToTons(pounds: Int) = pounds * POUND_TO_TONS

    @JvmStatic
    fun kilosToTons(kilos: Int) = kilos * KILO_TO_TONS_COEF

    @JvmStatic
    fun kilosToTonsRounded(kilos: Int) = Math.round(kilosToTons(kilos))

    @JvmStatic
    fun metersToInches(meters: Float) = meters * METERS_TO_INCHES_COEF

    @JvmStatic
    fun metersToInchesRounded(meters: Float) = Math.round(metersToInches(meters))

    @JvmStatic
    fun inchesToMeters(inches: Int) = inches / METERS_TO_INCHES_COEF

    @JvmStatic
    fun metersToFeets(meters: Float) = meters * METERS_TO_FEET_COEF

    @JvmStatic
    fun metersToMiles(meters: Float) = meters * METERS_TO_MILES_COEF

    @JvmStatic
    fun milesToMeters(miles: Double) = miles / METERS_TO_MILES_COEF

    @JvmStatic
    fun inchesToInchesString(inches: Float) = String.format("%.0f\"", inches)

    @JvmStatic
    fun inchesToFeetInchesString(inches: Float) = String.format("%d'", (inches / FEET_TO_INCHES_COEF).toInt())
            .run {
                val restInches = inches % FEET_TO_INCHES_COEF
                if (restInches > 0) {
                    this + String.format("%.0f\"", restInches)
                } else {
                    this
                }
            }

    @JvmStatic
    fun inchesToFeet(inches: Int) = inches * INCHES_TO_FEET_COEF
}