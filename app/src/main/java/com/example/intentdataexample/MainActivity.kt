package com.example.intentdataexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.intentdataexample.databinding.ActivityMainBinding
import com.example.intentdataexample.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var requestCallPhoneAccepted = false
    private var requestCameraAccepted = false
    private var requestReadContacts = false

    /**
     * Inicializa la actividad, configura la interfaz de usuario
     * y solicita los permisos necesarios para la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        setupButton()
        requestPermissions()
    }

    /**
     * Método que configura el spinner: elementos que contiene y eventos
     * que se lanzan al seleccionar un elemento
     */
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.intents, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spTypeIntent.apply {
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    toast("Se ha seleccionado un elemento")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    toast("Inicio del spinner")
                }
            }
        }
    }
    /**
     * Configura el spinner con opciones de intents.
     */
    private fun setupButton() {
        binding.btSentIntent.setOnClickListener {
            onIntentSelected(binding.spTypeIntent.selectedItemPosition)
        }
    }

    /**
     * Solicita los permisos requeridos al usuario en un array de permisos.
     * Se utiliza un código de solicitud global para los permisos.
     */
    private fun requestPermissions() {
        val perms = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS
        )
        requestPermissions(perms, PERMISSION_REQUEST_CODE)
    }

    /**
     * Maneja la selección de intents basado en la posición seleccionada en el spinner.
     * @param position La posición del ítem seleccionado en el spinner.
     */
    private fun onIntentSelected(position: Int) {
        when (position) {
            0 -> launchBrowser("http://www.moronlu18.com/wordpress")
            1 -> makePhoneCall("tel:(+34)123456789")
            2 -> showDial("tel:(+34)608033422")
            3 -> showMap("geo:36.720443,-4.419311?z=19")
            4 -> searchMap("geo:0,0?q=PC Box Arango")
            5 -> captureImage()
            6 -> viewContacts()
            7 -> editFirstContact()
        }
    }

    //region: Métodos para lanzan  intent implícitos
    private fun launchBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun makePhoneCall(phone: String) {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(phone)))
        } else requestPermissions(
            arrayOf(Manifest.permission.CALL_PHONE),
            PERMISSIONS_REQUEST_CALL_PHONE
        )
    }

    private fun showDial(phone: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))
    }

    private fun showMap(geoUri: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)))
    }

    private fun searchMap(query: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(query)))
    }

    private fun captureImage() {
        if (requestCameraAccepted) {
            startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_CAMERA)
        }
    }

    private fun viewContacts() {
        if (requestReadContacts) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/")))
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        }
    }

    private fun editFirstContact() {
        if (requestReadContacts) {
            startActivity(Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1")))
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        }
    }

    //endregion

    /**
     * Método que maneja las respuestas para un conjunto de permisos solicitados conjuntamente
     * (CALL_PHONE, CAMERA, y READ_CONTACTS).
     *
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty()) {
                requestCallPhoneAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                requestCameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                requestReadContacts = grantResults[2] == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    /**
     * Entero que ayuda a identificar la solicitud del permiso.
     * Este código se podría utilizar en la respuesta de la solicitud de permiso.
     */
    companion object {
        private const val PERMISSIONS_REQUEST_CALL_PHONE = 1
        private const val PERMISSIONS_REQUEST_CAMERA = 2
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 3

        //Código de solicitud global de los permisos
        private const val PERMISSION_REQUEST_CODE = 300
    }
}
