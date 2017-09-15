package com.rafaelmallare.highnoon

/**
 * Created by Rj on 9/14/2017.
 */
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
            if ((PlayerSkills[name].lvl) < lvl) return false
        }

        abilityList.add(ability)

        return true
    }
}