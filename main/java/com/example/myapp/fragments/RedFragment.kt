package com.example.myapp.fragments


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.NameAlertDialogBinding
import com.example.myapp.databinding.PicCellFragmentBinding
import com.example.myapp.databinding.RedFragmentBinding
import com.example.myapp.models.PicsList
import com.example.myapp.utils.PicListAdapter
import com.example.myapp.utils.UserClick2
import com.example.myapp.viewModel.WelcomeViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder


@AndroidEntryPoint
class RedFragment : Fragment(), UserClick2{

    private lateinit var binding: RedFragmentBinding

    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    private val exampleList = ArrayList<PicsList>()
    private var adapter = PicListAdapter(exampleList,this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate<RedFragmentBinding>(
            inflater,
            R.layout.red_fragment, container, false
        )

        binding.picListRecycler.adapter = adapter
        binding.picListRecycler.layoutManager = LinearLayoutManager(context)
        initView()
        return binding.root
    }

    private fun initView() {

        val popupMenu = PopupMenu(
            context,
            binding.addPic
        )

        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")

        popupMenu.setOnMenuItemClickListener { menuItem ->

            val id = menuItem.itemId

            if (id == 0) {
                cameraCheckPermission()
            } else if (id == 1) {
                galleryCheckPermission()
            }
            false

        }

        binding.addPic.setOnClickListener {
            popupMenu.show()
        }

    }

    private fun cameraCheckPermission() {

        Dexter.withContext(context)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }


                }
            ).onSameThread().check()
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun galleryCheckPermission() {

        Dexter.withContext(context).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    context,
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                )
                    .show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: com.karumi.dexter.listener.PermissionRequest?,
                p1: PermissionToken?
            ) {
                showRotationalDialogForPermission()
            }

        }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(context)
            .setMessage(
                "It looks like you have turned off permissions"
                        + "required for this feature. It can be enable under App settings!!!"
            )

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", parentFragment.toString(), null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap
                    val uri = context?.let { getImageUriFromBitmap(it,bitmap) }
                    var picNameHolder: String
                    val builder = AlertDialog.Builder(context)
                    val inflater = layoutInflater
                    val dialogLayout = inflater.inflate(R.layout.name_alert_dialog, null)
                    val editText = dialogLayout.findViewById<EditText>(R.id.userPicName)


                    with(builder) {
                        setTitle("Enter the picture's name")
                        setPositiveButton("Ok") { _, _ ->
                            picNameHolder = editText.text.toString()
                            val newItem = uri?.let {
                                PicsList(
                                    picNameHolder, it
                                )
                            }
                            val index = 0
                            if (newItem != null) {
                                exampleList.add(index, newItem)
                            }
                            adapter.notifyItemInserted(index)

                        }
                        setNegativeButton("Cancel") { _, _ ->
                            Log.d("Main", "Negative Button has been clicked")
                        }

                        setView(dialogLayout)
                        show()

                    }
                }

                GALLERY_REQUEST_CODE -> {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context?.getContentResolver(), data?.data)
                    val uri = context?.let { getImageUriFromBitmap(it,bitmap) }
                    var picNameHolder: String
                    val builder = AlertDialog.Builder(context)
                    val inflater = layoutInflater
                    val dialogLayout = inflater.inflate(R.layout.name_alert_dialog, null)
                    val editText = dialogLayout.findViewById<EditText>(R.id.userPicName)

                    with(builder) {
                        setTitle("Picutre Name")
                        setPositiveButton("Ok") { dialog, which ->
                            picNameHolder = editText.text.toString()
                            val newItem = PicsList(
                                picNameHolder, uri
                            )
                            val index = 0
                            exampleList.add(index, newItem)
                            adapter.notifyItemInserted(index)

                        }
                        setNegativeButton("Cancel") { dialog, which ->
                            Log.d("Main", "Nehative Button has been clicked")
                        }

                        setView(dialogLayout)
                        show()

                    }

                }
            }

        }

    }

    companion object {
        //Shared variable
        const val TAG_KEY = "TAG_KEY"
        fun newInstance(): RedFragment = RedFragment()
    }

    override fun onClick(Picture: PicsList) {
        val action = BaseFragmentDirections.actionBaseFragmentToPictureDetailsFragment(Picture)
        findNavController().navigate(action)
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }


}


