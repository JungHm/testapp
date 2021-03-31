package com.example.testapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.testapp.apidata.BusStation
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var mView: MapView
    private lateinit var gpsPoint: MapPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mView = MapView(this@MainActivity)
        val mViewContainer = findViewById<RelativeLayout>(R.id.map_View)

        mViewContainer.addView(mView)
        mView.setZoomLevel(2, true);
        mView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        gpsPoint =  mView.mapCenterPoint //gps 좌표 받아오는것


        val api = BusAPI.create()

        api.getStationInfo().enqueue(object : Callback<BusStation> {
            override fun onResponse(call: Call<BusStation>, response: Response<BusStation>) {
                var station = response.body();
                Log.d("JUSTHM", "${response.body()}")

            }

            override fun onFailure(call: Call<BusStation>, t: Throwable) {
                Log.d("JUSTHM", "FAIL LOAD API")
            }
        })

    }


    fun setKakaoMapListener(){
        var mlistener : MapView.MapViewEventListener = object : MapView.MapViewEventListener{
            override fun onMapViewInitialized(p0: MapView?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

            override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun temp()
    {
        val locationManager by lazy {
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                p0?.let {
                    val position = LatLng(it.latitude, it.longitude)
                    Log.e("lat and long", "${position.latitude} and ${position.longitude}")
                    //Toast.makeText(this@MainActivity, "위도${position.latitude} 경도${position.longitude}",Toast.LENGTH_LONG).show()
//                    mView.setMapCenterPoint(
//                        MapPoint.mapPointWithGeoCoord(
//                            position.latitude,
//                            position.longitude
//                        ), true
//                    )
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this@MainActivity, "권한 허용 안햇자나 너.", Toast.LENGTH_LONG)
            return
        }
        locationManager.requestLocationUpdates( //3000ms 마다 1미터 마다 갱신한다는 뜻 딜레이마다 또는 위치값 판별을 통해 일정 미터단위 움직임이 발생했을때도 리스너가 호출된다.
            LocationManager.GPS_PROVIDER,
            3000,
            1f,
            locationListener
        ) // 사용하지 않을때는 removeUpdates(locationListener) 자원해제 필수

    }
}




