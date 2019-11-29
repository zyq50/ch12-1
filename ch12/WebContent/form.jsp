<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="UploadServlet" method="post"  enctype="multipart/form-data">
    <table width="600px">
        <tr>
            <td>文件1</td>
            <td><input type="file" name="file1" /></td>
        </tr>
        <tr>
            <td>文件2</td>
            <td><input type="file" name="file1" /></td>
        </tr>
        <tr>
            <td>文件3</td>
            <td><input type="file" name="file3" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="上传" /></td>
        </tr>
    </table>
</form>

</body>
</html>