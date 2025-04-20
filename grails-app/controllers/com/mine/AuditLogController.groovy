package com.mine

import grails.converters.JSON
class AuditLogController {

  def index() {
        def entityType = params.entityType
        if (entityType) {
            def logs = AuditLog.findAllByEntityType(entityType)
            render logs as JSON
        } else {
            render status: 400, text: "entityType parameter is required"
        }
    }
}