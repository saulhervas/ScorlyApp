package com.saulhervas.scorlyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform