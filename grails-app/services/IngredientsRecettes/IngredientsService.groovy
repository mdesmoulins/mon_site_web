package IngredientsRecettes

import grails.gorm.services.Service
import IngredientsRecettes.Ingredients

@Service(Ingredients)
interface IngredientsService {

    Ingredients get(Serializable id)

    List<Ingredients> list(Map args)

    Long count()

    void delete(Serializable id)

    Ingredients save(Ingredients ingredients)

}