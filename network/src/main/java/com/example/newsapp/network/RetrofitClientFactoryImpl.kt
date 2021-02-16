package com.example.newsapp.network


class RetrofitClientFactoryImpl: RetrofitClientFactory {

    override fun <T> createClient(api: Class<T>): T =
        RetrofitClientProvider
            .provideRetrofitApiByInterface(api, BuildConfig.BASE_URL)


    override fun <T> createAuthenticatedClient(api: Class<T>): T =
        RetrofitClientProvider
            .provideRetrofitApiAuthenticatedByInterface(api, BuildConfig.BASE_URL)

}
