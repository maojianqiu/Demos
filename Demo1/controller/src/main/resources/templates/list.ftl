<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <#list list as user>
        ${user.userId}
        ${user.userName}
        ${(user.userDate?string("yyyy-MM-dd HH-mm-ss"))!"没有设置时间"}  <#--!后如果为空的话 需要显示的内容-->

    </#list>

</body>
</html>