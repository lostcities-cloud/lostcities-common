package io.dereknelson.lostcities.common.model

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class RoleConverter : AttributeConverter<Role, String> {
    override fun convertToDatabaseColumn(role: Role): String {
        return role.authority
    }

    override fun convertToEntityAttribute(authority: String): Role {
        return Role.findRoleByName(authority)
    }
}
