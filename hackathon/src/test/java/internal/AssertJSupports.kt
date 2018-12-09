package internal

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.ProxyableListAssert
import org.assertj.core.api.ProxyableObjectAssert
import org.assertj.core.api.SoftAssertions

/**
 *
 * @author Patrick Allain - 12/8/18.
 */
fun softly(block: SoftAssertions.() -> Unit) = SoftAssertions.assertSoftly { block.invoke(it) }

fun then(block: SoftAssertions.() -> Unit) = softly(block)

fun <T> castIt(obj: Any): T {
    @Suppress("UNCHECKED_CAST")
    return obj as T
}

fun <ACTUAL : Any?, ASSERT : AbstractAssert<ASSERT, ACTUAL>> onActual(a: ASSERT): ACTUAL? {
    return AbstractAssert::class.java.getDeclaredField("actual")
            .also { it.isAccessible = true }
            .let { it.get(a) }
            .takeIf { it != null }
            ?.let { castIt(it) }
}

inline fun <reified T : Any> ProxyableObjectAssert<T?>.notNull(crossinline block: (T) -> Unit) {
    this.isNotNull
    onActual(this)?.let { softly { block.invoke(it) } }
}

inline fun <reified T : Any> ProxyableListAssert<T?>.onSize(size: Int, crossinline block: (List<T>) -> Unit) {
    this.isNotNull
    onActual(this)
            ?.filterNotNull()
            ?.toList()
            ?.let { actual ->
                softly {
                    this.assertThat(actual).hasSize(size)
                    actual.takeIf { it.size == size }?.let(block)
                }
            }
}