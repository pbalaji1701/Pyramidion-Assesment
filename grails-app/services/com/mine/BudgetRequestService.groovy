package com.mine

import groovy.transform.CompileStatic
import groovy.time.TimeCategory
import groovy.transform.CompileDynamic

@CompileStatic
@CompileDynamic
class BudgetRequestService {

   def createBudgetRequest(User requester, Department department, BigDecimal amount, String purpose) {
        if (amount <= 0) {
            throw new Exception("Requested amount must be positive")
        }

        def sevenDaysAgo = new Date() - 7
        def existingRequests = BudgetRequest.findAllByRequestedByAndDepartmentAndPurposeAndDateCreatedGreaterThan(
            requester, department, purpose, sevenDaysAgo
        )
        if (existingRequests) {
            throw new Exception("Duplicate request for the same purpose within 7 days")
        }

        def request = new BudgetRequest(
            requestedAmount: amount,
            purpose: purpose,
            status: Status.PENDING,
            requestedBy: requester,
            department: department
        )

        if (!request.save(flush: true)) {
            throw new Exception("Failed to save budget request")
        }

        new AuditLog(
            action: "CREATE",
            entityId: request.id,
            entityType: "BudgetRequest",
            oldValue: null,
            newValue: request.toString(),
            changedBy: requester,
            timestamp: new Date()
        ).save(flush: true)

        return request
    }

    def approveBudgetRequest(User manager, Long requestId) {
        if (manager.role != Role.MANAGER) {
            throw new Exception("Only managers can approve requests")
        }

        def request = BudgetRequest.get(requestId)
        if (!request) {
            throw new Exception("Request not found")
        }
        if (request.status != Status.PENDING) {
            throw new Exception("Request is not pending")
        }

        def maxAllowed = request.department.yearlyAllocation * 0.10
        if (request.requestedAmount > maxAllowed) {
            throw new Exception("Requested amount exceeds 10% of yearly allocation")
        }

        def department = request.department
        if (department.currentBudget < request.requestedAmount) {
            throw new Exception("Insufficient budget")
        }

        department.currentBudget -= request.requestedAmount
        department.save(flush: true)

        request.status = Status.APPROVED
        request.approvedBy = manager
        request.save(flush: true)

        new AuditLog(
            action: "APPROVE",
            entityId: request.id,
            entityType: "BudgetRequest",
            oldValue: Status.PENDING.toString(),
            newValue: Status.APPROVED.toString(),
            changedBy: manager,
            timestamp: new Date()
        ).save(flush: true)

        return request
    }

    def rejectBudgetRequest(User manager, Long requestId) {
        if (manager.role != Role.MANAGER) {
            throw new Exception("Only managers can reject requests")
        }

        def request = BudgetRequest.get(requestId)
        if (!request) {
            throw new Exception("Request not found")
        }
        if (request.status != Status.PENDING) {
            throw new Exception("Request is not pending")
        }

        request.status = Status.REJECTED
        request.approvedBy = manager
        request.save(flush: true)

        new AuditLog(
            action: "REJECT",
            entityId: request.id,
            entityType: "BudgetRequest",
            oldValue: Status.PENDING.toString(),
            newValue: Status.REJECTED.toString(),
            changedBy: manager,
            timestamp: new Date()
        ).save(flush: true)

        return request
    }
}