<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="https://img.cpdaily.com/temp/media/media_base.css"/>
    <title>${message.title}</title>
</head>
<body>
<h1>${message.title}</h1>

<div class="article-info">
    <span class="publish-time">${message.cTime}</span>
</div>
<div class="content">
${message.content}
</div>

<div class="footer">
    <ul>
    <#if message.originUrl??>
        <li>
            原文地址
            <script>
                if (self !== top) {
                    document.write('<a href="${message.originUrl}" target="_blank">${message.originUrl}</a>')
                } else {
                    document.write('<a href="${message.originUrl}">${message.originUrl}</a>')
                }
            </script>
        </li>
    </#if>
    </ul>
</div>
</body>
</html>
