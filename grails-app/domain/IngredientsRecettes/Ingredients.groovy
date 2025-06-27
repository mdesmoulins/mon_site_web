

package IngredientsRecettes

class Ingredients {
    String nom
    String description


    static constraints = {
        nom nullable: false, blank: false
        description nullable: true, blank: true
    }
}
