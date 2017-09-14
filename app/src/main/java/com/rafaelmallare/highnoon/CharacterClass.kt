package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/23/2017.
 */

class CharacterClass(
        val name: String, val primaryStats: List<BaseStat>, val secondaryStats: List<BaseStat>,
        val skills: List<Skill>, val startGear: List<Equipment>, val startCash: Int = 0) {

}