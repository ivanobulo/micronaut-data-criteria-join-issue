package mn.data.join.issue
import io.micronaut.data.r2dbc.operations.R2dbcOperations
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import mn.data.join.issue.model.Book
import mn.data.join.issue.model.Chapter
import mn.data.join.issue.repository.BookRepository
import org.junit.jupiter.api.BeforeEach
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import java.time.Duration

@MicronautTest
class MnDataJoinIssueTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var r2dbc: R2dbcOperations

    @BeforeEach
    internal fun setUp() {
        transaction {
            bookRepository.save(Book(null, "Foo", setOf(toChapter("foo"), toChapter("bar"))))
                .then(
                    bookRepository.save(Book(null, "Bar", setOf(toChapter("bar"))))
                )
        }
    }

    private fun toChapter(name: String): Chapter = Chapter(null, name, null)

    @Test
    fun testItWorks() {
        val bookWithFooChapter = transaction { bookRepository.findAll(BookRepository.Specifications.hasChapter("foo")) }
        Assertions.assertEquals("Foo", bookWithFooChapter?.name)
    }

    private fun <T> transaction(
        timeout: Duration = Duration.ofSeconds(10),
        block: () -> Publisher<T>
    ): T? {
        val r2dbcOp = r2dbc.withTransaction {
            block()
        }
        return Mono.from(r2dbcOp).block(timeout)
    }
}
