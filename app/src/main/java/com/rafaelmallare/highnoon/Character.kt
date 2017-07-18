package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.BaseStat.*
import com.rafaelmallare.highnoon.DerivedStat.*

/**
 * Created by Rj on 7/16/2017.
 */
object Character {
    var name = "Character Name"
    var faction = "Placeholder: Faction"
    var classes = "Placeholder: Classes"

    var statSpread = 4

    val baseStats = mutableMapOf(
            STR to 1, CON to 1, DEX to 1,
            PER to 1, CHR to 1, INT to 1)

    fun changeStatBy(stat: BaseStat, amount: Int): Boolean {
        val updatedValue = (baseStats[stat] ?: 0) + amount
        if (updatedValue < 1) return false

        val tmpMap = baseStats.filterKeys { key -> key.category == stat.category }.toMutableMap()
        tmpMap.put(stat, updatedValue + amount)

        if ((tmpMap.values.max() ?: 0) - (tmpMap.values.min() ?: 0) > statSpread) return false

        baseStats.put(stat, updatedValue)

        return true
    }

    fun equipItem(item: Equipment) {
        //TODO: Equip
        derivedStats.addModifiers(item.name, item.modifiers)
    }

    fun unequipItem(item: Equipment) {
        //TODO: Unequip
        derivedStats.removeModifiers(item.name)
    }

    private object derivedStats {
        val modifierList = mutableListOf<Triple<DerivedStat, Int, String>>()

        operator fun get(derivedStat: DerivedStat): Pair<Int, Int> {
            val base: Int
            when (derivedStat) {
                HP -> base = baseStats[STR, 0] + 2 * baseStats[CON, 0] + 10
                INIT -> base = baseStats[DEX, 0] + baseStats[PER, 0]
                DEF -> base = baseStats[DEX, 0] // + Card? + EvasionSkill? (ask Sean)
                ATK -> base = baseStats[PER, 0] // + WepMastSkill
                ARM -> base = 0
                DMG -> base = baseStats[STR, 0]
                WILL -> base = baseStats[INT, 0] + baseStats[CHR, 0] + 3
                MP -> base = baseStats[INT, 0] + baseStats[CON, 0] + 10
                SPD -> base = baseStats[DEX, 0] / 2 + 4
            }

            var total = 0
            val statMods = modifierList.filter { mods -> mods.first == derivedStat }
            statMods.forEach { mods -> total += mods.second }

            return Pair(base, total)
        }

        fun addModifiers(sourceName: String, statModifiers: Map<DerivedStat, Int>) {
            for ((stat, value) in statModifiers) {
                modifierList.add(Triple(stat, value, sourceName))
            }
        }

        fun removeModifiers(sourceName: String) {
            modifierList.removeAll { mod -> mod.third == sourceName }
        }
    }
}