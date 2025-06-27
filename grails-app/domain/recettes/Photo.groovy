package recettes

class Photo {
    String filename
    String contentType
    Long size

    static constraints = {
        filename blank: false
        contentType blank: false
        size nullable: false
    }
}
