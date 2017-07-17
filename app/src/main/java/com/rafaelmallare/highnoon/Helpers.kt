package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/16/2017.
 */

val Pair<Int, Int>.base
    get() = first

val Pair<Int, Int>.total
    get() = second

operator fun MutableMap<BaseStat, Int>.get(stat: BaseStat, defaultValue: Int): Int {
    return this[stat] ?: defaultValue
}