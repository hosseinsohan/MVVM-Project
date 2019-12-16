package com.sohan.hossein.mvvmproject.data.provider

import com.sohan.hossein.mvvmproject.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}