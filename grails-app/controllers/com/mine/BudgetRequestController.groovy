package com.mine
import grails.converters.JSON

class BudgetRequestController {

     def budgetRequestService
    def springSecurityService

    def create() {
        def currentUser = springSecurityService.currentUser
        def json = request.JSON
        def departmentId = json.departmentId
        def amount = json.amount as BigDecimal
        def purpose = json.purpose

        def department = Department.get(departmentId)
        if (!department) {
            render status: 404, text: "Department not found"
            return
        }

        try {
            def request = budgetRequestService.createBudgetRequest(currentUser, department, amount, purpose)
            render request as JSON
        } catch (Exception e) {
            render status: 400, text: e.message
        }
    }

    def approve(Long id) {
        def currentUser = springSecurityService.currentUser
        try {
            def request = budgetRequestService.approveBudgetRequest(currentUser, id)
            render request as JSON
        } catch (Exception e) {
            render status: 400, text: e.message
        }
    }

    def reject(Long id) {
        def currentUser = springSecurityService.currentUser
        try {
            def request = budgetRequestService.rejectBudgetRequest(currentUser, id)
            render request as JSON
        } catch (Exception e) {
            render status: 400, text: e.message
        }
    }

    def pending() {
        def requests = BudgetRequest.findAllByStatus(Status.PENDING)
        render requests as JSON
    }
}