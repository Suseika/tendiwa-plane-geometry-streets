package org.tendiwa.plane.geometry.streets

import java.util.*

class Link<T>(val payload: T) {
    private var left: Link<T>? = null
    private var right: Link<T>? = null

    fun connect(other: Link<T>) {
        if (left == null) {
            left = other
        } else if (right == null) {
            right = other
        } else {
            throw IllegalStateException("This link has both valences occupied")
        }
        if (other.left == null) {
            other.left = this
        } else if (other.right == null) {
            other.right = this
        } else {
            throw IllegalStateException("Other link has both valences occupied")
        }
    }

    fun toOpenChainList(): List<T> {
        if (!isChainEnd()) {
            throw IllegalStateException(
                "Only an end of a chain may be turned into a list"
            )
        }
        var previous = this
        var current: Link<T>? = if (left == null) right else left
        val list = ArrayList<T>()
            .apply {
                add(previous.payload)
            }

        fun next(): Link<T>? =
            if (current!!.left == previous) {
                current!!.right
            } else {
                current!!.left
            }

        while (current != null) {
            list.add(current.payload)
            previous = current
            current = next()
        }
        return list
    }

    fun toCycleList(): List<T> {
        val first = this
        val list = ArrayList<T>().apply { add(first.payload) }
        var current: Link<T>? = right!!
        var previous = first
        do {
            if (current!!.right == previous) {
                list.add(current.left!!.payload)
                current = current.left
            } else {
                assert(current.left == previous)
                list.add(current.right!!.payload)
                current = current.right
            }
            previous = current!!
        } while (current != first)
        return list
    }

    /**
     * Checks if this link is an end of a chain, i.e. it is missing either a
     * [left] link or [right], but not both.
     */
    fun isChainEnd() = (left == null) xor (right == null)
}
