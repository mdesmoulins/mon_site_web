<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'mesRecettes.label', default: 'MesRecettes')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <asset:stylesheet href="indexframe1vscode.css"/>

    <style>
    /* Espacement entre chaque ligne d'ingrédient */
    #ingredients-container .ingredient-line {
        border: 1px solid #ddd;
        padding: 10px;
        margin-bottom: 12px;
        border-radius: 4px;
        background: #f9f9f9;
    }

    /* Espace entre labels et champs */
    .formRecette {
        display: inline-block;
        width: 90px;
        font-weight: bold;
        margin-right: 8px;
    }

    /* Champs sur une même ligne avec un petit gap */
    .ingredient-line select,
    .ingredient-line input[type="text"] {
        margin-right: 15px;
        min-width: 150px;
    }
    </style>
    <!-- CSRF token dans le head -->
    <meta name="_csrf" content="${request.getAttribute('_csrf.token')}" />
</head>
<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#edit-mesRecettes" class="skip" tabindex="-1">
                <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
            </a>
            <div class="nav" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                    <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </ul>
            </div>
        </section>

        <section class="row">
            <div id="edit-mesRecettes" class="col-12 content scaffold-edit" role="main">
                <h1><g:message code="default.edit.label" args="[entityName]" /></h1>

                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>

                <g:hasErrors bean="${this.mesRecettes}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.mesRecettes}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                <g:message error="${error}"/>
                            </li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>

                <g:form resource="${this.maRecette}" action="update" method="POST" enctype="multipart/form-data">
                    <g:hiddenField name="version" value="${maRecette?.version}" />
                    <g:hiddenField name="id" value="${maRecette?.id}" />
                    <g:hiddenField name="ingredientsTabOld" value="" />
                    <g:hiddenField name="uniteTabOld" value="" />
                    <g:hiddenField name="quantiteTabOld" value="" />

                    <div class="ligneForm">
                        <label class="formRecette">Nom</label><br/>
                        <g:field type="text" name="nom" value="${maRecette?.nom}"/><br/>
                    </div>

                    <div class="ligneForm">
                        <label class="formRecette">Description</label>
                        <g:textArea name="description" value="${maRecette?.description}" rows="4" cols="30"/><br/>
                    </div>

                    <div class="ligneForm">
                        <label class="formRecette">Instruction</label>
                        <g:textArea name="instruction" value="${maRecette?.instruction}" rows="4" cols="30"/><br/>
                    </div>

                    <div class="ligneForm">
                        <label class="formRecette">Ustensiles</label>
                        <g:select name="ustensilesNew" from="${mesUstensiles}" value="${maRecette?.ustensiles*.id}" optionKey="id" optionValue="nom" multiple="true" /><br/>
                        <button type="button" id="bouton-ajouter-ustensile" style="margin-left: 10px;">+</button>
                    </div>

                    <div class="LigneForm">
                        <label class="formRecette">Difficulte</label>
                        <g:select name="difficulte" from="${1..5}" value="${maRecette?.difficulte}" noSelection="['':'difficulte']" />
                    </div>

                    <div class="LigneForm">
                        <label class="formRecette">Miniature</label>
                        <input type="file" name="pMiniature"/>
                        <g:link controller="photo" action="list">Voir la galerie</g:link>
                    </div>

                    <div class="LigneForm">
                        <label class="formRecette">Complete</label>
                        <input type="file" name="pComplete"/>
                        <g:link controller="photo" action="list">Voir la galerie</g:link>
                    </div>

                    <div class="LigneForm">
                        <label class="formRecette">Ingrédients</label>  <button type="button" id="bouton-ajouter-ingredient" style="margin-top:10px;">+</button>
                        <g:each in="${maRecette.ingredientsrecettes}" status="indexIt" var="it">
                            <div id="ingredients-container-old">
                                <div class="ingredient-line-old">

                                    <!-- Select visible ingrédients -->
                                    <select name="ingredientsTabOld">
                                        <option value="">-- Choisir --</option>
                                        <g:each in="${mesIngredients}" var="ing">
                                            <option value="${ing.id}" <g:if test="${maRecette.ingredientsrecettes.getAt(indexIt).ingredient.id == ing.id}"> selected="true" </g:if>>${ing.nom}</option>
                                        </g:each>
                                    </select>

                                    <label class="formRecette">Quantité</label>
                                    <input type="text" name="quantiteTabOld" value="${maRecette.ingredientsrecettes.getAt(indexIt).quantite}"/>

                                    <!-- Select visible unités -->
                                    <select name="uniteTabOld">
                                        <option value="">-- Choisir --</option>
                                        <g:each in="${mesUnite}" var="u">
                                            <option value="${u.id}" <g:if test="${maRecette.ingredientsrecettes.getAt(indexIt).unite.id == u.id}"> selected="true" </g:if>>${u.nom}</option>
                                        </g:each>
                                    </select>

                                </div>
                            </div>
                        </g:each>
                        <div id="ingredients-container">
                            <div class="ingredient-line">

                                <!-- Template select caché ingrédients -->
                                <select name="ingredientsTab" data-template="true" style="display:none;" >
                                    <option value="">-- Choisir --</option>
                                    <g:each in="${mesIngredients}" var="ing">
                                        <option value="${ing.id}">${ing.nom}</option>
                                    </g:each>
                                </select>
                                <!-- Select visible ingrédients -->
                                <select name="ingredientsTab">
                                    <option value="">-- Choisir --</option>
                                    <g:each in="${mesIngredients}" var="ing">
                                        <option value="${ing.id}">${ing.nom}</option>
                                    </g:each>
                                </select>

                                <label class="formRecette">Quantité</label>
                                <!-- Template input caché quantite -->
                                <input type="text" name="quantiteTab" style="display:none;" />
                                <!-- Input visible quantite -->
                                <input type="text" name="quantiteTab" />

                                <!-- Template select caché unités -->
                                <select name="uniteTab" data-template="true" style="display:none;">
                                    <option value="">-- Choisir --</option>
                                    <g:each in="${mesUnite}" var="u">
                                        <option value="${u.id}">${u.nom}</option>
                                    </g:each>
                                </select>
                                <!-- Select visible unités -->
                                <select name="uniteTab">
                                    <option value="">-- Choisir --</option>
                                    <g:each in="${mesUnite}" var="u">
                                        <option value="${u.id}">${u.nom}</option>
                                    </g:each>
                                </select>

                            </div>
                        </div>
                    </div>

                    <fieldset class="buttons">
                        <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    </fieldset>
                </g:form>

            </div>
        </section>
    </div>
