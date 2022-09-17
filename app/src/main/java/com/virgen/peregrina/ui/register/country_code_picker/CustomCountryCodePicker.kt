package com.virgen.peregrina.ui.register.country_code_picker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.hbb20.CountryCodePicker
import com.virgen.peregrina.ui.register.RegisterActivity

class CustomCountryCodePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CountryCodePicker(context, attrs) {

    companion object {
        private const val TAG = "CustomCountryCodePicker"
    }

    override fun setOnCountryChangeListener(onCountryChangeListener: OnCountryChangeListener?) {
        super.setOnCountryChangeListener(onCountryChangeListener)
        Log.i(TAG, "setOnSelectedCountryListener()")
        Log.i(TAG, "PARAMS: code=$selectedCountryCode, name=$selectedCountryName")
    }

}