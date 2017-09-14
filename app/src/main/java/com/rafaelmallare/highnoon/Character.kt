package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.BaseStat.*
import com.rafaelmallare.highnoon.DerivedStat.*
import com.rafaelmallare.highnoon.EquipmentType.*
import com.rafaelmallare.highnoon.EquipmentSubType.*
import com.rafaelmallare.highnoon.EquipSlot.*

/**
 * Created by Rj on 7/16/2017.
 */
object Character {
    var name = "Character Name"
    val faction = "Placeholder: Faction"
    val classes = mutableListOf<CharacterClass>()

    object exp {
        private var _current = 0
        private var _total = 0

        val current
            get() = _current
        val total
            get() = _total

        fun changeBy(amount: Int) {
            if (amount >= 0) _total += amount
            _current += amount
        }
    }

    object abilities {
        private val abilityList = mutableListOf<Ability>()

        private val abilityNames: List<String>
            get() {
                val list = mutableListOf<String>()
                abilityList.forEach { (name) -> list.add(name) }
                return list
            }

        operator fun get(abilityName: String): Ability? {
            return abilityList.find { (name) -> name == abilityName }
        }

        fun add(ability: Ability): Boolean {
            if (!abilityNames.containsAll(ability.requiredAbilities)) return false
            ability.requiredSkills.forEach { (name, lvl) ->
//                if ((skills[name]?.lvl ?: -1) < lvl) return false
                if ((skills.general[name]?.lvl ?: -1) < lvl) return false
            }

            abilityList.add(ability)

            return true
        }
    }

    var statSpread = 4

    val baseStats = mutableMapOf(
            STR to 1, CON to 1, DEX to 1,
            PER to 1, CHR to 1, INT to 1)

    object skills {
        val general = mutableMapOf<String, Skill>()
        val combat = mutableMapOf(
                "Aim" to Skill("Aim", PER),
                "Block" to Skill("Block", CON),
                "Evasion" to Skill("Evasion", DEX),
                "Initiative" to Skill("Initiative", DEX, PER)
        )
        val mastery = mutableMapOf(
                UNARMED to Skill("Unarmed Mastery", PER),
                ONEHAND to Skill("One-Handed Mastery", PER),
                TWOHAND to Skill("Two-Handed Mastery", PER),
                HALFHAND to Skill("Hand-and-a-Half Mastery", PER),
                LONG to Skill("Long Weapon Mastery", PER),
                THROWN to Skill("Thrown Mastery", PER),
                PISTOL to Skill("Pistol Mastery", PER),
                RIFLE to Skill("Rifle Mastery", PER),
                SHOTGUN to Skill("Shotgun Mastery", PER),
                ARCHERY to Skill("Archery Mastery", PER)
        )
    }

//    val skills = mutableMapOf<String, Skill>()
//
//    val combatSkills = mutableMapOf(
//            "Aim" to Skill("Aim", PER),
//            "Block" to Skill("Block", CON),
//            "Evasion" to Skill("Evasion", DEX),
//            "Initiative" to Skill("Initiative", DEX, PER)
//    )

//    val masterySkills = mutableMapOf(
//            UNARMED to Skill("Unarmed Mastery", PER),
//            ONEHAND to Skill("One-Handed Mastery", PER),
//            TWOHAND to Skill("Two-Handed Mastery", PER),
//            HALFHAND to Skill("Hand-and-a-Half Mastery", PER),
//            LONG to Skill("Long Weapon Mastery", PER),
//            THROWN to Skill("Thrown Mastery", PER),
//            PISTOL to Skill("Pistol Mastery", PER),
//            RIFLE to Skill("Rifle Mastery", PER),
//            SHOTGUN to Skill("Shotgun Mastery", PER),
//            ARCHERY to Skill("Archery Mastery", PER)
//    )

    val inventory = mutableListOf<Equipment>()

    private val emptyHead = Equipment("Empty Head", HELMET)
    private val emptyBody = Equipment("Empty Body", ARMOR)
    private val emptyHand = Equipment("Empty Hand", MELEE)
    private val fullHand = Equipment("Full Hand", MELEE)

    val equipmentSlots = mutableMapOf(
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

//    fun lvlUpSkill(skillName: String) {
//        if (skills.containsKey(skillName)) {
//            val lvlCost = -(skills[skillName]?.costToNextLvl ?: 0)
//            if (skills[skillName]?.lvlUp() ?: false) exp.changeBy(lvlCost)
//        } else {
//            val lvlCost = -(combatSkills[skillName]?.costToNextLvl ?: 0)
//            if (combatSkills[skillName]?.lvlUp() ?: false) exp.changeBy(lvlCost)
//        }
//    }
//
//    fun lvlUpSkill(skillName: EquipmentSubType) {
//        val lvlCost = -(masterySkills[skillName]?.costToNextLvl ?: 0)
//        if (masterySkills[skillName]?.lvlUp() ?: false) exp.changeBy(lvlCost)
//    }

    fun equipItem(item: Equipment, inOffhand: Boolean = false): Boolean {
        if (item !in inventory) return false

        if (item.isTwoHanded) {
            equipmentSlots.put(OFFHAND, fullHand)
            equipmentSlots.put(item.equipSlot, item)
        } else if (item.isWeapon && equipmentSlots[OFFHAND] === fullHand) {
            equipmentSlots.put(OFFHAND, emptyHand)
            equipmentSlots.put(item.equipSlot, item)
        } else if (item.isWeapon && inOffhand) {
            item.isOffhand = true
            equipmentSlots.put(OFFHAND, item)
        } else equipmentSlots.put(item.equipSlot, item)

        derivedStats.addModifiers(item.name, item.modifiers)

        return true
    }

    fun unequipItem(item: Equipment): Boolean {
        val slot = item.equipSlot
        if (item !== equipmentSlots[slot]) return false
        when(slot) {
            HEAD -> equipmentSlots.put(HEAD, emptyHead)
            BODY -> equipmentSlots.put(BODY, emptyBody)
            PRIMEHAND -> {
                if (item.isTwoHanded) equipmentSlots.put(OFFHAND, emptyHand)
                equipmentSlots.put(PRIMEHAND, emptyHand)
            }
            OFFHAND -> {
                if (item.isTwoHanded) equipmentSlots.put(PRIMEHAND, emptyHand)
                item.isOffhand = false
                equipmentSlots.put(OFFHAND, emptyHand)
            }
        }

        derivedStats.removeModifiers(item.name)

        return true
    }

    object derivedStats {
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
            val statMods = modifierList.filter { (stat, _, _) -> stat == derivedStat }
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