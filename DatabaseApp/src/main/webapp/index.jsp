<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Strona powitalna</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #333;
            color: #fff;
            background-image: url('backgroundOffice.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
        .container {
            text-align: center;
            padding: 20px;
            border-radius: 8px;
            background: #444;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        h1 {
            color: #fff;        }
        p {
            color: #ccc;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            background: #007bff;
            color: #fff;
        }
        a:hover {
            background: #0056b3;
        }

    </style>
</head>
<body class="dark-mode">
<div class="container">
    <h1>Welcome to the Employees database!</h1>
    <p>You will be redirected in 5 seconds.</p>
    <p>If you don't want to wait, <a href="hello-servlet">click  here</a>.</p>
</div>
<script>
    setTimeout(function() {
        window.location.href = 'hello-servlet';
    }, 5000);




</script>
</body>
</html>