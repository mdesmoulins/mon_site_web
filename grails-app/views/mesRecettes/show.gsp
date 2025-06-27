<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'mesRecettes.label', default: 'MesRecettes')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>

    <!-- CSS principal -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Abhaya+Libre+ExtraBold:wght@800&display=swap" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inria+Serif:wght@700&display=swap" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Ibarra+Real+Nova:wght@700&display=swap" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Hind:wght@400&display=swap" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400&display=swap" />
    <asset:stylesheet href="indexframe2vscode.css"/>
</head>
<body>

<div class="main-container-2">
    <div class="box-5">
        <div class="section-10"><a href="/MesRecettes"><asset:image src="Logo.png"/></a></div>
        <div class="group-7"><a href="/MesRecettes"><span class="text-17">Maxpatisse</span></a></div>
        <div class="section-2"></div>
    </div>

    <div class="group-1">
        <!-- Affichage image de la recette (photoComplete) -->
        <div class="img-recette" style="background: url('/assets/uploads/${mesRecettes?.photoComplete?.filename}') no-repeat center/cover;"></div>
        <span class="text">${mesRecettes?.nom}</span>

        <div class="pic"></div>

        <span class="text-3">Description</span>
        <span class="text-4">${mesRecettes?.description}</span>
    </div>

    <div class="boxes-row">
        <div class="box-ustensiles">
            <div>
                <span class="text-5">Matériels:</span>
            </div>
            <g:each in="${mesRecettes?.ustensiles}" var="ustensile">
                <div><span class="text-ligne">• ${ustensile.nom}</span></div>
            </g:each>
        </div>

        <div class="box-ingredients">
            <div>
                <span class="text-5">Ingrédients:</span>
            </div>
            <g:each in="${mesRecettes?.ingredientsrecettes?.sort { it.ingredient?.nom?.toLowerCase() }}" var="ingredientRecette">
                <div>
                    <span class="text-ligne">
                        • ${ingredientRecette?.ingredient?.nom} : ${ingredientRecette?.quantite} ${ingredientRecette?.unite?.nom ?: ''}
                    </span>
                </div>
            </g:each>
        </div>
    </div>
</div>

<div class="group-instruction">
    <span class="text-instruction">Instruction</span>
    <span class="text-instruction2">${mesRecettes?.instruction}</span>
</div>

<div class="box-edit">
    <div class="group-edit">
        <g:link controller="mesRecettes" action="edit" id="${mesRecettes?.id}">
            <div class="section-edit"></div>
            <span class="text-edit">Éditer</span>
        </g:link>
    </div>
</div>

</body>
</html>
