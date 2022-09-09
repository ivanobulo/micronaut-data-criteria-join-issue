package mn.data.join.issue.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation

@MappedEntity
data class Chapter(
    @field:Id
    @field:GeneratedValue
    var id: Long?,
    val name: String,
    @Relation(Relation.Kind.MANY_TO_ONE)
    val book: Book?
)
