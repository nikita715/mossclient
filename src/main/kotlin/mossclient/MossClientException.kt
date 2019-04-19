package mossclient

/**
 * Common moss client exception
 */
internal class MossClientException(message: String, throwable: Throwable? = null) : RuntimeException(message, throwable)