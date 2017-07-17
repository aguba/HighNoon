package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.BaseStatType.*
import com.rafaelmallare.highnoon.DerivedStatType.*

/**
 * Created by Rj on 7/16/2017.
 */
object Character {
    var name = "Character Name"
    var faction = "Placeholder: Faction"
    var classes = "Placeholder: Classes"

    var statDiffCap = 4

    val stats = mutableMapOf(
            STR to 1, CON to 1, DEX to 1,
            PER to 1, CHR to 1, INT to 1)

    fun changeStatBy(stat: BaseStatType, amount: Int): Boolean {
        val updatedValue = (stats[stat] ?: 0) + amount
        if (updatedValue < 1) return false

        val tmpMap = stats.filterKeys { key -> key.category == stat.category }.toMutableMap()
        tmpMap.put(stat, updatedValue + amount)

        if ((tmpMap.values.max() ?: 0) - (tmpMap.values.min() ?: 0) > statDiffCap) return false

        stats.put(stat, updatedValue)

        return true
    }
}