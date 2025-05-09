package com.msantosfelipe.financehub.domains.objectives.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.objectives.domain.model.InvestmentsObjective
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@JdbcRepository(dialect = Dialect.POSTGRES)
interface InvestmentsObjectivePostgresRepository : CoroutineCrudRepository<InvestmentsObjective, UUID>
