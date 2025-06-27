package recettes


import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class IngredientsRecetteController {

    IngredientsRecetteService ingredientsRecetteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ingredientsRecetteService.list(params), model:[ingredientsRecetteCount: ingredientsRecetteService.count()]
    }

    def show(Long id) {
        respond ingredientsRecetteService.get(id)
    }

    def create() {
        respond new IngredientsRecette(params)
    }

    def save(IngredientsRecette ingredientsRecette) {
        if (ingredientsRecette == null) {
            notFound()
            return
        }

        try {
            ingredientsRecetteService.save(ingredientsRecette)
        } catch (ValidationException e) {
            respond ingredientsRecette.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ingredientsRecette.label', default: 'IngredientsRecette'), ingredientsRecette.id])
                redirect ingredientsRecette
            }
            '*' { respond ingredientsRecette, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond ingredientsRecetteService.get(id)
    }

    def update(IngredientsRecette ingredientsRecette) {
        if (ingredientsRecette == null) {
            notFound()
            return
        }

        try {
            ingredientsRecetteService.save(ingredientsRecette)
        } catch (ValidationException e) {
            respond ingredientsRecette.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ingredientsRecette.label', default: 'IngredientsRecette'), ingredientsRecette.id])
                redirect ingredientsRecette
            }
            '*'{ respond ingredientsRecette, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        ingredientsRecetteService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingredientsRecette.label', default: 'IngredientsRecette'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    def deleteSelected() {
        def ids = params.list('recettesASupprimer')*.toLong()
        ids.each {
            MesRecettes.get(it)?.delete(flush: true)
        }
        redirect(action: 'index')
    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredientsRecette.label', default: 'IngredientsRecette'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
