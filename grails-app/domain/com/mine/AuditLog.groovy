package com.mine

class AuditLog {

   String action
    Long entityId
    String entityType
    String oldValue
    String newValue
    User changedBy
    Date timestamp

    static constraints = {
        action nullable: false
        entityId nullable: false
        entityType nullable: false
        oldValue nullable: true
        newValue nullable: true
        changedBy nullable: false
        timestamp nullable: false
    }
}