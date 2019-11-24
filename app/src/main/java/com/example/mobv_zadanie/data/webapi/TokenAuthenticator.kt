package com.example.mobv_zadanie.data.webapi

import android.util.Log
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route

class TokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        Log.i("TAG_API", "server response: "+response.code())

        if (response.request().header("OpinAuth")?.compareTo("accept")==0
                && response.code()==401)
        {
            //todo call refresh API for token
            /*
            val userAccessToken = //get stored user accessToken

                if (!response.request().header("Authorization").equals("Bearer $userAccessToken")){
                    return null
                }

            val refreshToken= //get stored user refreshToken

            val tokenResponse = ZadanieApi.create().tokenRefreshCall(RefreshTokenRequest(user_id,refreshToken)).execute()

            if (tokenResponse.isSuccessful){
                userAccessToken=tokenResponse.body()!!.accessToken
                uloz(tokenResponse.body()!!.accessToken) // store new access token
                uloz(tokenResponse.body()!!.refreshToken) // store new refresh token

                return response.request().newBuilder()
                    .header("Authorization","Bearer $userAccessToken")
                    .build()
            }
            */


        }
        return null
    }
}