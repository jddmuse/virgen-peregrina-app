package com.virgen.peregrina.util.provider

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(
     @ApplicationContext private val context: Context
) {

    fun getStringResource(strResource: Int): String = context.getString(strResource)

    fun getStringResource(@StringRes strResource: Int, vararg formatArgs: Any): String =
        context.getString(strResource, *formatArgs)


}