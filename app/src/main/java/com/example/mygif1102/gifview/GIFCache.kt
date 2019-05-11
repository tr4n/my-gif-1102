package com.example.mygif1102.gifview

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.renderscript.ScriptGroup

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 *
 * The GIFCache class takes care of downloading and caching of GIF files.
 *
 *
 * All downloaded files are stored in the implementing app's own cache-directory, under a
 * sub-directory called "GIFView". The exact path can be retrieved using the method
 * [.getCacheSubDir], which will return a [File] object representing that
 * directory.
 *
 */
class GIFCache
/**
 *
 * Creates a new GIFCache object for GIF images defined by the given `url`.
 *
 * @param context The [Context] is required to access the app's cache directory
 * @param url The URL that points to the GIF image.
 */
internal constructor(context: Context, private val url: String) {
    private val cachedGIF: File

    /**
     *
     * Returns the InputStream to the cached GIF image file.
     *
     * If the GIF is not cached, it will be downloded automatically. The InputStream returned
     * is always the InputStream to the cached file.
     *
     * @return The InputStream to the cached file.
     *
     * @throws IOException Thrown if an I/O error occurs.
     */
//    internal val inputStream: InputStream
//        @Throws(IOException::class)
//        get() {
//            if (!cachedGIF.exists()) {
//                downloadGIF()
//            }
//            return openCachedGIFInputStream()
//        }

    internal fun getInputStream(onDataLoaded: (inputStream: InputStream) -> Unit){
        GIFDownloadTask(onDataLoaded).execute(url)
    }
    init {
        try {
            if (!getCacheSubDir(context).exists()) {
                if (!cacheSubDir!!.mkdirs()) {
                    throw IOException("Cannot create directory " + cacheSubDir!!.absolutePath)
                }
            }
            cachedGIF = File(cacheSubDir!!.absolutePath + File.separator + bytesToHex(computeHash(url)))
        } catch (e: Exception) {
            throw IllegalStateException(e) as Throwable
        }
    }

    /**
     *
     * Download the GIF image into the cache.
     *
     * @throws IOException Thrown in case of an I/O Error.
     */
    @Throws(IOException::class)
    private fun downloadGIF() {
        var bis: InputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = URL(url).content as InputStream
            bos = BufferedOutputStream(FileOutputStream(cachedGIF))

            val bytes = ByteArray(BUFFER_SIZE)
            var bytesRead: Int
            var hasBytes: Boolean
            do {
                bytesRead = bis.read(bytes)
                hasBytes = bytesRead != -1
                if (hasBytes) {
                    bos.write(bytes, 0, bytesRead)
                }
            } while (hasBytes)

            bos.flush()
        } finally {
            bis?.close()
            bos?.close()
        }
    }

    /**
     *
     * Returns a freshly opened InputStream for the cached file.
     *
     * @return A freshly opened [InputStream] for the cached GIF file.
     * @throws FileNotFoundException Thrown in case the GIF file cannot be found.
     */
    @Throws(FileNotFoundException::class)
    private fun openCachedGIFInputStream(): InputStream {
        return BufferedInputStream(FileInputStream(cachedGIF))
    }

    companion object {

        private val CACHE_SUB_DIR = File.separator + "GIFView"
        private val HASH_MD5 = "MD5"
        private val BUFFER_SIZE = 65536
        private var cacheSubDir: File? = null

        /**
         *
         * Compute an MD5 hash for the given `text`.
         *
         *
         * Please note that the two exceptions thrown by this method are theoretically possible,
         * but very unlikely in most cases.
         *
         * @param text The text to calculate the hash from.
         * @return The MD5 hash of the given `text` as a hexadecimal [String].
         *
         * @throws NoSuchAlgorithmException Thrown in case the MF5 algorithm cannot be found
         * @throws UnsupportedEncodingException Thrown if the encoding of `text` is not
         * supported.
         */
        @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
        private fun computeHash(text: String): ByteArray {
            val md = MessageDigest.getInstance(HASH_MD5)
            md.update(text.toByteArray(charset("UTF-8")), 0, text.length)
            return md.digest()
        }

        /**
         * Convert an array of arbitrary bytes into a String of hexadecimal number-pairs with each pair representing on byte
         * of the array.
         *
         * @param bytes the array to convert into hexadecimal string
         * @return the String containing the hexadecimal representation of the array
         */
        fun bytesToHex(bytes: ByteArray): String {
            val result = StringBuffer()
            bytes.forEach{
                result.append(String.format("%02X", it))
            }
            return result.toString()
        }

        /**
         * Returns a [File] object representing the directory within the app's cache-area under
         * which this [GIFCache] object stores all cached files.
         *
         * @return THe [File] object representing the cache sub-directory.
         */
        fun getCacheSubDir(context: Context): File {
            if (cacheSubDir == null) {
                cacheSubDir = File(context.cacheDir.toString() + CACHE_SUB_DIR)
            }
            return cacheSubDir as File
        }
    }

    inner class GIFDownloadTask(
        private val onDataLoaded: (inputStream: InputStream) -> Unit
    ) : AsyncTask<String, Void?, Boolean>() {

        override fun doInBackground(vararg params: String?): Boolean {
            if(!cachedGIF.exists()){
                val urlString = params[0]
                var bis: InputStream? = null
                var bos: BufferedOutputStream? = null

                try {
                    bis = URL(urlString).content as InputStream
                    bos = BufferedOutputStream(FileOutputStream(cachedGIF))

                    val bytes = ByteArray(GIFCache.BUFFER_SIZE)
                    var bytesRead: Int
                    var hasBytes: Boolean
                    do {
                        bytesRead = bis.read(bytes)
                        hasBytes = bytesRead != -1
                        if (hasBytes) {
                            bos.write(bytes, 0, bytesRead)
                        }
                    } while (hasBytes)

                    bos.flush()
                } finally {
                    bis?.close()
                    bos?.close()
                }
                return true
            }
            return false
        }

        override fun onPostExecute(result: Boolean) {
            onDataLoaded(openCachedGIFInputStream())
        }
    }
}
