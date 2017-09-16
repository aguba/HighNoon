package com.rafaelmallare.highnoon

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Rj on 9/16/2017.
 */
class PlayerStatsTest {
    @Test
    fun get() {
        //input: DerivedStat
        //returns derived stat value
        assertEquals(13, PlayerStats[DerivedStat.HP].base)
        assertEquals(2, PlayerStats[DerivedStat.INIT].base)
        assertEquals(1, PlayerStats[DerivedStat.DEF].base)
        assertEquals(0, PlayerStats[DerivedStat.ARM].base)
        assertEquals(1, PlayerStats[DerivedStat.DMG].base)
        assertEquals(5, PlayerStats[DerivedStat.WILL].base)
        assertEquals(12, PlayerStats[DerivedStat.MP].base)
        assertEquals(4, PlayerStats[DerivedStat.SPD].base)
        assertEquals(1, PlayerStats[DerivedStat.ATK].base)
    }

    @Test
    fun changeStatBy() {
        
    }

    @Test
    fun addModifiers() {

    }

    @Test
    fun removeModifiers() {

    }

}