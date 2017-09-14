package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/23/2017.
 */

data class Ability(
        val name: String, val expCost: Int, val isActive: Boolean, val requiredAbilities: MutableList<String> = mutableListOf(),
        val requiredSkills: MutableMap<String, Int> = mutableMapOf(), val requiredStats: MutableMap<BaseStat, Int> = mutableMapOf(),
        val description: String = "Ability description")