package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.BaseStat.*
import com.rafaelmallare.highnoon.DerivedStat.*
import com.rafaelmallare.highnoon.EquipmentType.*
import com.rafaelmallare.highnoon.EquipSlot.*

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

    val emptyHead = Equipment("Empty Head", HELMET)
    val emptyBody = Equipment("Empty Body", ARMOR)
    val emptyHand = Equipment("Empty Hand", MELEE)
    val fullHand = Equipment("Full Hand", MELEE)

    val equipmentSlots = mutableMapOf<EquipSlot, Equipment>(
            HEAD to emptyHead, BODY to emptyBody, PRIMEHAND to emptyHand, OFFHAND to emptyHand
    )

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
        //TODO: Check if item is in inventory first
        if (item.equipSlot == PRIMEHAND && equipmentSlots[OFFHAND] == fullHand) equipmentSlots.put(OFFHAND, emptyHand)
        else if (item.isTwoHanded) equipmentSlots.put(OFFHAND, fullHand)
        equipmentSlots.put(item.equipSlot, item)

        derivedStats.addModifiers(item.name, item.modifiers)
    }

    fun unequipItem(item: Equipment): Boolean {
        val slot = item.equipSlot
        if (item != equipmentSlots[slot]) return false
        when(slot) {
            HEAD -> equipmentSlots.put(HEAD, emptyHead)
            BODY -> equipmentSlots.put(BODY, emptyBody)
            PRIMEHAND -> {
                if (equipmentSlots[OFFHAND] == item) equipmentSlots.put(OFFHAND, emptyHand)
                else {
                    if (item.isTwoHanded) equipmentSlots.put(OFFHAND, emptyHand)
                    equipmentSlots.put(PRIMEHAND, emptyHand)
                }
            }
        }

        derivedStats.removeModifiers(item.name)

        return true
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