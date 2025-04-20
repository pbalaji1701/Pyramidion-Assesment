package com.mine

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class BudgetRequestControllerSpec extends Specification implements ControllerUnitTest<BudgetRequestController> {

     void "test index action"() {
        when:
        controller.index()

        then:
        status == 200

     }
}
