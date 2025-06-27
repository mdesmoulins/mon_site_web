<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'mesRecettes.label', default: 'MesRecettes')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <!-- CSS principal -->
    <asset:stylesheet href="indexframe1vscode.css"/>
</head>
<body>
<div class="main-container">
<div class="box">
    <div class="box-2">
        <div class="section"><a href="http://localhost:8080/mesRecettes/"><asset:image src="Logo.png"/></a></div>
        <div class="group"><a href="http://localhost:8080/mesRecettes/"><span class="text">Maxpatisse</span></a></div>
        <div class="section-2"><div class="pic"></div></div>
    </div>

    <div class="box-3">
        <sec:ifAnyGranted  roles="ROLE_CHEF">
        <div class="group-3">
            <button type="button" class="text-3" onclick="toggleEditMode()">✏️ Éditer</button>
                </div>
            </sec:ifAnyGranted>
        <div class="section-4">
            <g:form action="index" method="get" name="triForm">
                <label for="tri-select" class="select-label"></label><br>
                <select id="tri-select" name="tri" class="text-4" onchange="this.form.submit()">
                    <option value="">👨‍🍳 Sélectionnez une difficulté</option>
                    <option value="asc" ${params.tri == 'asc' ? 'selected' : ''}>Difficulté croissante</option>
                    <option value="desc" ${params.tri == 'desc' ? 'selected' : ''}>Difficulté décroissante</option>
                </select>
            </g:form>
        </div>

        </div>

</div>

<div class="wrapper">
    <span class="text-5">

<g:if test="${recetteCherche}">${recetteCherche}
</g:if>
<g:else>Gateaux
</g:else>
    </span>
    <div class="img-loupe" onClick="document.forms['maRecherche'].submit();"></div>
    <div class="group-24">
        <g:form name="maRecherche" action="search" method="post">
            <input class="text-26" type="text" placeholder="Recherche" name="recetteCherche" value="${recetteCherche}">
        </g:form>
    </div>
</div>

Résultat : ${mesRecettesList.size()}

<g:form action="deleteSelected" method="post" name="formDelete">
    <input type="submit" value="🗑 Supprimer les recettes sélectionnées" id="deleteBtn" style="display:none; margin: 10px 0;" />

    <g:each in="${mesRecettesList}" var="it" status="indice">

    <g:if test="${indice % 4 == 0}">
        <g:if test="${indice != 0}">
            </div>
        </g:if>
        <div class="ligneRecette">
    </g:if>
        <div class="box-4">
            <input type="checkbox" name="recettesASupprimer" value="${it.id}" class="checkbox-delete" style="display:none;" />
        <g:link action="show" id="${it.id}">
            <div class="pic-recette" style=" background: url('/assets/uploads/${it?.photoMiniature?.filename}')"/></div>
        </g:link>

    <span class="text-7">
        <g:link action="show" id="${it.id}">${it.nom}</g:link>
    </span>
    <span class="text-8">
        ${'👨‍🍳'.repeat(it.difficulte)}${''.repeat(5 - it.difficulte)}
    </span>
    </div>
    <g:if test="${indice == mesRecettesList.size() - 1}">
        </div>
    </g:if>
</g:each>
</g:form>
<div class="group-7">
    <g:link action="create">
        <span class="text-15">New</span>
    </g:link>
</div>
    <script>
        function toggleEditMode() {
            const checkboxes = document.querySelectorAll('.checkbox-delete');
            const deleteBtn = document.getElementById('deleteBtn');

            const isVisible = checkboxes[0]?.style.display === 'inline-block';

            checkboxes.forEach(cb => {
                cb.style.display = isVisible ? 'none' : 'inline-block';
            });

            deleteBtn.style.display = isVisible ? 'none' : 'block';
        }
    </script>

    </body>
    </html>
