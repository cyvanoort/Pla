<html>
<head>
<title>
CMS systeem
</title>
<link rel="stylesheet" type="text/css" href="include/style.css">
</head>
<body>
<?php
	echo '<script type="text/javascript">';
	echo 'alert("Gebruikersnaam of wachtwoord is onjuist");';
	echo '</script>';
?>
<div id="login">
Administrator inloggen:
<form method="POST" action="/include/login.php">
<table>
<tr>
<td>
Gebruikersnaam:
</td>
<td>
<input type="text" name="user" />
</td>
</tr>
<tr>
<td>
Wachtwoord
</td>
<td>
<input type="password" name="pass" />
</td>
</tr>
<tr>
<td>
<input type="submit" value="inloggen" />
</td>
</tr>
</table>
</form>
</div>
</body>
</html>