package recettes

import grails.converters.JSON
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UstensilesController {

    UstensilesService ustensilesService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", saveFromPopup: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ustensilesService.list(params), model:[ustensilesCount: ustensilesService.count()]
    }

    def show(Long id) {
        respond ustensilesService.get(id)
    }

    def create() {
        respond new Ustensiles(params)
    }

    def save(Ustensiles ustensiles) {
        if (ustensiles == null) {
            notFound()
            return
        }

        try {
            ustensilesService.save(ustensiles)
        } catch (ValidationException e) {
            respond ustensiles.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ustensiles.label', default: 'Ustensiles'), ustensiles.id])
                redirect ustensiles
            }
            '*' { respond ustensiles, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond ustensilesService.get(id)
    }

    def update(Ustensiles ustensiles) {
        if (ustensiles == null) {
            notFound()
            return
        }

        try {
            ustensilesService.save(ustensiles)
        } catch (ValidationException e) {
            respond ustensiles.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ustensiles.label', default: 'Ustensiles'), ustensiles.id])
                redirect ustensiles
            }
            '*'{ respond ustensiles, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        ustensilesService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ustensiles.label', default: 'Ustensiles'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    // Nouvelle action pour sauvegarder depuis la popup (AJAX JSON)
    def saveFromPopup() {
        def data = request.JSON

        if (!data.nom || data.nom.trim() == '') {
            render([success: false, message: "Le nom est requis"] as JSON)
            return
        }

        def ustensile = new Ustensiles(nom: data.nom.trim())

        try {
            ustensilesService.save(ustensile)
            render([success: true, ustensile: [id: ustensile.id, nom: ustensile.nom]] as JSON)
        } catch (ValidationException e) {
            render([success: false, message: "Erreur de validation: ${ustensile.errors.allErrors*.defaultMessage.join(', ')}"] as JSON)
        } catch (Exception e) {
            render([success: false, message: "Erreur lors de l'enregistrement"] as JSON)
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ustensiles.label', default: 'Ustensiles'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