</div>


<!-- ✅ POPUP Ustensile -->
<div id="ustensile-popup" style="display:none; position:fixed; top:30%; left:50%; transform:translate(-50%, -50%);
background:#fff; border:1px solid #ccc; padding:20px; z-index:1000; box-shadow: 0 0 10px rgba(0,0,0,0.3);">
    <h3>Ajouter un ustensile</h3>
    <label for="nouvelUstensile">Nom :</label>
    <input type="text" id="nouvelUstensile" />
    <br/><br/>
    <button id="valider-ustensile">Ajouter</button>
    <button id="fermer-ustensile">Annuler</button>
</div>

<div id="ustensile-overlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
background:rgba(0,0,0,0.5); z-index:999;"></div>

<!-- ✅ POPUP Ingrédient -->
<div id="ingredient-popup" style="display:none; position:fixed; top:30%; left:50%; transform:translate(-50%, -50%);
background:#fff; border:1px solid #ccc; padding:20px; z-index:1000; box-shadow: 0 0 10px rgba(0,0,0,0.3);">
    <h3>Ajouter un ingrédient</h3>
    <label for="nouvelIngredient">Nom :</label>
    <input type="text" id="nouvelIngredient" />
    <br/><br/>
    <button id="valider-ingredient">Ajouter</button>
    <button id="fermer-ingredient">Annuler</button>
</div>

