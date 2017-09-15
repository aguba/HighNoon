package com.rafaelmallare.highnoon

/**
 * Created by Rj on 9/14/2017.
 */
object PlayerSkills {
    private val general = mutableMapOf<String, Skill>()
    private val combat = mutableMapOf(
            "Aim" to Skill("Aim", BaseStat.PER),
            "Block" to Skill("Block", BaseStat.CON),
            "Evasion" to Skill("Evasion", BaseStat.DEX),
            "Initiative" to Skill("Initiative", BaseStat.DEX, BaseStat.PER)
    )
    private val mastery = mutableMapOf(
            EquipmentSubType.UNARMED to Skill("Unarmed Mastery", BaseStat.PER),
            EquipmentSubType.ONEHAND to Skill("One-Handed Mastery", BaseStat.PER),
            EquipmentSubType.TWOHAND to Skill("Two-Handed Mastery", BaseStat.PER),
            EquipmentSubType.HALFHAND to Skill("Hand-and-a-Half Mastery", BaseStat.PER),
            EquipmentSubType.LONG to Skill("Long Weapon Mastery", BaseStat.PER),
            EquipmentSubType.THROWN to Skill("Thrown Mastery", BaseStat.PER),
            EquipmentSubType.PISTOL to Skill("Pistol Mastery", BaseStat.PER),
            EquipmentSubType.RIFLE to Skill("Rifle Mastery", BaseStat.PER),
            EquipmentSubType.SHOTGUN to Skill("Shotgun Mastery", BaseStat.PER),
            EquipmentSubType.ARCHERY to Skill("Archery Mastery", BaseStat.PER)
    )

    private val emptySkill = Skill("Empty Skill", BaseStat.CHR)

    operator fun get(skill: String) : Skill {
        if (general.containsKey(skill)) return general[skill] ?: emptySkill
        else if (combat.containsKey(skill)) return combat[skill] ?: emptySkill
        else return emptySkill
    }

    operator fun get(masterySkill: EquipmentSubType) : Skill {
        if (mastery.containsKey(masterySkill)) return mastery[masterySkill] ?: emptySkill
        else return emptySkill
    }
}