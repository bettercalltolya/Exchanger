package com.bettercalltolya.common.core

import java.time.Instant

object CurrentTime {

    fun epochSeconds(): Long = Instant.now().epochSecond
}
