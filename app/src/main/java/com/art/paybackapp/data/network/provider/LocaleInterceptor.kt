package com.art.paybackapp.data.network.provider

import com.art.paybackapp.common_android.AppLocale
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LocaleInterceptor @Inject constructor(
    private val appLocale: AppLocale
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return languageAsQuery(chain)
    }

    private fun languageAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .url(
                chain.request().url.newBuilder()
                    .addQueryParameter("lang", appLocale.displayLanguage).build()
            )
            .build()
    )

}