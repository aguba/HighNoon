package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/16/2017.
 */

enum class BaseStat(val category: String) {
    STR("PHYS"), CON("PHYS"), DEX("PHYS"),
    PER("MENT"), CHR("MENT"), INT("MENT")
}

enum class DerivedStat {
    HP, INIT, DEF, ARM, ATK, DMG, WILL, MP, SPD
}