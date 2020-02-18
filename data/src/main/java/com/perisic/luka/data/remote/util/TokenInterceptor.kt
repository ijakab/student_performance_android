package com.perisic.luka.data.remote.util

import com.perisic.luka.data.local.dao.TokenModelDao
import com.perisic.luka.data.local.model.Token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    tokenDao: TokenModelDao
) : Interceptor {

    private var token: Token? = tokenDao.fetchTokenSync()

    init {
        tokenDao.fetchTokenAsync().observeForever {
            it?.let {
                token = it
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().apply {
            token?.token?.let {
                addHeader("Authorization", "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}