package io.dereknelson.lostcities.common.model.game.components

data class Card(
    val color: Color,
    val value: Int,
    val isMultiplier: Boolean = false
)
