package com.art.paybackapp.common_android

import android.annotation.TargetApi
import android.content.res.Resources
import android.os.Build
import java.util.Locale

class AppLocale{

    val displayLanguage: String = getLocale()

    private fun getLocale(): String {
        return when {
            Build.VERSION.SDK_INT < 24 -> {
                getDefaultLocaleApi()
            }
            else -> {
                getLocaleApi24()
            }
        }

    }

    private fun getDefaultLocaleApi(): String = Locale.getDefault().language

    @TargetApi(24)
    private fun getLocaleApi24(): String = Resources.getSystem().configuration.locales.toLanguageTags().substringBefore('-')

}