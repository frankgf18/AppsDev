package com.example.appsdev.Home.Mapas

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentMapasBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Mapas : BaseFragment<FragmentMapasBinding>(FragmentMapasBinding::inflate), OnMapReadyCallback{

    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
    }

    private fun init() {
        childFragmentManager.findFragmentById(R.id.map)
        (SupportMapFragment()).getMapAsync(this)

        val mapView = binding.map
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private val color1 = 0xff000000

    private fun events() {
        binding.btnRegistrar.setOnClickListener {
            medirDisctancia(map.myLocation)
        }
        binding.btnAlerta.setOnClickListener {

            SweetAlertDialog(requireActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE).apply {
                titleText = "Sweet!"
                contentText = "Here's a custom image."
                setCustomImage(R.drawable.perro)
                show();
            }
        }
    }

    private fun medirDisctancia(loc: Location) {

        direccion(loc)

        crearRuta(loc)

        val locationA = Location("punto A")
        locationA.latitude = ofiLati
        locationA.longitude = ofiLong

        val locationB = Location("punto B")
        locationB.latitude = loc.latitude
        locationB.longitude = loc.longitude

        val distance = locationA.distanceTo(locationB).toInt()
        binding.tvPrueba.text = "$distance metros de distancia."
    }

    private fun direccion(loc: Location) {
        val geocoder = Geocoder(requireContext())
        val test = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
        val direccion :Address = test[0]
        binding.tvDireccion.text = direccion.getAddressLine(0)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        //createMarker()
        ubicacionActual()
        marcadorOficina()
        /*map.setOnMyLocationClickListener {
            Toast.makeText(requireContext(), "${it.latitude}", Toast.LENGTH_SHORT).show()
        }*/
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(oficina, 18f), 3000, null)
    }

    private fun marcadorOficina() {
        val oficina = MarkerOptions().position(oficina).title("Oficina")
        map.addMarker(oficina)
        //map.addCircle(CircleOptions().center(LatLng(ofiLati, ofiLong)).radius(10.0).strokeColor(0xff2390DE.toInt()).fillColor(Color.TRANSPARENT))
        map.addCircle(CircleOptions().center(LatLng(ofiLati,ofiLong)).radius(30.0).strokeColor(0xff2390DE.toInt()))
    }

    private fun crearRuta(loc: Location) {
        val polyLineOptions = PolylineOptions()
            .add(LatLng(ofiLati,ofiLong))
            .add(LatLng(loc.latitude,loc.longitude))
            .width(15f)
            .color(ContextCompat.getColor(requireContext(),R.color.P1))

        val poly = map.addPolyline(polyLineOptions)

        poly.startCap = RoundCap()
        poly.endCap = RoundCap()//CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_camara))

        /*val pattern = listOf(
            Dot(),Gap(10f),Dash(50f),Gap(10f)
        )
        poly.pattern = pattern*/

        /*polyline.isClickable = true
        map.setOnPolylineClickListener {
            changeColor(it)
        }*/
    }

    val ofiLati = -12.056960738566376
    val ofiLong = -77.03764092167044

    private var oficina = LatLng(ofiLati, ofiLong)

    private fun ubicacionActual(){
        if (!::map.isInitialized) return
        if (verificarPermisos()){
            map.isMyLocationEnabled = true
        }else{
            pedirPermisos()
        }
    }
    private fun verificarPermisos(): Boolean = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private val REQUEST_CODE = 101
    private fun pedirPermisos(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
        }
    }
    @SuppressLint("MissingPermission", "MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    map.isMyLocationEnabled = true
                }else{
                    Toast.makeText(requireContext(), "Para activar la localizacion y acepta los permisos", Toast.LENGTH_SHORT).show()
                }
            }
            else->{
                Toast.makeText(requireContext(), "No aceptaste ningun permiso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!verificarPermisos()){
            map.isMyLocationEnabled = false
        }else{
            Toast.makeText(requireContext(), "vivo", Toast.LENGTH_SHORT).show()
        }
    }
}