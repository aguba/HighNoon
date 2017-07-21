package com.rafaelmallare.highnoon

import com.rafaelmallare.highnoon.Currency.*
import com.rafaelmallare.highnoon.EquipmentType.*
import com.rafaelmallare.highnoon.EquipmentSubType.*

/**
 * Created by Rj on 7/17/2017.
 */

class Equipment(val name: String, val type: EquipmentType,
                val subType: EquipmentSubType? = null, val modifiers: Map<DerivedStat, Int> = mapOf<DerivedStat, Int>(),
                val cost: Int = 0, val currency: Currency = G) {

    val id: Int

    //Allows checking of equipment slot with private backing var to ensure slot can only be changed
    //through approved methods
    private var _equipSlot: EquipSlot
    val equipSlot
        get() = _equipSlot

    private companion object {
        var refId = 0
    }

    init {
        Companion.refId++
        id = refId
        _equipSlot = type.equipSlot
    }

    val isWeapon: Boolean
        get() = type == MELEE || type == RANGED

    val isTwoHanded: Boolean
        get() = isWeapon && (subType == TWOHAND || subType == LONG || subType == RIFLE || subType == SHOTGUN || subType == ARCHERY)

    var isOffhand: Boolean
        get() = equipSlot == EquipSlot.OFFHAND
        set(value) {
            if (isWeapon) {
                if (value) _equipSlot = EquipSlot.OFFHAND
                else _equipSlot = EquipSlot.PRIMEHAND
            }
        }

    fun equals(equipment: Equipment): Boolean {
        return id == equipment.id && name == equipment.name
    }
}