package recettes

import grails.gorm.services.Service

@Service(Ustensiles)
interface UstensilesService {

    Ustensiles get(Serializable id)

    List<Ustensiles> list(Map args)

    Long count()

    void delete(Serializable id)

    Ustensiles save(Ustensiles ustensiles)

}