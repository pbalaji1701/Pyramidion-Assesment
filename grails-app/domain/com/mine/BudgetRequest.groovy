package com.mine

import java.time.LocalDateTime

class BudgetRequest {
    User requestedBy
    Department department
    String purpose
    LocalDateTime dateCreated
    BigDecimal requestedAmount
    Status status
    User approvedBy

    static constraints = {
        requestedBy nullable: false
        department nullable: false
        purpose nullable: false
        requestedAmount nullable: false
        status nullable: false
        approvedBy nullable: true
    }
}