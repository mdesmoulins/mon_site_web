package recettes


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class IngredientsRecetteServiceSpec extends Specification {

    IngredientsRecetteService ingredientsRecetteService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new IngredientsRecette(...).save(flush: true, failOnError: true)
        //new IngredientsRecette(...).save(flush: true, failOnError: true)
        //IngredientsRecette ingredientsRecette = new IngredientsRecette(...).save(flush: true, failOnError: true)
        //new IngredientsRecette(...).save(flush: true, failOnError: true)
        //new IngredientsRecette(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //ingredientsRecette.id
    }

    void "test get"() {
        setupData()

        expect:
        ingredientsRecetteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<IngredientsRecette> ingredientsRecetteList = ingredientsRecetteService.list(max: 2, offset: 2)

        then:
        ingredientsRecetteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        ingredientsRecetteService.count() == 5
    }

    void "test delete"() {
        Long ingredientsRecetteId = setupData()

        expect:
        ingredientsRecetteService.count() == 5

        when:
        ingredientsRecetteService.delete(ingredientsRecetteId)
        sessionFactory.currentSession.flush()

        then:
        ingredientsRecetteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        IngredientsRecette ingredientsRecette = new IngredientsRecette()
        ingredientsRecetteService.save(ingredientsRecette)

        then:
        ingredientsRecette.id != null
    }
}
