package com.imadev.plantindentifier.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.google.firebase.storage.FirebaseStorage
import com.imadev.plantindentifier.databinding.FragmentHomeBinding
import com.imadev.plantindentifier.ui.BaseFragmentBinding
import com.imadev.plantindentifier.utils.Constants.REQUEST_CAMERA
import com.imadev.plantindentifier.utils.Constants.REQUEST_GALLERY
import com.imadev.plantindentifier.utils.Resource
import com.imadev.plantindentifier.utils.toUri
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {


    private val viewModel by viewModels<HomeViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.scanBtn.setOnClickListener {
            showImportDialog(requireContext())
        }


        viewModel.predictionsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> TODO()
                Resource.Loading -> TODO()
                is Resource.Success -> TODO()
            }
        }


    }


    private fun showImportDialog(context: Context) {
        val items = arrayOf("Gallery", "Camera")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Import from")
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    // Import from gallery
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, REQUEST_GALLERY)
                }
                1 -> {
                    // Import from camera
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, REQUEST_CAMERA)
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    // Get the selected image from the gallery
                    val selectedImageUri = data?.data
                    uploadImageToFirebaseStorage(selectedImageUri)
                }
                REQUEST_CAMERA -> {
                    // Get the taken photo
                    val imageBitmap = data?.extras?.get("data") as Bitmap

                    imageBitmap.toUri(requireContext())?.let {
                        uploadImageToFirebaseStorage(it)
                    }

                }
            }
        }
    }


    private fun uploadImageToFirebaseStorage(uri: Uri?) {
        if (uri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("images/$filename")
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "uploadImageToFirebaseStorage: downloadUrl $it")
                    viewModel.predict(it.toString())
                }
            }.addOnFailureListener {
                Log.d(TAG, "Error ${it.message}")

                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }


}