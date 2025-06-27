package recettes

import recettes.IngredientsRecette
import grails.gorm.services.Service

@Service(IngredientsRecette)
interface IngredientsRecetteService {

    IngredientsRecette get(Serializable id)

    List<IngredientsRecette> list(Map args)

    Long count()

    void delete(Serializable id)

    IngredientsRecette save(IngredientsRecette ingredientsRecette)

}