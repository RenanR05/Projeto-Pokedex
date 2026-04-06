package org.pokedex.platform

class IosPlatform : Platform {
    override val name: String = "iOS"
    override val isAndroid: Boolean = false
    override val isIos: Boolean = true
}

actual fun getPlatform(): Platform = IosPlatform()
