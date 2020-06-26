package com.app.livefree.util

import android.graphics.Bitmap
import androidx.fragment.app.FragmentManager
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType


/**
 * Created by root on 3/12/19.
 */
object PickImageUtils {

    fun imagePick(fragmentManager: FragmentManager, returnImageUri: (bitmap: Bitmap) -> Unit) {
        val pickSetup = PickSetup()
                .setTitle("Choose")
                .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                .setSystemDialog(true)

        PickImageDialog.build(pickSetup).setOnPickResult { result ->
         /*   var uri = Uri.parse(result.path)
            Log.d("Uri:", uri.toString())*/
            returnImageUri(result.bitmap)
        }.show(fragmentManager)
    }
}