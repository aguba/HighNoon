package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.Currency.*
import com.rafaelmallare.highnoon.EquipmentType.*
import com.rafaelmallare.highnoon.EquipmentSubType.*

/**
 * Created by Rj on 7/17/2017.
 */

class Equipment(val name: String, val type: EquipmentType,
                val subType: EquipmentSubType? = null, val modifiers: Map<DerivedStat, Int>,
                val cost: Int = 0, val currency: Currency = G) {
    val isWeapon: Boolean
        get() = type == MELEE || type == RANGED
}