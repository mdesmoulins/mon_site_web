package recettes

import IngredientsRecettes.Unite

class BootStrap {

    def init = { servletContext ->
        User.withTransaction {
            //Creation utilisateur admin
            if (User.countByUsername("Maxence") == 0) {
                User userAdmin = new User(username: "Maxence", password: "BonusAxione2").save(flush: true, failOnError: true)
                new UserRole(utilisateur: userAdmin, role: Role.get(1)).save(flush: true)
            }
        }

    }
    def destroy = {
    }
}
