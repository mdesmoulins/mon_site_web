package IngredientsRecettes


import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class IngredientsController {

    IngredientsService ingredientsService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ingredientsService.list(params), model:[ingredientsCount: ingredientsService.count()]
    }

    def show(Long id) {
        respond ingredientsService.get(id)
    }

    def create() {
        respond new Ingredients(params)
    }

    def save(Ingredients ingredients) {
        if (ingredients == null) {
            notFound()
            return
        }

        try {
            ingredientsService.save(ingredients)
        } catch (ValidationException e) {
            respond ingredients.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ingredients.label', default: 'Ingredients'), ingredients.id])
                redirect ingredients
            }
            '*' { respond ingredients, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond ingredientsService.get(id)
    }

    def update(Ingredients ingredients) {
        if (ingredients == null) {
            notFound()
            return
        }

        try {
            ingredientsService.save(ingredients)
        } catch (ValidationException e) {
            respond ingredients.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ingredients.label', default: 'Ingredients'), ingredients.id])
                redirect ingredients
            }
            '*'{ respond ingredients, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        ingredientsService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingredients.label', default: 'Ingredients'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredients.label', default: 'Ingredients'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
