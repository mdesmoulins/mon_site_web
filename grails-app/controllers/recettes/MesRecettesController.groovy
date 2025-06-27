package recettes

import IngredientsRecettes.Ingredients
import IngredientsRecettes.Unite
import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

@Transactional
class MesRecettesController {

    MesRecettesService mesRecettesService

    //static allowedMethods = [save: "POST", update: "POST", delete: "DELETE", deleteSelected: "POST"]

    def index(Integer max) {
        def colTri = "nom"
        if (params.tri) {
            colTri = "difficulte"
        }

        def maListe = MesRecettes.findAll([sort: colTri, order: params.tri])
        render(view: "index", model: [mesRecettesList: maListe, recetteCherche: params.recetteCherche])
    }

    def search(params) {
        def maListe = MesRecettes.findAllByNomIlike("%" + params.recetteCherche + "%")
        render(view: "index", model: [mesRecettesList: maListe, recetteCherche: params.recetteCherche])
    }

    def show(Long id) {
        def recette = mesRecettesService.get(id)
        if (!recette) {
            notFound()
            return
        }

        recette.ingredientsrecettes = recette.ingredientsrecettes?.sort {
            it.ingredient?.nom?.toLowerCase()
        }

        render(view: 'show', model: [mesRecettes: recette])
    }

    def edit(Long id) {
        MesRecettes maRecette

        if (id) {
            maRecette = MesRecettes.get(id)
            if (!maRecette) {
                flash.message = "Recette introuvable avec l'id ${id}."
                redirect action: "index"
                return
            }
        } else {
            maRecette = new MesRecettes()
        }

        def mesUstensiles = Ustensiles.list(sort: 'nom')
        def mesIngredients = Ingredients.list(sort: 'nom')
        def mesUnite = Unite.list(sort: 'nom')

        render(view: "edit", model: [
                maRecette: maRecette,
                mesUstensiles: mesUstensiles,
                mesIngredients: mesIngredients,
                mesUnite: mesUnite
        ])
    }


    def update(MesRecettes maRecette) {
        println (params)
        MesRecettes.withTransaction {
            if (maRecette == null) {
                maRecette = new MesRecettes(params)
            }

            MultipartFile fileMiniature = request.getFile('pMiniature')
            if (fileMiniature && !fileMiniature.empty) {
                String uploadDir = grailsApplication.config.getProperty('photo.upload.dir', String, 'uploads')
                new File(uploadDir).mkdirs()

                String filename = UUID.randomUUID().toString() + "_" + fileMiniature.originalFilename
                fileMiniature.transferTo(new File(uploadDir, filename))

                def phMiniature = new Photo(
                        filename: filename,
                        contentType: fileMiniature.contentType,
                        size: fileMiniature.size
                )
                phMiniature.save(flush: true)

                maRecette.photoMiniature = phMiniature
            }

            MultipartFile fileComplete = request.getFile('pComplete')
            if (fileComplete && !fileComplete.empty) {
                String uploadDir = grailsApplication.config.getProperty('photo.upload.dir', String, 'uploads')
                new File(uploadDir).mkdirs()

                String filename = UUID.randomUUID().toString() + "_" + fileComplete.originalFilename
                fileComplete.transferTo(new File(uploadDir, filename))

                def phComplete = new Photo(
                        filename: filename,
                        contentType: fileComplete.contentType,
                        size: fileComplete.size
                )
                phComplete.save(flush: true)

                maRecette.photoComplete = phComplete
            }

            maRecette?.ustensiles?.clear()
            if (params.ustensilesNew) {
                for (itUstensile in params.ustensilesNew)  {
                    def ustensile
                    if (itUstensile.isInteger()) {
                        ustensile = Ustensiles.get(itUstensile)
                    } else {
                        ustensile = new Ustensiles(nom: itUstensile)
                    }
                    maRecette.addToUstensiles(ustensile)
                }
            }

            maRecette?.ingredientsrecettes?.clear()

            def boucle = 0
            if(params.ingredientsTab) {
                for (itIngredient in params.ingredientsTab) {
                    if (itIngredient) {
                        def ingredient

                        if((itIngredient.isInteger())) {
                               ingredient = Ingredients.get(itIngredient)
                        }
                        else {
                            ingredient = Ingredients.findByNom(itIngredient)
                            if (!ingredient) {
                                ingredient = new Ingredients(nom: itIngredient)
                                ingredient.save(failOnError: true, flush: true)
                            }
                        }
                        def unite = Unite.get(params.uniteTab[boucle])

                        // Création avec liaison à la recette
                        IngredientsRecette ingredientsRecette = new IngredientsRecette(
                                mesRecettes: maRecette,
                                ingredient: ingredient,
                                quantite: params.quantiteTab[boucle]?.toInteger(),
                                unite: unite
                        )
                        maRecette.addToIngredientsrecettes(ingredientsRecette)
                    }
                    boucle++
                }
            }
            boucle = 0
            if(params.ingredientsTabOld) {
                for (itIngredient in params.ingredientsTabOld) {
                    if (itIngredient) {
                        def ingredient = Ingredients.get(itIngredient)
                        def unite = Unite.get(params.uniteTabOld[boucle])

                        // Création avec liaison à la recette
                        IngredientsRecette ingredientsRecette = new IngredientsRecette(
                                mesRecettes: maRecette,
                                ingredient: ingredient,
                                quantite: params.quantiteTabOld[boucle]?.toInteger(),
                                unite: unite
                        )
                        maRecette.addToIngredientsrecettes(ingredientsRecette)
                    }
                    boucle++
                }
            }

            maRecette.save(failOnError: true, flush: true)
        }

        render(view: "show", model: [mesRecettes: maRecette])
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        mesRecettesService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'mesRecettes.label', default: 'MesRecettes'), id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def deleteSelected() {
        def selectedIds = params.list('recettesASupprimer')

        if (selectedIds) {
            selectedIds.each { idStr ->
                try {
                    Long id = idStr.toLong()
                    MesRecettes recette = MesRecettes.get(id)
                    if (recette) {
                        recette.delete(flush: true)
                    }
                } catch (NumberFormatException e) {
                    // Ignorer les erreurs de conversion d'id
                }
            }
            flash.message = "${selectedIds.size()} recette(s) supprimée(s)."
        } else {
            flash.message = "Aucune recette sélectionnée."
        }

        redirect action: 'index'
    }
    def create() {
        def maRecette = new MesRecettes()

        def mesUstensiles = Ustensiles.list(sort: 'nom')
        def mesIngredients = Ingredients.list(sort: 'nom')
        def mesUnite = Unite.list(sort: 'nom')

        render(view: "edit", model: [
                maRecette: maRecette,
                mesUstensiles: mesUstensiles,
                mesIngredients: mesIngredients,
                mesUnite: mesUnite
        ])
    }



    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mesRecettes.label', default: 'MesRecettes'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}