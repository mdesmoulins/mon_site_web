package recettes

import grails.gorm.transactions.Transactional
import org.springframework.web.multipart.MultipartFile
import recettes.Photo

@Transactional
class PhotoController {

    def index() {
        redirect action: "list"
    }

    def uploadForm() {}

    def upload() {
        MultipartFile file = request.getFile('photo')
        if (file && !file.empty) {
            String uploadDir = grailsApplication.config.getProperty('photo.upload.dir', String, 'uploads')
            new File(uploadDir).mkdirs()

            String filename = UUID.randomUUID().toString() + "_" + file.originalFilename
            file.transferTo(new File(uploadDir, filename))

            def photo = new Photo(
                    filename: filename,
                    contentType: file.contentType,
                    size: file.size
            )
            photo.save(flush: true)
            flash.message = "Photo ajoutée avec succès"
        } else {
            flash.message = "Aucun fichier sélectionné"
        }
        redirect(action: "list")
    }

    def list() {
        [photos: Photo.list()]
    }

    def show(Long id) {
        def photo = Photo.get(id)
        if (!photo) {
            render status: 404
            return
        }

        String uploadDir = grailsApplication.config.getProperty('photo.upload.dir', String, 'uploads')
        File file = new File(uploadDir, photo.filename)

        if (!file.exists()) {
            render status: 404
            return
        }

        response.setContentType(photo.contentType)
        response.outputStream << file.bytes
        response.outputStream.flush()
    }

    def delete(Long id) {
        def photo = Photo.get(id)
        if (photo) {
            String uploadDir = grailsApplication.config.getProperty('photo.upload.dir', String, 'uploads')
            File file = new File(uploadDir, photo.filename)
            if (file.exists()) {
                file.delete()
            }
            photo.delete(flush: true)
            flash.message = "Photo supprimée"
        }
        redirect(action: "list")
    }
}
