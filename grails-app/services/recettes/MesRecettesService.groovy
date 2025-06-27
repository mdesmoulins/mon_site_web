package recettes

import grails.gorm.services.Service

@Service(MesRecettes)
interface MesRecettesService {

    MesRecettes get(Serializable id)

    List<MesRecettes> list(Map args)

    Long count()

    void delete(Serializable id)

    MesRecettes save(MesRecettes mesRecettes)



}