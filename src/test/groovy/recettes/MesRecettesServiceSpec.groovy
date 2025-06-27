package recettes

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MesRecettesServiceSpec extends Specification {

    MesRecettesService mesRecettesService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MesRecettes(...).save(flush: true, failOnError: true)
        //new MesRecettes(...).save(flush: true, failOnError: true)
        //MesRecettes mesRecettes = new MesRecettes(...).save(flush: true, failOnError: true)
        //new MesRecettes(...).save(flush: true, failOnError: true)
        //new MesRecettes(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //mesRecettes.id
    }

    void "test get"() {
        setupData()

        expect:
        mesRecettesService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MesRecettes> mesRecettesList = mesRecettesService.list(max: 2, offset: 2)

        then:
        mesRecettesList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mesRecettesService.count() == 5
    }

    void "test delete"() {
        Long mesRecettesId = setupData()

        expect:
        mesRecettesService.count() == 5

        when:
        mesRecettesService.delete(mesRecettesId)
        sessionFactory.currentSession.flush()

        then:
        mesRecettesService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MesRecettes mesRecettes = new MesRecettes()
        mesRecettesService.save(mesRecettes)

        then:
        mesRecettes.id != null
    }
}
