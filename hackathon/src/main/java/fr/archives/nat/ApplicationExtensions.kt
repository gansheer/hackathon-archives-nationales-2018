package fr.archives.nat

/**
 *
 * @author Patrick Allain - 12/7/18.
 */
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

fun <T : Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
    return clazz.enclosingClass?.takeIf {
        clazz.enclosingClass.kotlin.companionObject?.java == clazz
    } ?: clazz
}

fun <T : Any> logger(clazz: Class<T>): Logger = LoggerFactory.getLogger(unwrapCompanionClass(clazz))

inline fun <reified T : Any> logger(): Logger = logger(T::class.java)

private fun onLevelEnable(level: Boolean, function: () -> Unit) {
    if (level) {
        function.invoke()
    }
}

abstract class KLog {
    val log = logger(this.javaClass)
}


fun Logger.info(block: () -> String) = onLevelEnable(this.isInfoEnabled) { this.info(block.invoke()) }

fun <E : Exception> Logger.errorThrow(e: E, block: () -> String): E =
        onLevelEnable(this.isErrorEnabled) { this.error(block.invoke(), e) }.let { e }