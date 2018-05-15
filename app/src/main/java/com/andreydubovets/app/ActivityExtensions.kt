package com.andreydubovets.app

import android.app.Activity
import android.content.Intent
import com.andreydubovets.testapp.R


fun Activity.launchPickImageIntent() {
    val getIntent = Intent(Intent.ACTION_GET_CONTENT)
    getIntent.type = INTENT_PICK_IMAGE_TYPE

    val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    pickIntent.type = INTENT_PICK_IMAGE_TYPE

    val chooserIntent = Intent.createChooser(getIntent, getString(R.string.pick_image_intent_title))
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

    startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST)
}

private const val INTENT_PICK_IMAGE_TYPE = "image/*"

const val PICK_IMAGE_REQUEST = 1000
