package mn.data.join.issue.repository

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.PageableRepository
import io.micronaut.data.repository.jpa.criteria.PredicateSpecification
import io.micronaut.data.repository.jpa.reactive.ReactorJpaSpecificationExecutor
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import io.micronaut.data.runtime.criteria.get
import io.micronaut.data.runtime.criteria.joinMany
import mn.data.join.issue.model.Book
import mn.data.join.issue.model.Chapter

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface BookRepository : ReactorJpaSpecificationExecutor<Book>, ReactorPageableRepository<Book, Long> {

    object Specifications {
        fun hasChapter(chapterName: String): PredicateSpecification<Book> =
            PredicateSpecification { root, criteriaBuilder ->
                val chaptersPath = root.joinMany(Book::chapters)
                criteriaBuilder.equal(chaptersPath[Chapter::name], chapterName)
            }
    }
}