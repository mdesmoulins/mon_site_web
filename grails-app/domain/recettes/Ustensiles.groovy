package recettes

class Ustensiles {
    String nom
    String description

    static constraints = {
        nom (nullable:false,blank:false)
        description (nullable:true,blank:true)
    }


}