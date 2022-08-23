package com.cren90.kotlin.common.extensions

import java.io.*

@Suppress("unused")
inline fun <reified T: Any> T.deepCopy(): T? {
    var oos: ObjectOutputStream? = null
    var ois: ObjectInputStream? = null

    try {
        val baos = ByteArrayOutputStream()
        oos = ObjectOutputStream(baos)
        oos.writeObject(this)
        oos.flush()

        val bais = ByteArrayInputStream(baos.toByteArray())
        ois = ObjectInputStream(bais)
        return ois.readObject() as? T

    } catch (e: Exception) {
        return null

    } finally {
        try {
            oos?.close()
            ois?.close()

        } catch (e: IOException) {
            //TODO: Move to using whatever logger we end up with
            e.printStackTrace()
        }

    }
}