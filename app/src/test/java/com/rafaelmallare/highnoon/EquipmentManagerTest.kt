package com.rafaelmallare.highnoon

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Rj on 9/15/2017.
 */
class EquipmentManagerTest {
    val sword = Equipment("Sword", EquipmentType.MELEE, EquipmentSubType.ONEHAND)
    val shield = Equipment("Shield", EquipmentType.SHIELDS)
    val helmet = Equipment("Helmet", EquipmentType.HELMET)
    val armor = Equipment("Armor", EquipmentType.ARMOR)
    val longSword = Equipment("Long Sword", EquipmentType.MELEE, EquipmentSubType.LONG)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun equipItem() {
        assertEquals("Empty Head", EquipmentManager[EquipSlot.HEAD].name)
        assertEquals("Empty Body", EquipmentManager[EquipSlot.BODY].name)
        assertEquals("Empty Hand", EquipmentManager[EquipSlot.PRIMEHAND].name)
        assertEquals("Empty Hand", EquipmentManager[EquipSlot.OFFHAND].name)

        assertFalse(EquipmentManager.equipItem(helmet))

        PlayerManager.inventory.add(helmet)
        PlayerManager.inventory.add(armor)
        PlayerManager.inventory.add(sword)
        PlayerManager.inventory.add(shield)

        assertTrue(EquipmentManager.equipItem(helmet))
        assertTrue(EquipmentManager.equipItem(armor))
        assertTrue(EquipmentManager.equipItem(sword))
        assertTrue(EquipmentManager.equipItem(shield))

        assertEquals(helmet, EquipmentManager[EquipSlot.HEAD])
        assertEquals(armor, EquipmentManager[EquipSlot.BODY])
        assertEquals(sword, EquipmentManager[EquipSlot.PRIMEHAND])
        assertEquals(shield, EquipmentManager[EquipSlot.OFFHAND])

        PlayerManager.inventory.add(longSword)

        assertTrue(EquipmentManager.equipItem(longSword))
        assertEquals(longSword, EquipmentManager[EquipSlot.PRIMEHAND])
        assertEquals("Full Hand", EquipmentManager[EquipSlot.OFFHAND].name)

        EquipmentManager.equipItem(sword)
        assertEquals(sword, EquipmentManager[EquipSlot.PRIMEHAND])
        assertEquals("Empty Hand", EquipmentManager[EquipSlot.OFFHAND].name)

        EquipmentManager.equipItem(sword, true)
        assertEquals(sword, EquipmentManager[EquipSlot.OFFHAND])
    }

    @Test
    fun unequipItem() {
        PlayerManager.inventory.add(helmet)
        PlayerManager.inventory.add(armor)
        PlayerManager.inventory.add(sword)
        PlayerManager.inventory.add(shield)
        EquipmentManager.equipItem(helmet)
        EquipmentManager.equipItem(armor)
        EquipmentManager.equipItem(sword)
        EquipmentManager.equipItem(shield)
        assertEquals(helmet, EquipmentManager[EquipSlot.HEAD])
        assertEquals(armor, EquipmentManager[EquipSlot.BODY])
        assertEquals(sword, EquipmentManager[EquipSlot.PRIMEHAND])
        assertEquals(shield, EquipmentManager[EquipSlot.OFFHAND])

        assertTrue(EquipmentManager.unequipItem(helmet))
        assertTrue(EquipmentManager.unequipItem(armor))
        assertTrue(EquipmentManager.unequipItem(sword))
        assertTrue(EquipmentManager.unequipItem(shield))

        assertFalse(EquipmentManager.unequipItem(sword))

        assertEquals("Empty Head", EquipmentManager[EquipSlot.HEAD].name)
        assertEquals("Empty Body", EquipmentManager[EquipSlot.BODY].name)
        assertEquals("Empty Hand", EquipmentManager[EquipSlot.PRIMEHAND].name)
        assertEquals("Empty Hand", EquipmentManager[EquipSlot.OFFHAND].name)
    }

}