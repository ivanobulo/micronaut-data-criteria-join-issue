package mn.data.join.issue.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation

@MappedEntity
data class Book(
    @field:Id
    @field:GeneratedValue
    var id: Long?,
    val name: String?,
    @Relation(Relation.Kind.ONE_TO_MANY, cascade = [Relation.Cascade.PERSIST], mappedBy = "book")
    val chapters: Set<Chapter>? = null,
)
