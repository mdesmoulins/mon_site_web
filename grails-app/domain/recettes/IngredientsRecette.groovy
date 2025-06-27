

package recettes

import IngredientsRecettes.Ingredients
import IngredientsRecettes.Unite

class IngredientsRecette {
    Ingredients ingredient
    Unite unite
    Integer quantite

    static constraints = {
        ingredient nullable: false
        unite nullable: false
        quantite nullable: false
    }
}
