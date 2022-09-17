package com.virgen.peregrina.util.provider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
     @ApplicationContext private val context: Context
) {

    fun getStringResource(strResource: Int): String = context.getString(strResource)

    fun getStringResource(strResource: Int, vararg formatArgs: Any): String =
        context.getString(strResource, *formatArgs)


}