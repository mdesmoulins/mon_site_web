package recettes

class MesRecettes {
    String nom
    String description
    String instruction
    Integer difficulte
    Photo photoMiniature
    Photo photoComplete
    static hasMany = [ingredientsrecettes: IngredientsRecette, ustensiles: Ustensiles]

    static mapping = {
        description sqlType: 'text'
        instruction sqlType: 'text'
    }

    static constraints = {
        nom nullable: false, blank: false
        description nullable: true, blank: true
        difficulte nullable: false, min: 1, max: 5
        instruction nullable: false, blank: false
    }
}
