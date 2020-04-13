package com.moviefinder.android.base

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment {
    constructor(layout: Int) : super(layout)
    constructor() : super()

    open fun draw() {
        println("hello my friend")
    }
}