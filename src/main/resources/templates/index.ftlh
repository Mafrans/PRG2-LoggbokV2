<html>
<head>

</head>
<body>
    <ul>
        <#list logEntries as entry>
            <li>
                ${entry!"No"} :
                <#if entry?? >
                    ${entry.title!"Untitled"} by ${entry.author.name!"Unknown"}
                </#if>
            </li>
        </#list>
    </ul>
</body>
</html>