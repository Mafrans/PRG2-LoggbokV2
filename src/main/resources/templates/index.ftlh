<html>
<head>
    <!-- Bootstrap 5.0 beta -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container">
        <div class="jumbotron mt-5">
            <h1 class="display-4">Thoughtbook</h1>
            <p class="lead">A collection of thoughts, created and filled by <a href="https://patreon.com/Mafrans">Mafrans</a>.</p>
        </div>

        <div class="form mt-5" aria-label="Input Form">
            <div class="row">
                <div class="col-8">
                    <div class="form-group">
                        <label for="titleField">Title</label>
                        <input class="form-control mb-3" placeholder="A new thought..." type="text" id="titleField" />
                    </div>
                </div>

                <div class="col-4">
                    <div class="form-group">
                        <label for="authorSelect">Author</label>
                        <select class="form-control mb-3" class="form-control" id="authorSelect">
                            <#list authors as author>
                                <#if author?? && author.name?? >
                                    <option value="${author.name}">${author.name}</option>
                                </#if>
                            </#list>
                            <option value="new">New Author</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row d-none" id="newAuthorRow">
                <div class="col-8"></div>
                <div class="col-4">
                    <div class="form-group mb-3">
                        <label for="titleField">New Author Name</label>
                        <input class="form-control" placeholder="New Author" type="text" id="newAuthorField" />
                    </div>
                </div>
            </div>


            <div class="form-group mb-3">
                <label for="bodyArea">Body</label>
                <textarea class="form-control" id="bodyArea" rows="3"></textarea>
            </div>

            <button id="submitButton" class="btn btn-primary">Submit</button>
        </div>

        <hr class="my-5" />

        <h2 class="display-5">Past Thoughts</h2>

        <div id="accordion">
            <#list logEntries as entry>
                <#if entry?? >
                    <div class="card">
                        <div class="card-header" id="heading-${entry?index}">
                            <h5 class="mb-0">
                                <button class="btn btn-link" data-toggle="collapse" data-target="#collapse-${entry?index}" aria-expanded="true" aria-controls="collapse-${entry?index}">
                                    ${entry.title}
                                    <br /><small class="text-muted">${entry.author.name}</small>
                                </button>
                            </h5>
                        </div>

                        <div id="collapse-${entry?index}" class="collapse show" aria-labelledby="heading-${entry?index}" data-parent="#accordion">
                            <div class="card-body">
                                ${entry.body}
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>

    <script>

        if(location.search !== "") location.href = location.href.split("?")[0];

        function updateAuthor() {
            const newAuthorRow = document.querySelector("#newAuthorRow");
            newAuthorRow.classList.toggle("d-none", authorSelect.value !== "new");
        }

        const titleField = document.querySelector("#titleField");
        const authorSelect = document.querySelector("#authorSelect");
        const newAuthorField = document.querySelector("#newAuthorField");
        const bodyArea = document.querySelector("#bodyArea");
        const submitButton = document.querySelector("#submitButton");

        authorSelect.addEventListener("input", () => updateAuthor());
        document.addEventListener("DOMContentLoaded", () => updateAuthor());

        submitButton.addEventListener("click", () => {
            const title = titleField.value;
            const body = bodyArea.value;
            let author = authorSelect.value;
            if(author === "new") {
                author = newAuthorField.value;
            }

            location.href = location.href + "?title=" + title + "&body=" + body + "&author=" + author;
        });
    </script>
</body>
</html>