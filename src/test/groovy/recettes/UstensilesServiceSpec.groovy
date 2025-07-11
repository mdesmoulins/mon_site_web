package recettes

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UstensilesServiceSpec extends Specification {

    UstensilesService ustensilesService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Ustensiles(...).save(flush: true, failOnError: true)
        //new Ustensiles(...).save(flush: true, failOnError: true)
        //Ustensiles ustensiles = new Ustensiles(...).save(flush: true, failOnError: true)
        //new Ustensiles(...).save(flush: true, failOnError: true)
        //new Ustensiles(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //ustensiles.id
    }

    void "test get"() {
        setupData()

        expect:
        ustensilesService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Ustensiles> ustensilesList = ustensilesService.list(max: 2, offset: 2)

        then:
        ustensilesList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        ustensilesService.count() == 5
    }

    void "test delete"() {
        Long ustensilesId = setupData()

        expect:
        ustensilesService.count() == 5

        when:
        ustensilesService.delete(ustensilesId)
        sessionFactory.currentSession.flush()

        then:
        ustensilesService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Ustensiles ustensiles = new Ustensiles()
        ustensilesService.save(ustensiles)

        then:
        ustensiles.id != null
    }
}
