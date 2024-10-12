<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>File Upload</title>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 30px;
            padding: 10px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: black;
            margin-bottom: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .list-group-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .alert {
            margin-top: 10px;
        }
        .btn-back {
            background: linear-gradient(45deg, #00bfff, #1e90ff);
            color: white;
            border: none;
        }
        .btn-back:hover {
            background: linear-gradient(45deg, #1e90ff, #00bfff);
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Elevate Your Files to the Cloud with S3</h2>

    <h4>Upload a File</h4>
    <form action="/uploadfile" method="post" enctype="multipart/form-data" class="mb-4">
        <div class="form-group">
            <label for="file">Select a file to upload:</label>
            <input type="file" name="file" class="form-control-file" required>
        </div>
        <button type="submit" class="btn btn-primary btn-sm">Upload a File</button>
    </form>

    <h4>Upload a Folder</h4>
    <form action="/uploadfolder" method="post" enctype="multipart/form-data" class="mb-4">
        <div class="form-group">
            <label for="files">Select a folder to upload:</label>
            <input type="file" name="files" webkitdirectory multiple class="form-control-file" required/>
        </div>
        <button type="submit" class="btn btn-primary btn-sm">Upload a Folder</button>
    </form>

    <h2 class="mt-5 text-center">Available Files In S3</h2>
    <ul class="list-group">
        <c:forEach var="file" items="${files}">
            <li class="list-group-item">
                <span>${file}</span>
                <div>
                    <form action="/downloadfile" method="get" style="display:inline;">
                        <input type="hidden" name="fileName" value="${file}">
                        <button type="submit" class="btn btn-success btn-sm">Download</button>
                    </form>
                    <form action="/deletefile" method="post" style="display:inline;">
                        <input type="hidden" name="fileName" value="${file}">
                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                    </form>

                </div>
            </li>
        </c:forEach>
    </ul>

    <c:if test="${not empty message}">
        <div class="alert alert-info text-center" role="alert">
                ${message}
        </div>
        <div class="text-center mt-4">
            <a href="/" class="btn btn-back btn-sm">Back to Homepage</a>
        </div>
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
