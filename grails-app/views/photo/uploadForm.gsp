<h2>Ajouter une photo</h2>
<g:form controller="photo" action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="photo"/>
    <input type="submit" value="Envoyer"/>
</g:form>
<g:link controller="photo" action="list">Voir la galerie</g:link>
