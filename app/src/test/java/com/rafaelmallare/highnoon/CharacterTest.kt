package com.rafaelmallare.highnoon

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Created by Rj on 7/20/2017.
 */
class CharacterTest {
    @Before
    fun clearEquipment() {
        val failEquipment = Equipment("Fail", EquipmentType.MELEE)
        Character.unequipItem(Character.equipmentSlots[EquipSlot.HEAD] ?: failEquipment)
        Character.unequipItem(Character.equipmentSlots[EquipSlot.BODY] ?: failEquipment)
        Character.unequipItem(Character.equipmentSlots[EquipSlot.PRIMEHAND] ?: failEquipment)
        Character.unequipItem(Character.equipmentSlots[EquipSlot.OFFHAND] ?: failEquipment)
    }

    @Test
    fun getBaseStats() {
        for (stat in BaseStat.values()) {
            assertEquals(1, Character.baseStats[stat])

            Character.changeStatBy(stat, 1)
            assertEquals(2, Character.baseStats[stat])

            Character.changeStatBy(stat, 10)
            assertEquals(2, Character.baseStats[stat])
        }
    }

    @Test
    fun getInventory() {

    }

    @Test
    fun getEquipmentSlots() {
        val failEquipment = Equipment("Fail", EquipmentType.MELEE)
        assertEquals("Empty Head", (Character.equipmentSlots[EquipSlot.HEAD] ?: failEquipment).name)
        assertEquals("Empty Body", (Character.equipmentSlots[EquipSlot.BODY] ?: failEquipment).name)
        assertEquals("Empty Hand", (Character.equipmentSlots[EquipSlot.PRIMEHAND] ?: failEquipment).name)
        assertEquals("Empty Hand", (Character.equipmentSlots[EquipSlot.OFFHAND] ?: failEquipment).name)
    }

    @Test
    fun changeStatBy() {

    }

    @Test
    fun equipItem() {
        val helmet = Equipment("Helmet", EquipmentType.HELMET)
        val armor = Equipment("Armor", EquipmentType.ARMOR)
        val sword = Equipment("Sword", EquipmentType.MELEE)
        val offSword = Equipment("Off Sword", EquipmentType.MELEE)
        val failEquipment = Equipment("Fail", EquipmentType.MELEE)

        Character.inventory.add(helmet)
        Character.inventory.add(armor)
        Character.inventory.add(sword)
        Character.inventory.add(offSword)

        assertTrue(Character.equipItem(helmet))
        assertTrue(Character.equipItem(armor))
        assertTrue(Character.equipItem(sword))
        assertTrue(Character.equipItem(offSword, true))

        assertFalse(Character.equipItem(failEquipment))

        assertEquals(helmet, Character.equipmentSlots[EquipSlot.HEAD])
        assertEquals(armor, Character.equipmentSlots[EquipSlot.BODY])
        assertEquals(sword, Character.equipmentSlots[EquipSlot.PRIMEHAND])
        assertEquals(offSword, Character.equipmentSlots[EquipSlot.OFFHAND])

        val twoHandSword = Equipment("Two Handed Sword", EquipmentType.MELEE, EquipmentSubType.TWOHAND)
        Character.inventory.add(twoHandSword)
        assertTrue(Character.equipItem(twoHandSword))
        assertEquals(twoHandSword, Character.equipmentSlots[EquipSlot.PRIMEHAND])
        assertEquals("Full Hand", (Character.equipmentSlots[EquipSlot.OFFHAND] ?: failEquipment).name)

        assertTrue(Character.equipItem(sword))
        assertEquals(sword, Character.equipmentSlots[EquipSlot.PRIMEHAND])
        assertEquals("Empty Hand", (Character.equipmentSlots[EquipSlot.OFFHAND] ?: failEquipment).name)
    }

    @Test
    fun unequipItem() {
        val helmet = Equipment("Helmet", EquipmentType.HELMET)
        val armor = Equipment("Armor", EquipmentType.ARMOR)
        val sword = Equipment("Sword", EquipmentType.MELEE)
        val offSword = Equipment("Off Sword", EquipmentType.MELEE)
        val failEquipment = Equipment("Fail", EquipmentType.MELEE)

        Character.inventory.add(helmet)
        Character.inventory.add(armor)
        Character.inventory.add(sword)
        Character.inventory.add(offSword)
        Character.equipItem(helmet)
        Character.equipItem(armor)
        Character.equipItem(sword)
        Character.equipItem(offSword, true)

        assertEquals(helmet, Character.equipmentSlots[EquipSlot.HEAD])
        assertEquals(armor, Character.equipmentSlots[EquipSlot.BODY])
        assertEquals(sword, Character.equipmentSlots[EquipSlot.PRIMEHAND])
        assertEquals(offSword, Character.equipmentSlots[EquipSlot.OFFHAND])

        Character.unequipItem(helmet)
        Character.unequipItem(armor)
        Character.unequipItem(sword)
        Character.unequipItem(offSword)

        assertEquals("Empty Head", (Character.equipmentSlots[EquipSlot.HEAD] ?: failEquipment).name)
        assertEquals("Empty Body", (Character.equipmentSlots[EquipSlot.BODY] ?: failEquipment).name)
        assertEquals("Empty Hand", (Character.equipmentSlots[EquipSlot.PRIMEHAND] ?: failEquipment).name)
        assertEquals("Empty Hand", (Character.equipmentSlots[EquipSlot.OFFHAND] ?: failEquipment).name)
    }

}