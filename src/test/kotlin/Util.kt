import java.nio.file.Paths

fun getTestInput(day: Int, subValue: Int = 0): String {
    val filename = buildString {
        append("test_")
        append(day)
        if (subValue > 0) {
            append('_')
            append(subValue)
        }
        append(".txt")
    }

    return Paths.get("inputs", filename).toFile().readText()
}