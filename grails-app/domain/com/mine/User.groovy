package com.mine

class User {

    String username
    Role role

    static constraints = {
        username nullable: false, blank: false
        role nullable: false
    }
}