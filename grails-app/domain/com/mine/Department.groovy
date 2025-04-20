package com.mine

class Department {

   String name
    BigDecimal currentBudget
    BigDecimal yearlyAllocation

    static constraints = {
        name nullable: false, blank: false
        currentBudget nullable: false
        yearlyAllocation nullable: false
    }

}