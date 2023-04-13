package org.hse.midoriya.core.model.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "productcode")
data class ProductCode(
    @Id
    @Column(name = "code", nullable = false)
    val code: String? = null,
    @Column(name = "title", nullable = false)
    val title: String? = null,
    @Column(name = "isactive")
    var isActive: Boolean = false,
    @Column(name = "exist")
    var exist: Boolean? = null
)