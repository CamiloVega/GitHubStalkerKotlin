package cvdevelopers.githubstalker.utils.image

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLoader @Inject constructor(context: Context) {

    private val picasso = Picasso.Builder(context).build()

    fun loadCircularImage (url: String, imageView: ImageView) {
        val rc = picasso.load(url)
        rc.transform(CircleTransformation())
        rc.into(imageView)
    }
}