package com.rafaelmallare.highnoon

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Rj on 9/15/2017.
 */
class PlayerSkillsTest {
    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun get() {
        //input: String
        //for general and combat skills

        assertEquals("Aim", PlayerSkills["Aim"].name)
        assertEquals("Block", PlayerSkills["Block"].name)
        assertEquals("Evasion", PlayerSkills["Evasion"].name)
        assertEquals("Initiative", PlayerSkills["Initiative"].name)
        assertEquals("Debug Skill", PlayerSkills["Debug Skill"].name)
    }

    @Test
    fun get1() {
        //input: EquipmentSubType
        //for mastery skills
        
        assertEquals("Unarmed Mastery", PlayerSkills[EquipmentSubType.UNARMED].name)
        assertEquals("Pistol Mastery", PlayerSkills[EquipmentSubType.PISTOL].name)
    }

}