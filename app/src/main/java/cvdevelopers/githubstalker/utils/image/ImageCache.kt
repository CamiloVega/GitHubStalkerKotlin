package cvdevelopers.githubstalker.utils.image

import android.graphics.Bitmap

import com.squareup.picasso.Cache

import java.util.LinkedHashMap

class ImageCache : Cache {
    override fun clearKeyUri(keyPrefix: String?) {
        cacheMap.keys
                .filter { it.contains(keyPrefix as CharSequence)}
                .forEach { cacheMap.remove(it) }
    }

    private val cacheMap = LinkedHashMap<String, Bitmap>()

    override fun get(stringResource: String): Bitmap? {
        return cacheMap[stringResource]
    }

    override fun set(stringResource: String, bitmap: Bitmap) {
        cacheMap.put(stringResource, bitmap)
    }

    override fun size(): Int {
        return cacheMap.size
    }

    override fun maxSize(): Int {
        return Integer.MAX_VALUE
    }

    override fun clear() {
        cacheMap.clear()
    }
}
