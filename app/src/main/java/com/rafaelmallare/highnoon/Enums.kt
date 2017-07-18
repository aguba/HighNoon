package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.EquipSlot.*

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

enum class EquipmentType(val equipSlot: EquipSlot) {
    ARMOR(BODY), SHIELDS(PRIMEHAND), HELMET(HEAD), MELEE(PRIMEHAND), RANGED(PRIMEHAND)
}

enum class EquipmentSubType {
    SHIELD, UNARMED, ONEHAND, TWOHAND, HALFHAND, LONG, THROWN, PISTOL, RIFLE, SHOTGUN, ARCHERY, SPECIAL
}

enum class Currency {
    G, CASH, TRI
}

enum class EquipSlot {
    HEAD, BODY, PRIMEHAND, OFFHAND
}