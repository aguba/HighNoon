package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/17/2017.
 */
open class Skill(val name: String, val parentStat1: BaseStat, val parentStat2: BaseStat? = null, val untrained: Boolean = false) {
    var lvl = -1
    val costToNextLvl
        get() = if (lvl < 10) lvl + 6 else 0

    var description = "Skill Description"

    fun lvlUp(): Boolean {
        if (lvl >= 10) return false

        PlayerManager.exp.changeBy(-costToNextLvl)
        lvl++
        return true
    }
}

class ClassSkill(name: String, parentStat1: BaseStat, parentStat2: BaseStat? = null,
                 untrained: Boolean = false, val requiredClasses: List<String> = listOf<String>())
    : Skill(name, parentStat1, parentStat2, untrained)