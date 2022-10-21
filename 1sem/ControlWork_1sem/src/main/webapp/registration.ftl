<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration page</title>
</head>


<body>
<div class="container">
    <h2 class="header">Sign Up Form</h2>
    <form method="post" action="registration">

        <div class="box">
            <label for="firstName">First Name: </label>
            <div class="fr">
                <input type="text" name="firstname" placeholder="Enter First Name"
                       autofocus="on" required>
            </div>
        </div>

        <div class="box">
            <label for="secondName"> Last Name: </label>
            <div class="fr">
                <input type="text" required name="lastname"
                       placeholder="Enter Last Name">
            </div>
        </div>

        <div class="box">
            <label for="login"> Login: </label>
            <div class="fr">
                <input type="text" required name="login" placeholder="Enter Login">
            </div>
        </div>

        <div class="box">
            <label for="password"> Password </label>
            <div class="fr">
                <input type="Password" required name="password" placeholder="Enter Password">
            </div>
        </div>

        <div class="box">
            <label for="email" class="fl fontLabel"> Email ID: </label>
            <div class="fr">
                <input type="email" required name="email" placeholder="Enter Email">
            </div>
        </div>

        <div class="cls">
            <input type="button" name="Submit" class="submit" value="sumbit">
        </div>

    </form>
</div>
</body>
</html>