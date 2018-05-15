package com.andreydubovets.app.extension

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.andreydubovets.testapp.R


fun Activity.launchPickImageIntent() {
    val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    pickIntent.type = INTENT_PICK_IMAGE_TYPE

    val chooserIntent = Intent.createChooser(pickIntent, getString(R.string.pick_image_intent_title))

    startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST)
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

private const val INTENT_PICK_IMAGE_TYPE = "image/*"

const val PICK_IMAGE_REQUEST = 1000
