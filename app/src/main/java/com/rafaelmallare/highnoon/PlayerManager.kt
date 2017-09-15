package com.rafaelmallare.highnoon

/**
 * Created by Rj on 7/16/2017.
 */
object PlayerManager {
    var name = "Character Name"
    val faction = "Placeholder: Faction"
    val classes = mutableListOf<CharacterClass>()

    object exp {
        private var _current = 0
        private var _total = 0

        val current
            get() = _current
        val total
            get() = _total

        fun changeBy(amount: Int) : Boolean {
            if (amount >= 0) _total += amount
            if ((_current + amount) < 0) return false
            _current += amount
            return true
        }
    }

    val inventory = mutableListOf<Equipment>()
}