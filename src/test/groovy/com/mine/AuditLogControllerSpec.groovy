package com.mine

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class AuditLogControllerSpec extends Specification implements ControllerUnitTest<AuditLogController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
