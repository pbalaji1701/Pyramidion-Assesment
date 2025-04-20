package budget.request.system

class UrlMappings {
    static mappings = {
        "/api/budget-request"(controller: "budgetRequest", action: "create", method: "POST")
        "/api/budget-request/$id/approve"(controller: "budgetRequest", action: "approve", method: "PUT")
        "/api/budget-request/$id/reject"(controller: "budgetRequest", action: "reject", method: "PUT")
        "/api/budget-request/pending"(controller: "budgetRequest", action: "pending", method: "GET")
        "/api/audit-logs"(controller: "auditLog", action: "index", method: "GET")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }

}
