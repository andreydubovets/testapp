package com.andreydubovets.testapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.andreydubovets.testapp.imagelist.ImageListAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var imageList: RecyclerView
    private lateinit var addImageButton: Button

    private val fileManager: FileManager = FileManager()
    private val imageListAdapter = ImageListAdapter(emptyList())
    private val storagePermissionsManager: StoragePermissionsManager = StoragePermissionsManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        storagePermissionsManager.requestPermissionsIfNeeded({
            fileManager.createQulixFolder()
            initList()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> data?.let { handleImageSuccess(it.data) }
                else -> {
                }
            }
        }
    }

    private fun handleImageSuccess(uri: Uri) {
        fileManager.copyImageAndChangeData(this, uri)
        updateImageList()
    }


    private fun initViews() {
        imageList = findViewById(R.id.recyclerview_images)
        addImageButton = findViewById(R.id.button_add_image)

        addImageButton.setOnClickListener { launchPickImageIntent() }
    }

    private fun initList() {
        imageList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        imageList.adapter = imageListAdapter
        updateImageList()
    }

    private fun updateImageList() {
        val uris = fileManager.getUriListFromQulixFolder()
        imageListAdapter.setData(uris)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        storagePermissionsManager.onRequestPermissionsResult(grantResults)
    }
}