<div id="ingredient-overlay" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
background:rgba(0,0,0,0.5); z-index:999;"></div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const container = document.getElementById('ingredients-container');

        // Gestion dynamique des ingrédients
        function addIngredientLine() {
            const lastLine = container.querySelector('.ingredient-line:last-of-type');
            const newLine = lastLine.cloneNode(true);

            // Reset values for text inputs
            newLine.querySelectorAll('input[type="text"]').forEach(input => input.value = '');

            // Reset selects using the hidden template selects
            ['ingredientsTab', 'uniteTab'].forEach(name => {
                const visibleSelect = newLine.querySelector(`select[name="${name}"]:not([data-template])`);
                const templateSelect = container.querySelector(`select[name="${name}"][data-template]`);

                if (visibleSelect && templateSelect) {
                    visibleSelect.innerHTML = templateSelect.innerHTML;
                    visibleSelect.value = ''; // clear selection
                }
            });

            container.appendChild(newLine);

            // Attach event listeners on new inputs/selects
            newLine.querySelectorAll('input, select').forEach(el => {
                el.addEventListener('input', handleInputChange);
                el.addEventListener('change', handleInputChange);
            });
        }

        function handleInputChange() {
            const lines = container.querySelectorAll('.ingredient-line');
            const lastLine = lines[lines.length - 1];
            const inputs = lastLine.querySelectorAll('input, select');
            const isFilled = Array.from(inputs).some(input => input.value && input.value.trim() !== '');

            if (isFilled) {
                addIngredientLine();
            }
        }

        // Attach listeners to existing inputs/selects except those in templates
        container.querySelectorAll('.ingredient-line select:not([data-template]), .ingredient-line input').forEach(el => {
            el.addEventListener('input', handleInputChange);
            el.addEventListener('change', handleInputChange);
        });

        // Trigger check on load in case the last line is already filled
        handleInputChange();
    });

</script>

<!-- ✅ Script popup JS -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const popup = document.getElementById('ustensile-popup');
        const overlay = document.getElementById('ustensile-overlay');
        const boutonAjouter = document.getElementById('bouton-ajouter-ustensile');
        const validerBtn = document.getElementById('valider-ustensile');
        const fermerBtn = document.getElementById('fermer-ustensile');
        const input = document.getElementById('nouvelUstensile');
        const selectUstensile = document.querySelector('select[name="ustensilesNew"]'); // ✅ bon select

        boutonAjouter.addEventListener('click', () => {
            popup.style.display = 'block';
            overlay.style.display = 'block';
        });

        fermerBtn.addEventListener('click', () => {
            popup.style.display = 'none';
            overlay.style.display = 'none';
        });

        validerBtn.addEventListener('click', () => {
            const nom = input.value.trim();
            if (nom !== '') {
                const option = document.createElement('option');
                option.text = nom;
                option.value = nom;
                option.selected = true;
                selectUstensile.appendChild(option);

                popup.style.display = 'none';
                overlay.style.display = 'none';
                input.value = '';
            }
        });
    });
</script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // --- popup ingrédient ---
        const popupIngredient = document.getElementById('ingredient-popup');
        const overlayIngredient = document.getElementById('ingredient-overlay');
        const boutonAjouterIngredient = document.getElementById('bouton-ajouter-ingredient');
        const validerIngredientBtn = document.getElementById('valider-ingredient');
        const fermerIngredientBtn = document.getElementById('fermer-ingredient');
        const inputIngredient = document.getElementById('nouvelIngredient');

        boutonAjouterIngredient.addEventListener('click', () => {
            popupIngredient.style.display = 'block';
            overlayIngredient.style.display = 'block';
        });

        fermerIngredientBtn.addEventListener('click', () => {
            popupIngredient.style.display = 'none';
            overlayIngredient.style.display = 'none';
        });

        validerIngredientBtn.addEventListener('click', () => {
            const nom = inputIngredient.value.trim();
            if (nom !== '') {
                // Trouver tous les <select name="ingredientsTab[]"> et ajouter l'option à chacun
                document.querySelectorAll('select[name="ingredientsTab"], select[name="ingredientsTab[]"]').forEach(select => {
                    const option = document.createElement('option');
                    option.text = nom;
                    option.value = nom;
                    select.appendChild(option);
                });

                // Fermer popup
                popupIngredient.style.display = 'none';
                overlayIngredient.style.display = 'none';
                inputIngredient.value = '';
            }
        });
    });
</script>
</body>
</html>
