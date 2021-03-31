package com.example.testapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.*
import android.util.Log


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(this@SplashActivity, "권한허용.", Toast.LENGTH_LONG).show()
                SplashEnd()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                Toast.makeText(this@SplashActivity, "권한허용을 하지 않으면 이용할 수 없습니다.", Toast.LENGTH_LONG)
                    .show()
                finish()
            }
        }

        TedPermission.with(this@SplashActivity)
            .setPermissionListener(permissionlistener)
            //.setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
            .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다...\n [설정] > [권한] 에서 사용으로 활성화해주세요.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
            ).check()


    }

    private fun SplashEnd() {
        if (TedPermission.isGranted(this@SplashActivity)) {
            Log.d("JUSTHM", "SPLASH END")
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        } else {
            Log.d("JUSTHM", "IS DENIED")
            moveTaskToBack(true);                        // 태스크를 백그라운드로 이동
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask()
            };                        // 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid());    // 앱 프로세스 종료
        }
    }
}