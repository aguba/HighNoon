package com.rafaelmallare.highnoon

/**
 * Created by Rj on 9/13/2017.
 */
object PlayerStats {
    var statSpread = 4

    private val baseStats = mutableMapOf(
            BaseStat.STR to 1, BaseStat.CON to 1, BaseStat.DEX to 1,
            BaseStat.PER to 1, BaseStat.CHR to 1, BaseStat.INT to 1)

    operator fun get(stat: BaseStat) = baseStats[stat]
    operator fun get(stat: DerivedStat) = derivedStats[stat]

    fun changeStatBy(stat: BaseStat, amount: Int): Boolean {
        val updatedValue = (baseStats[stat] ?: 0) + amount
        if (updatedValue < 1) return false

        val tmpMap = baseStats.filterKeys { key -> key.category == stat.category }.toMutableMap()
        tmpMap.put(stat, updatedValue + amount)

        if ((tmpMap.values.max() ?: 0) - (tmpMap.values.min() ?: 0) > statSpread) return false

        baseStats.put(stat, updatedValue)

        return true
    }

    fun addModifiers(sourceName: String, statModifiers: Map<DerivedStat, Int>) {
        for ((stat, value) in statModifiers) {
            derivedStats.modifierList.add(Triple(stat, value, sourceName))
        }
    }

    fun removeModifiers(sourceName: String) {
        derivedStats.modifierList.removeAll { mod -> mod.third == sourceName }
    }

    private object derivedStats {
        val modifierList = mutableListOf<Triple<DerivedStat, Int, String>>()

        operator fun get(derivedStat: DerivedStat): Pair<Int, Int> {
            val base: Int
            when (derivedStat) {
                DerivedStat.HP -> base = baseStats[BaseStat.STR, 0] + 2 * baseStats[BaseStat.CON, 0] + 10
                DerivedStat.INIT -> base = baseStats[BaseStat.DEX, 0] + baseStats[BaseStat.PER, 0]
                DerivedStat.DEF -> base = baseStats[BaseStat.DEX, 0] // + Card? + EvasionSkill? (ask Sean)
                DerivedStat.ATK -> base = baseStats[BaseStat.PER, 0] // + WepMastSkill
                DerivedStat.ARM -> base = 0
                DerivedStat.DMG -> base = baseStats[BaseStat.STR, 0]
                DerivedStat.WILL -> base = baseStats[BaseStat.INT, 0] + baseStats[BaseStat.CHR, 0] + 3
                DerivedStat.MP -> base = baseStats[BaseStat.INT, 0] + baseStats[BaseStat.CON, 0] + 10
                DerivedStat.SPD -> base = baseStats[BaseStat.DEX, 0] / 2 + 4
            }

            var total = 0
            val statMods = modifierList.filter { (stat, _, _) -> stat == derivedStat }
            statMods.forEach { mods -> total += mods.second }

            return Pair(base, total)
        }
    }
}