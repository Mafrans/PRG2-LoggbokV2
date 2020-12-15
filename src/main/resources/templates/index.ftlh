<html>
<head>
    <!-- Bootstrap 5.0 beta -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container">
        <form>
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
                <label for="bodyField">Body</label>
                <textarea class="form-control" id="bodyField" rows="3"></textarea>
            </div>

            <button id="submitButton" class="btn btn-primary">Submit</button>
        </form>
        <ul>
            <#list logEntries as entry>
                <li>
                    <#if entry?? >
                        ${entry.title!"Untitled"} by ${entry.author.name!"Unknown"}
                    </#if>
                </li>
            </#list>
        </ul>
    </div>

    <script>
        const authorSelect = document.querySelector("#authorSelect");
        const submitButton = document.querySelector("#submitButton");
        authorSelect.addEventListener("input", () => {
            const newAuthorRow = document.querySelector("#newAuthorRow");
            newAuthorRow.classList.toggle("d-none", authorSelect.value !== "new");
        });

        submitButton.addEventListener("submit", console.log);
    </script>
</body>
</html>