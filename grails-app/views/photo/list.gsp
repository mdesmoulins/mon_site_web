<h2>Galerie</h2>

<g:each in="${photos}" var="photo">
    <div style="margin-bottom: 20px;">
        <img src="${createLink(controller:'photo', action:'show', id:photo.id)}" width="200"/><br/>
        <g:link action="delete" id="${photo.id}">Supprimer</g:link>
    </div>
</g:each>

<g:link controller="photo" action="uploadForm">Ajouter une autre photo</g:link>
