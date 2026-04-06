package org.pokedex.platform

class AndroidPlatform : Platform {
    override val isAndroid: Boolean = true
    override val isIos: Boolean = false
}

actual fun getPlatform(): Platform = AndroidPlatform()
