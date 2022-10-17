package com.example.aifilteringsystem

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.*
import androidx.lifecycle.LiveData
import android.net.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.system.exitProcess

class NetworkConnection(private val context: Context) :
    ConnectivityManager.NetworkCallback() {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR) // 데이터 사용 관련 감지
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI) // 와이파이 사용 관련 감지
        .build()

    // 네트워크 연결 안 되어있을 때 보여줄 다이얼로그
    val dialog =
        AlertDialog.Builder(context)
            .setTitle("네트워크 연결 안 됨")
            .setMessage("와이파이 또는 모바일 데이터를 확인해주세요")
            .setPositiveButton("예", DialogInterface.OnClickListener { dialogInterface, i ->
                when (i) {
                    DialogInterface.BUTTON_POSITIVE ->
                        exitProcess(0)
                }
            })
            .setCancelable(false)
            .create()

    // NetworkCallback 등록
    @RequiresApi(Build.VERSION_CODES.N)
    fun register() {
        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                // 콜백이 등록되거나 네트워크가 연결되었을 때 실행되는 메소드
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (getConnectivityStatus() == null) {
                        // 네트워크 연결 안 되어 있을 때
                        dialog.show()
                        Log.d("testtest", "Alost")
                    } else {
                        // 네트워크 연결 되어 있을 때
                        dialog.dismiss()
                        Log.d("testtest", "dismiss")
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    dialog.show()
                    Log.d("testtest", "lost")
                }
            })
    }

    // NetworkCallback 해제
    fun unregister() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    // 현재 네트워크 상태 확인
    @RequiresApi(Build.VERSION_CODES.M)
    fun getConnectivityStatus(): Network? {
        // 연결된 네트워크가 없을 시 null 리턴
        return connectivityManager.activeNetwork
    }
}