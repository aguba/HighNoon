package com.rafaelmallare.highnoon

/**
 * Created by Rj on 9/13/2017.
 */
object EquipmentManager {
    private val emptyHead = Equipment("Empty Head", EquipmentType.HELMET)
    private val emptyBody = Equipment("Empty Body", EquipmentType.ARMOR)
    private val emptyHand = Equipment("Empty Hand", EquipmentType.MELEE, EquipmentSubType.UNARMED)
    private val fullHand = Equipment("Full Hand", EquipmentType.MELEE)

    private val nullEquipment = Equipment("Null Equipment", EquipmentType.MELEE)

    private val equipmentSlots = mutableMapOf(
            EquipSlot.HEAD to emptyHead, EquipSlot.BODY to emptyBody, EquipSlot.PRIMEHAND to emptyHand, EquipSlot.OFFHAND to emptyHand
    )

    operator fun get(slot: EquipSlot) : Equipment {
        return equipmentSlots[slot] ?: nullEquipment
    }

    fun equipItem(item: Equipment, forceOffHand: Boolean = false): Boolean {
        if (item !in PlayerManager.inventory) return false

        if (item.isTwoHanded) {
            equipmentSlots.put(EquipSlot.OFFHAND, fullHand)
            equipmentSlots.put(item.equipSlot, item)
        } else if ((item.isWeapon || item.isShield) && equipmentSlots[EquipSlot.OFFHAND] === fullHand) {
            equipmentSlots.put(EquipSlot.OFFHAND, emptyHand)
            equipmentSlots.put(item.equipSlot, item)
        } else if (item.isOffhand || ((item.isWeapon || item.isShield) && forceOffHand)) {
            item.isOffhand = true
            equipmentSlots.put(EquipSlot.OFFHAND, item)
        } else equipmentSlots.put(item.equipSlot, item)

        PlayerStats.addModifiers(item.name, item.modifiers)

        return true
    }

    fun unequipItem(item: Equipment): Boolean {
        val slot = item.equipSlot
        if (item !== equipmentSlots[slot]) return false
        when(slot) {
            EquipSlot.HEAD -> equipmentSlots.put(EquipSlot.HEAD, emptyHead)
            EquipSlot.BODY -> equipmentSlots.put(EquipSlot.BODY, emptyBody)
            EquipSlot.PRIMEHAND -> {
                if (item.isTwoHanded) equipmentSlots.put(EquipSlot.OFFHAND, emptyHand)
                equipmentSlots.put(EquipSlot.PRIMEHAND, emptyHand)
            }
            EquipSlot.OFFHAND -> {
                if (item.isTwoHanded) equipmentSlots.put(EquipSlot.PRIMEHAND, emptyHand)
                item.isOffhand = (item.defaultEquipSlot == EquipSlot.OFFHAND)
                equipmentSlots.put(EquipSlot.OFFHAND, emptyHand)
            }
        }

        PlayerStats.removeModifiers(item.name)

        return true
    }
}