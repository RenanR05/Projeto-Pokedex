package org.pokedex.platform

interface Platform {
    val isAndroid: Boolean
    val isIos: Boolean
}

expect fun getPlatform(): Platform
