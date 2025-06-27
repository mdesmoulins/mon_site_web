package IngredientsRecettes

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class IngredientsServiceSpec extends Specification {

    IngredientsService ingredientsService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Ingredients(...).save(flush: true, failOnError: true)
        //new Ingredients(...).save(flush: true, failOnError: true)
        //Ingredients ingredients = new Ingredients(...).save(flush: true, failOnError: true)
        //new Ingredients(...).save(flush: true, failOnError: true)
        //new Ingredients(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //ingredients.id
    }

    void "test get"() {
        setupData()

        expect:
        ingredientsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Ingredients> ingredientsList = ingredientsService.list(max: 2, offset: 2)

        then:
        ingredientsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        ingredientsService.count() == 5
    }

    void "test delete"() {
        Long ingredientsId = setupData()

        expect:
        ingredientsService.count() == 5

        when:
        ingredientsService.delete(ingredientsId)
        sessionFactory.currentSession.flush()

        then:
        ingredientsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Ingredients ingredients = new Ingredients()
        ingredientsService.save(ingredients)

        then:
        ingredients.id != null
    }
}
